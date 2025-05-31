package onlinechat.server;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import onlinechat.exceptions.ClientHandlerException;
import onlinechat.exceptions.JsonClientHandlerException;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class JsonClientHandler extends ClientHandler {
    private final BufferedReader reader;
    private final BufferedWriter writer;
    private final Gson gson = new Gson();


    public JsonClientHandler(Socket socket, Server server) throws IOException {
        super(socket, server);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    String line = reader.readLine();
                    ClientMessage clientMessage = gson.fromJson(line, ClientMessage.class);

                    if (clientMessage == null || clientMessage.type == null) {
                        sendFailure("message has no type");
                        continue;
                    }

                    handleClientMessage(clientMessage);

                } catch (ClientHandlerException | IOException e) {
                    Thread.currentThread().interrupt();
                }
            }
        } catch (JsonSyntaxException e) {
            Thread.currentThread().interrupt();
        } finally {
            server.removeClient(this);
            try {
                socket.close();
            } catch (IOException ignored) {
            }
        }
    }


    @Override
    public void handleMessage(ClientMessage clientMessage)  {
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
        JsonObject response = new JsonObject();
        response.addProperty("type", "list");

        JsonArray listUsers = new JsonArray();

        List<ClientHandler> loggedInClients = server.getLoggedInClients();
        for (ClientHandler client : loggedInClients) {
            JsonObject user = new JsonObject();
            user.addProperty("name", client.getUsername());
            listUsers.add(user);
        }
        response.addProperty("message", listUsers.toString());
        try {
            sendJson(response);
        } catch (JsonClientHandlerException e) {
            throw new ClientHandlerException("Exception while sending list of clients", e);
        }
    }

    public void sendSuccess(String message, String sessionId) throws ClientHandlerException {
        JsonObject json = new JsonObject();
        json.addProperty("type", "success");
        json.addProperty("message", message);
        json.addProperty("session", sessionId);
        try {
            sendJson(json);
        } catch (JsonClientHandlerException e) {
            throw new ClientHandlerException(e.getMessage(), e);
        }
    }

    @Override
    public void sendLogOut(String message) throws ClientHandlerException {
        JsonObject json = new JsonObject();
        json.addProperty("type", "logout");
        json.addProperty("message", message);
        json.addProperty("name", username);
        try {
            sendJson(json);
        } catch (JsonClientHandlerException e) {
            throw new ClientHandlerException(e.getMessage(), e);
        }
    }


    @Override
    public void sendFailure(String message) throws ClientHandlerException {
        JsonObject json = new JsonObject();
        json.addProperty("type", "error");
        json.addProperty("message", message);
        try {
            sendJson(json);
        } catch (JsonClientHandlerException e) {
            throw new ClientHandlerException(e.getMessage(), e);
        }
    }


    @Override
    public void sendMessage(ClientMessage clientMessage) throws ClientHandlerException {
        JsonObject json = new JsonObject();
        json.addProperty("type", clientMessage.type);
        json.addProperty("name", clientMessage.name);
        json.addProperty("message", clientMessage.message);
        json.addProperty("session", clientMessage.session);
        try {
            sendJson(json);
        } catch (JsonClientHandlerException e) {
            throw new ClientHandlerException(e.getMessage(), e);
        }
    }

    @Override
    public void sendPing() {
        JsonObject ping = new JsonObject();
        ping.addProperty("type", "ping");
        try {
            sendJson(ping);
        } catch (JsonClientHandlerException e) {
            System.err.println("Ping failed for " + username);
        }
    }


    private void sendJson(JsonObject json) throws JsonClientHandlerException {
        String jsonString = json.toString();
        try {
            writer.write(jsonString);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            throw new JsonClientHandlerException("Error sending json", e);
        }
    }
}