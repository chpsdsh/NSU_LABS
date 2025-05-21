package onlinechat.server;

import onlinechat.exceptions.ClientHandlerException;

import java.net.Socket;

public abstract class ClientHandler implements Runnable {
    protected final Socket socket;
    protected final Server server;
    protected String username;
    protected String clientType;
    protected String sessionId;
    protected volatile long lastActivityTime;
    protected volatile long lastPingTime;

    public ClientHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        lastActivityTime = System.currentTimeMillis();
    }

    public String getClientType() {
        return clientType;
    }

    public String getUsername() {
        return username;
    }

    public String getSessionId() {
        return sessionId;
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
        updateActivity();
        switch (clientMessage.type) {
            case "login" -> handleLogIn(clientMessage);
            case "disconnect" -> handleLogOut();
            case "message" -> handleMessage(clientMessage);
            case "list" -> handleListRequest();
            default -> sendFailure("unknown command");
        }
    }

    public void updateActivity() {
        lastActivityTime = System.currentTimeMillis();
    }

    public void updatePingTime(){
        lastPingTime = System.currentTimeMillis();
    }

    public abstract void handleMessage(ClientMessage clientMessage) throws ClientHandlerException;

    public abstract void sendMessage(ClientMessage clientMessage) throws ClientHandlerException;

    public abstract void handleLogIn(ClientMessage clientMessage) throws ClientHandlerException;

    public abstract void handleLogOut() throws ClientHandlerException;

    public abstract void handleListRequest() throws ClientHandlerException;

    public abstract void sendSuccess(String message, String sessionId) throws ClientHandlerException;

    public abstract void sendSuccess(String message) throws ClientHandlerException;

    public abstract void sendFailure(String message) throws ClientHandlerException;

    public abstract void sendLoginInformation(String username, String sessionId) throws ClientHandlerException;

}
