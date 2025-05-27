package onlinechat.clients;


import com.google.gson.*;
import onlinechat.exceptions.ChatException;
import onlinechat.exceptions.JsonClientException;
import onlinechat.server.ClientMessage;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;


public class JsonClient {
    private final Socket socket;
    private final BufferedReader in;
    private final BufferedWriter out;
    private String name;
    private String sessionId;
    private boolean isLoggedIn = false;
    private final Gson gson = new Gson();
    private final Thread messageHandler;
    private final Thread messageWriter;

    public static void main(String[] args) throws ChatException {
        try {
            new JsonClient();
        } catch (IOException e) {
            throw new ChatException("Error connecting to server", e);
        }
    }

    public JsonClient() throws IOException {
        long pid = ProcessHandle.current().pid();
        System.out.println("PID: " + pid);

        socket = new Socket("localhost", 1234);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        out.write("PROTOCOL:JSON");
        out.newLine();
        out.flush();
        messageHandler = new Thread(this::messageListening);
        messageHandler.start();
        messageWriter = new Thread(this::messageWriting);
        messageWriter.start();
    }

    private void messageWriting() {
        Scanner scanner = new Scanner(System.in);
        while (!Thread.currentThread().isInterrupted()) {
            String input = scanner.nextLine();

            switch (input) {
                case "/disconnect" -> {
                    try {
                        sendDisconnect();
                        closeClient();
                    } catch (JsonClientException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                case "/login" -> {
                    try {
                        sendLogin();
                        isLoggedIn = true;
                    } catch (JsonClientException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                case "/list" -> {
                    try {
                        sendList();
                    } catch (JsonClientException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                default -> {
                    if (isLoggedIn) {
                        try {
                            sendMessage(input);
                        } catch (JsonClientException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            }
        }
    }

    private void messageListening() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                String line = in.readLine();
                ClientMessage clientMessage = gson.fromJson(line, ClientMessage.class);
                handleMessage(clientMessage);
            } catch (IOException | JsonClientException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void handleMessage(ClientMessage clientMessage) throws JsonClientException {
        switch (clientMessage.type) {
            case "message" -> readMessage(clientMessage);
            case "success" -> {
                if (clientMessage.session != null) {
                    sessionId = clientMessage.session;
                }
                System.out.println("[SUCCESS]" + clientMessage.message);
            }
            case "error" -> {
                System.out.println("[ERROR]" + clientMessage.message);
                closeClient();
            }
            case "logout" -> {
                System.out.println("[LOGOUT] " + clientMessage.name);
                closeClient();
            }
            case "login" -> System.out.println("[LOGIN] " + clientMessage.name);
            case "list" -> readUsersList(clientMessage);
            case "ping" -> replyPing();
            case "disconnect" -> System.out.println("[DISCONNECT] " + clientMessage.name + " " + clientMessage.message);
        }
    }


    private void replyPing() throws JsonClientException {
        ClientMessage clientMessage = new ClientMessage();
        clientMessage.type = "ping";
        String json = gson.toJson(clientMessage);
        try {
            out.write(json);
            out.newLine();
            out.flush();

        } catch (IOException e) {
            throw new JsonClientException("Error sending message", e);
        }
    }

    private void readMessage(ClientMessage clientMessage) {
        if (clientMessage.name != null) {
            System.out.println(clientMessage.name + ": " + clientMessage.message);
        } else {
            System.out.println(clientMessage.message);
        }
    }

    private void readUsersList(ClientMessage clientMessage) {
        JsonArray listUsers = JsonParser.parseString(clientMessage.message).getAsJsonArray();
        for (JsonElement elem : listUsers) {
            JsonObject user = elem.getAsJsonObject();
            System.out.println(user.get("name").getAsString());
        }
    }

    private void sendMessage(String message) throws JsonClientException {
        ClientMessage clientMessage = new ClientMessage();
        clientMessage.type = "message";
        clientMessage.name = name;
        clientMessage.message = message;
        clientMessage.session = sessionId;
        String json = gson.toJson(clientMessage);
        try {
            out.write(json);
            out.newLine();
            out.flush();
        } catch (IOException e) {
            throw new JsonClientException("Error sending message", e);
        }
    }

    private void sendDisconnect() throws JsonClientException {
        ClientMessage clientMessage = new ClientMessage();
        clientMessage.type = "disconnect";
        clientMessage.message = "disconnected";
        clientMessage.name = name;
        clientMessage.session = sessionId;
        String json = gson.toJson(clientMessage);
        try {
            out.write(json);
            out.newLine();
            out.flush();
        } catch (IOException e) {
            throw new JsonClientException("Exception while disconnecting", e);
        }

    }

    private void sendLogin() throws JsonClientException {
        System.out.println("Write your nickname");
        BufferedReader nameReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            name = nameReader.readLine();
        } catch (IOException e) {
            throw new JsonClientException("Error reading nickname", e);
        }
        ClientMessage clientMessage = new ClientMessage();
        clientMessage.type = "login";
        clientMessage.name = name;
        String json = gson.toJson(clientMessage);
        try {
            out.write(json);
            out.newLine();
            out.flush();
        } catch (IOException e) {
            throw new JsonClientException("Error sending login message", e);
        }
    }

    private void sendList() throws JsonClientException {
        ClientMessage clientMessage = new ClientMessage();
        clientMessage.type = "list";
        clientMessage.name = name;
        clientMessage.session = sessionId;
        String json = gson.toJson(clientMessage);
        try {
            out.write(json);
            out.newLine();
            out.flush();
        } catch (IOException e) {
            throw new JsonClientException("Exception while disconnecting", e);
        }
    }

    private void closeClient() {
        try {
            socket.close();
            in.close();
            out.close();
        } catch (IOException ignored) {
        }
        messageHandler.interrupt();
        messageWriter.interrupt();
        System.exit(0);
    }
}