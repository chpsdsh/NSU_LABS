package onlinechat.server;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import onlinechat.exceptions.ClientHandlerException;
import onlinechat.exceptions.JsonClientHandlerException;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class JsonClientHandler extends ClientHandler {
    private final JsonReader reader;
    private final BufferedWriter writer;
    private final Gson gson = new Gson();


    public JsonClientHandler(Socket socket, Server server) throws IOException {
        super(socket, server);
        reader = new JsonReader(new InputStreamReader(socket.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    @Override
    public void run() {
        try {
            reader.setLenient(true);

            while (!Thread.currentThread().isInterrupted()) {
                ClientMessage clientMessage = gson.fromJson(reader, ClientMessage.class);
                updateActivity();

                try {
                    if (clientMessage.type == null) {
                        sendFailure("message has no type");
                    }
                    handleClientMessage(clientMessage);
                } catch (ClientHandlerException e) {
                    Thread.currentThread().interrupt();
                }
            }
        } catch (JsonSyntaxException e) {
            System.err.println("Error в JsonClientHandler: " + e.getMessage());
        } finally {
            try {
                handleLogOut();
            } catch (ClientHandlerException e) {
                Thread.currentThread().interrupt();
            }
        }
    }


    @Override
    public void handleMessage(ClientMessage clientMessage) throws ClientHandlerException {
        server.broadcastMessage(clientMessage);
        JsonObject json = new JsonObject();
        json.addProperty("type", clientMessage.type);
        json.addProperty("message", clientMessage.message);
        json.addProperty("session", clientMessage.session);
        try {
            sendJson(json);
        } catch (JsonClientHandlerException e) {
            throw new ClientHandlerException(e.getMessage(), e);
        }
    }


    @Override
    public void handleLogIn(ClientMessage clientMessage) throws ClientHandlerException {
        setLoggingData(username, server.generateSessionID());
        sendSuccess("successfully logged in", sessionId);
        server.broadcastUserLogin(username, sessionId);
    }

    @Override
    public void handleLogOut() throws ClientHandlerException {
        JsonObject json = new JsonObject();
        json.addProperty("type", "logout");
        json.addProperty("message", "user logged out");
        try {
            sendJson(json);
        } catch (JsonClientHandlerException e) {
            throw new ClientHandlerException(e.getMessage(), e);
        }
    }

    @Override
    public void handleListRequest() throws ClientHandlerException {
        JsonObject response = new JsonObject();
        response.addProperty("type", "success");

        JsonArray listUsers = new JsonArray();

        List<ClientHandler> loggedInClients = server.getLoggedInClients();
        for (ClientHandler client : loggedInClients) {
            JsonObject user = new JsonObject();
            user.addProperty("name", client.getUsername());
            user.addProperty("type", client.getClientType());
            listUsers.add(user);
        }
        response.add("listusers", listUsers);
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
    public void sendSuccess(String message) throws ClientHandlerException {
        JsonObject json = new JsonObject();
        json.addProperty("type", "success");
        json.addProperty("message", message);
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
    public void sendLoginInformation(String username, String sessionId) throws ClientHandlerException {
        JsonObject json = new JsonObject();
        json.addProperty("type", "message");
        json.addProperty("message", "User logged" + username);
        try {
            sendJson(json);
        } catch (JsonClientHandlerException e) {
            throw new ClientHandlerException(e.getMessage(), e);
        }
    }

    @Override
    public void sendMessage(ClientMessage clientMessage) throws ClientHandlerException {
        JsonObject json = new JsonObject();
        json.addProperty("type", "message");
        json.addProperty("name", clientMessage.name);
        json.addProperty("message", clientMessage.message);
        json.addProperty("session", clientMessage.session);
        try {
            sendJson(json);
        } catch (JsonClientHandlerException e) {
            throw new ClientHandlerException(e.getMessage(), e);
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