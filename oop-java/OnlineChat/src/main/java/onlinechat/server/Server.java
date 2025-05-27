package onlinechat.server;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    private ServerSocket serverSocket;
    private BufferedReader input;
    private final Object clientLock = new Object();
    private final List<ClientHandler> clients = new CopyOnWriteArrayList<>();
    private final List<ClientMessage> messageHistory = new ArrayList<>();
    private final Object historyLock = new Object();
    private final Thread activityPinger;
    private final Thread connectionPinger;
    private final int MAX_HISTORY = 100;
    private static final Logger logger = LogManager.getLogger(Server.class);

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
        connectionPinger = new Thread(this::pingConnection);
        connectionPinger.start();
        activityPinger.start();
        start();
    }

    public void addToHistory(ClientMessage msg) {
        synchronized (historyLock) {
            logger.info("Adding message to history");
            if (messageHistory.size() > MAX_HISTORY) {
                messageHistory.remove(0);
                for (ClientHandler client : clients) {
                    client.updateLastSend();
                }
            }
            messageHistory.add(msg);
        }
    }


    public List<ClientMessage> getHistory() {
        synchronized (historyLock) {
            logger.info("Getting history ");
            return new ArrayList<>(messageHistory);
        }
    }

    public void broadcastMessage(ClientMessage message) {
        addToHistory(message);
        logger.info("Broadcasting message");
        for (ClientHandler client : clients) {
            if (client.isLoggedIn()) {
                client.notifyMessageLock();
            }
        }
    }


    public String generateSessionID() {
        logger.info("Generating session id");
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
            logger.info("Client added");

        }
    }

    protected void removeClient(ClientHandler client) {
        synchronized (clientLock) {
            clients.remove(client);
            logger.info("Client removed");
        }
    }

    public void pingConnection() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            long now = System.currentTimeMillis();

            for (ClientHandler client : clients) {
                if (client.isLoggedIn()) {
                    try {
                        if (now - client.getLastPingTime() > 15000) {
                            client.disconnect("No ping response");
                            logger.info("no ping response" + client.getUsername());
                        }

                        logger.info("Pinging connection " + client.getUsername());
                        client.sendPing();
                    } catch (Exception e) {
                        client.disconnect("Exception pinging client");
                    }
                }
            }
        }
    }

    public void pingActivity() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            long now = System.currentTimeMillis();

            for (ClientHandler client : clients) {
                System.out.println(now - client.getLastActivityTime());
                if (client.isLoggedIn() && (now - client.getLastActivityTime() > 20000)) {
                    client.disconnect("Disconnected due to inactivity");
                    logger.info("Disconnected due to inactivity " + client.getUsername());

                }
            }
        }
    }

    public void start() {
        logger.info("Server started");

        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String protocol = input.readLine();
                ClientHandler clientHandler;
                if (protocol.equals("PROTOCOL:JSON")) {
                    logger.info("Json client connected");
                    System.out.println("JSON");
                    clientHandler = new JsonClientHandler(clientSocket, this);
                } else if (protocol.equals("PROTOCOL:OBJECT")) {
                    logger.info("Serialization client connected");
                    clientHandler = new ObjectClientHandler(clientSocket, this);
                } else {
                    System.out.println("UNKNOWN PROTOCOL");
                    logger.error("Unknown protocol");
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
