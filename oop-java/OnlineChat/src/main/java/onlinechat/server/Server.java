package onlinechat.server;

import onlinechat.exceptions.ClientHandlerException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Server {
    private ServerSocket serverSocket;
    private BufferedReader input;
    private final Object clientLock = new Object();
    private final List<ClientHandler> clients = new ArrayList<>();
    private Thread activityPinger;
    private Thread connectionPinger;

    public static void main(String[] args) {
        new Server(1234);
    }

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.exit(1);
        }
        activityPinger = new Thread(this::pingActivity);
        activityPinger.start();
        start();

    }

    public void broadcastUserLogin(String username, String sessionId) throws ClientHandlerException {
        synchronized (clientLock) {
            for (ClientHandler client : clients) {
                if (client.isLoggedIn() && !client.sessionId.equals(sessionId)) {
                    client.sendLoginInformation(username, sessionId);
                }
            }
        }
    }

    public void broadcastMessage(ClientMessage clientMessage) throws ClientHandlerException {
        synchronized (clientLock) {
            for (ClientHandler client : clients) {
                if (client.isLoggedIn() && !client.sessionId.equals(clientMessage.session)) {
                    client.sendMessage(clientMessage);
                }
            }
        }
    }

    public void broadcastUserDisconnect(String username) throws ClientHandlerException {
        synchronized (clientLock) {
            for (ClientHandler client : clients) {
                if (client.isLoggedIn()) {
                    client.sendDisconnectInformation(username);
                }
            }
        }
    }

    public String generateSessionID() {
        return "session-" + new Random().nextInt(999999);
    }

    public List<ClientHandler> getLoggedInClients() {
        List<ClientHandler> loggedInClients = new ArrayList<>();
        synchronized (clientLock) {
            for (ClientHandler client : clients) {
                if (client.isLoggedIn()) {
                    loggedInClients.add(client);
                }
            }
        }
        return loggedInClients;
    }

    private void addClient(ClientHandler client) {
        synchronized (clientLock) {
            clients.add(client);
        }
    }

    protected void removeClient(ClientHandler client) {
        synchronized (clientLock) {
            clients.remove(client);
        }
    }

    public void pingActivity()  {
        while (!Thread.currentThread().isInterrupted()){
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            long now = System.currentTimeMillis();
            List<ClientHandler> toRemove = new ArrayList<>();

            synchronized (clientLock) {
                for (ClientHandler client : clients) {
                    if (client.isLoggedIn() && (now - client.getLastActivityTime() > 10000)) {
                        try {
                            client.sendFailure("Disconnected due to inactivity");
                        } catch (ClientHandlerException e) {
                            System.err.println("Error disconnecting client");
                        }

                        toRemove.add(client);
                    }
                }
                clients.removeAll(toRemove);
            }

        }
    }

    public void start() {
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String protocol = input.readLine();
                ClientHandler clientHandler;
                if (protocol.equals("PROTOCOL:JSON")) {
                    System.out.println("JSON");
                    clientHandler = new JsonClientHandler(clientSocket, this);
                } else if (protocol.equals("PROTOCOL:OBJECT")) {
                    clientHandler = new ObjectClientHandler(clientSocket, this);
                } else {
                    System.out.println("UNKNOWN PROTOCOL");
                    clientSocket.close();
                    return;
                }
                addClient(clientHandler);
                new Thread(clientHandler).start();

            } catch (IOException e) {
                System.out.println("IOException");
            }
        }
    }
}
