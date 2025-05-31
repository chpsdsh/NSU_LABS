package onlinechat.server;

import onlinechat.exceptions.ClientHandlerException;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class ClientHandler implements Runnable {
    protected final Socket socket;
    protected final Server server;
    protected String username;
    protected String sessionId;
    protected volatile long lastActivityTime;
    protected volatile long lastPingTime;
    protected final BlockingQueue<ClientMessage> outgoingMessages = new LinkedBlockingQueue<>();
    private final Thread senderThread;
    private final Object messageLock = new Object();
    private int lastSendIndex = 0;


    public ClientHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        lastActivityTime = System.currentTimeMillis();
        senderThread = new Thread(this::senderLoop);
    }

    public void notifyMessageLock() {
        synchronized (messageLock) {
            messageLock.notify();
        }
    }

    public void updateLastSend() {
        synchronized (messageLock) {
            lastSendIndex--;
        }
    }

    private void senderLoop() {
        while (!Thread.currentThread().isInterrupted()) {
            List<ClientMessage> history = server.getHistory();
            synchronized (messageLock) {
                if (lastSendIndex >= history.size()) {
                    try {
                        messageLock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                while (lastSendIndex < history.size()) {
                    ClientMessage msg = history.get(lastSendIndex++);
                    try {
                        if (!msg.session.equals(sessionId)) {
                            sendMessage(msg);
                        }
                    } catch (ClientHandlerException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
    }


    public String getUsername() {
        return username;
    }


    public long getLastActivityTime() {
        return lastActivityTime;
    }

    public long getLastPingTime() {
        return lastPingTime;
    }

    public boolean isLoggedIn() {
        return username != null;
    }

    public void setLoggingData(String username, String sessionId) {
        this.username = username;
        this.sessionId = sessionId;
    }

    protected void handleClientMessage(ClientMessage clientMessage) throws ClientHandlerException {
        switch (clientMessage.type) {
            case "login" -> {
                updateActivity();
                senderThread.start();
                handleLogIn(clientMessage);
            }
            case "disconnect" -> handleLogOut(clientMessage);
            case "message" -> {
                updateActivity();
                handleMessage(clientMessage);
            }
            case "list" -> {
                updateActivity();
                handleListRequest();
            }
            case "ping" -> updatePingTime();
            default -> sendFailure("unknown command");
        }
    }

    public void updateActivity() {
        lastActivityTime = System.currentTimeMillis();
    }

    public void updatePingTime() {
        lastPingTime = System.currentTimeMillis();
    }

    public void disconnect(String reason) {
        try {
            sendLogOut(reason);
        } catch (ClientHandlerException ignored) {
        }
        server.removeClient(this);
        try {
            socket.close();
        } catch (IOException ignored) {
        }

        ClientMessage clientMessage = new ClientMessage();
        clientMessage.type = "disconnect";
        clientMessage.message = reason;
        clientMessage.name = username;
        clientMessage.session = sessionId;
        server.broadcastMessage(clientMessage);
    }


    public abstract void handleMessage(ClientMessage clientMessage) ;

    public abstract void handleLogIn(ClientMessage clientMessage) throws ClientHandlerException;

    public abstract void handleLogOut(ClientMessage clientMessage) throws ClientHandlerException;

    public abstract void handleListRequest() throws ClientHandlerException;

    public abstract void sendFailure(String message) throws ClientHandlerException;

    public abstract void sendLogOut(String message) throws ClientHandlerException;

    public abstract void sendMessage(ClientMessage clientMessage) throws ClientHandlerException;

    public abstract void sendPing() throws ClientHandlerException;

    public abstract void sendSuccess(String message, String sessionId) throws ClientHandlerException;

}
