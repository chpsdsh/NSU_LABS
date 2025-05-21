package onlinechat.server;

import onlinechat.exceptions.ClientHandlerException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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
    private BufferedWriter out;
    private final Object clientLock = new Object();
    private final List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        new Server(1234);
    }

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.exit(1);
        }
        start();
    }

    public void broadcastUserLogin(String username, String sessionId) throws ClientHandlerException {
        synchronized (clientLock){
            for(ClientHandler client: clients){
                if(client.isLoggedIn() && !client.sessionId.equals(sessionId)){
                    client.sendLoginInformation(username,sessionId);
                }
            }
        }
    }

    public void broadcastMessage(ClientMessage clientMessage) throws ClientHandlerException {
        synchronized (clientLock){
            for(ClientHandler client: clients){
                if(client.isLoggedIn() && !client.sessionId.equals(clientMessage.session)){
                    client.sendLoginInformation(clientMessage);
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

    private void removeClient(ClientHandler client) {
        synchronized (clientLock) {
            clients.remove(client);
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
                    clientHandler = new JsonClientHandler(clientSocket, this);
                } else if (protocol.equals("PROTOCOL:OBJECT")) {
                    clientHandler = new ObjectClientHandler(clientSocket,this);
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
