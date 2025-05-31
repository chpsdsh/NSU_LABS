package onlinechat.server;

import onlinechat.exceptions.ClientHandlerException;
import onlinechat.exceptions.ObjectClientHandlerException;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ObjectClientHandler extends ClientHandler {
    private final ObjectInputStream reader;
    private final ObjectOutputStream writer;

    public ObjectClientHandler(Socket socket, Server server) throws IOException {
        super(socket, server);

        writer = new ObjectOutputStream(socket.getOutputStream());
        writer.flush();
        reader = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    ClientMessage clientMessage = (ClientMessage) reader.readObject();

                    if (clientMessage == null || clientMessage.type == null) {
                        sendFailure("message has no type");
                        continue;
                    }

                    handleClientMessage(clientMessage);

                } catch (ClientHandlerException | ClassNotFoundException | IOException e) {
                    Thread.currentThread().interrupt();
                }
            }
        } finally {
            server.removeClient(this);
            try {
                socket.close();
            } catch (IOException ignored) {
            }
        }
    }


    @Override
    public void handleMessage(ClientMessage clientMessage) {
        server.broadcastMessage(clientMessage);
    }


    @Override
    public void handleLogIn(ClientMessage clientMessage) throws ClientHandlerException {
        setLoggingData(clientMessage.name, server.generateSessionID());
        sendSuccess("successfully logged in", sessionId);
        sendPing();
        clientMessage.session = sessionId;
        server.broadcastMessage(clientMessage);
    }

    @Override
    public void handleLogOut(ClientMessage clientMessage) throws ClientHandlerException {
        server.removeClient(this);
        try {
            socket.close();
        } catch (IOException e) {
            throw new ClientHandlerException("Error closing socket", e);
        }
        server.broadcastMessage(clientMessage);
        Thread.currentThread().interrupt();
    }

    @Override
    public void handleListRequest() throws ClientHandlerException {
        ClientMessage response = new ClientMessage();
        response.type = "list";
        List<ClientHandler> loggedInClients = server.getLoggedInClients();
        StringBuilder sb = new StringBuilder();
        for (ClientHandler client : loggedInClients) {
            sb.append(client.username);
            sb.append("\n");
        }
        response.message = sb.toString();
        try {
            sendObject(response);
        } catch (ObjectClientHandlerException e) {
            throw new ClientHandlerException("Exception while sending list of clients", e);
        }
    }

    public void sendSuccess(String message, String sessionId) throws ClientHandlerException {
        ClientMessage successMessage = new ClientMessage();
        successMessage.type = "success";
        successMessage.message = message;
        successMessage.session = sessionId;
        try {
            sendObject(successMessage);
        } catch (ObjectClientHandlerException e) {
            throw new ClientHandlerException(e.getMessage(), e);
        }
    }

    @Override
    public void sendLogOut(String message) throws ClientHandlerException {
        ClientMessage clientMessage = new ClientMessage();
        clientMessage.type = "logout";
        clientMessage.message = message;
        clientMessage.name = username;
        try {
            sendObject(clientMessage);
        } catch (ObjectClientHandlerException e) {
            throw new ClientHandlerException(e.getMessage(), e);
        }
    }


    @Override
    public void sendFailure(String message) throws ClientHandlerException {
        ClientMessage clientMessage = new ClientMessage();
        clientMessage.type = "error";
        clientMessage.message = message;
        try {
            sendObject(clientMessage);
        } catch (ObjectClientHandlerException e) {
            throw new ClientHandlerException(e.getMessage(), e);
        }
    }


    @Override
    public void sendMessage(ClientMessage clientMessage) throws ClientHandlerException {
        try {
            sendObject(clientMessage);
        } catch (ObjectClientHandlerException e) {
            throw new ClientHandlerException(e.getMessage(), e);
        }
    }

    @Override
    public void sendPing() {
        ClientMessage ping = new ClientMessage();
        ping.type = "ping";
        try {
            sendObject(ping);
        } catch (ObjectClientHandlerException e) {
            System.err.println("Ping failed for " + username);
        }
    }


    private void sendObject(ClientMessage clientMessage) throws ObjectClientHandlerException {
        try {
            writer.writeObject(clientMessage);
            writer.flush();
        } catch (IOException e) {
            throw new ObjectClientHandlerException("Error sending object", e);
        }
    }
}