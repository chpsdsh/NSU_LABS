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
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    private ServerSocket serverSocket;
    private BufferedReader input;
    private final Object clientLock = new Object();
    private final List<ClientHandler> clients = new CopyOnWriteArrayList<>();
    private final List<ClientMessage> messageHistory = new ArrayList<>();
    private final Object historyLock = new Object();
    private Thread activityPinger;
    private Thread connectionPinger;
    private final int MAX_HISTORY = 100;

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
            if (messageHistory.size() > MAX_HISTORY) {
                messageHistory.remove(0);
                for (ClientHandler client : clients) {
                    client.updateLastSend();
                }
            }
        }
        messageHistory.add(msg);
    }


    public List<ClientMessage> getHistory() {
        synchronized (historyLock) {
            return new ArrayList<>(messageHistory);
        }
    }

    public void broadcastMessage(ClientMessage message) {
        addToHistory(message);
        for (ClientHandler client : clients) {
            if (client.isLoggedIn()) {
                client.notifyMessageLock();
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
                        } else {
                            System.out.println("sendping");
                            client.sendPing();
                        }
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
                }
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
