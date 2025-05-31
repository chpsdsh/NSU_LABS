package onlinechat.clients;

import onlinechat.exceptions.ChatException;
import onlinechat.exceptions.ObjectClientException;
import onlinechat.server.ClientMessage;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;


public class ObjectClient {
    private final Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String name;
    private String sessionId;
    private boolean isLoggedIn = false;
    private final Thread messageHandler;
    private final Thread messageWriter;

    public static void main(String[] args) throws ChatException {
        try {
            new ObjectClient();
        } catch (IOException e) {
            throw new ChatException("Error connecting to server", e);
        }
    }

    public ObjectClient() throws IOException {
        long pid = ProcessHandle.current().pid();
        System.out.println("PID: " + pid);

        socket = new Socket("localhost", 1234);

        BufferedWriter typeWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        typeWriter.write("PROTOCOL:OBJECT");
        typeWriter.newLine();
        typeWriter.flush();

        out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();
        in = new ObjectInputStream(socket.getInputStream());

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
                    } catch (ObjectClientException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                case "/login" -> {
                    try {
                        sendLogin();
                        isLoggedIn = true;
                    } catch (ObjectClientException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                case "/list" -> {
                    try {
                        sendList();
                    } catch (ObjectClientException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                default -> {
                    if (isLoggedIn) {
                        try {
                            sendMessage(input);
                        } catch (ObjectClientException e) {
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
                ClientMessage clientMessage = (ClientMessage) in.readObject();
                handleMessage(clientMessage);
            } catch (IOException | ClassNotFoundException | ObjectClientException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void handleMessage(ClientMessage clientMessage) throws ObjectClientException {
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
                System.out.println("[LOGOUT] " + clientMessage.name + " " + clientMessage.message);
                closeClient();
            }
            case "login" -> System.out.println("[LOGIN] " + clientMessage.name);
            case "list" -> readUsersList(clientMessage);
            case "ping" -> replyPing();
            case "disconnect" -> System.out.println("[DISCONNECT] " + clientMessage.name + " " + clientMessage.message);
        }
    }


    private void replyPing() throws ObjectClientException {
        ClientMessage clientMessage = new ClientMessage();
        clientMessage.type = "ping";
        try {
            out.writeObject(clientMessage);
            out.flush();
        } catch (IOException e) {
            throw new ObjectClientException("Error sending message", e);
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
        System.out.println(clientMessage.message);
    }

    private void sendMessage(String message) throws ObjectClientException {
        ClientMessage clientMessage = new ClientMessage();
        clientMessage.type = "message";
        clientMessage.name = name;
        clientMessage.message = message;
        clientMessage.session = sessionId;
        try {
            out.writeObject(clientMessage);
            out.flush();
        } catch (IOException e) {
            throw new ObjectClientException("Error sending message", e);
        }
    }

    private void sendDisconnect() throws ObjectClientException {
        ClientMessage clientMessage = new ClientMessage();
        clientMessage.type = "disconnect";
        clientMessage.message = "disconnected";
        clientMessage.name = name;
        clientMessage.session = sessionId;
        try {
            out.writeObject(clientMessage);
            out.flush();
        } catch (IOException e) {
            throw new ObjectClientException("Exception while disconnecting", e);
        }

    }

    private void sendLogin() throws ObjectClientException {
        System.out.println("Write your nickname");
        BufferedReader nameReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            name = nameReader.readLine();
        } catch (IOException e) {
            throw new ObjectClientException("Error reading nickname", e);
        }
        ClientMessage clientMessage = new ClientMessage();
        clientMessage.type = "login";
        clientMessage.name = name;
        try {
            out.writeObject(clientMessage);
            out.flush();
        } catch (IOException e) {
            throw new ObjectClientException("Error sending login message", e);
        }
    }

    private void sendList() throws ObjectClientException {
        ClientMessage clientMessage = new ClientMessage();
        clientMessage.type = "list";
        clientMessage.name = name;
        clientMessage.session = sessionId;
        try {
            out.writeObject(clientMessage);
            out.flush();
        } catch (IOException e) {
            throw new ObjectClientException("Exception while disconnecting", e);
        }
    }

    private void closeClient() {
        try {
            socket.close();
            in.close();
            out.close();
        } catch (IOException ignored) {
        }
//        messageHandler.interrupt();
//        messageWriter.interrupt();
        System.exit(0);
    }
}

