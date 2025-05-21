package onlinechat.client;


import com.google.gson.Gson;
import onlinechat.exceptions.ChatException;
import onlinechat.server.ClientMessage;

import java.io.*;
import java.net.Socket;

public class JsonClient {
    private final Socket socket;
    private final BufferedReader in;
    private final BufferedWriter out;
    private String name;
    private final Gson gson = new Gson();


    public static void main(String[] args) throws ChatException {
        try {
            new JsonClient();
        } catch (IOException e) {
            throw new ChatException("Error connecting to server", e);
        }
    }

    public JsonClient() throws IOException {
        socket = new Socket("localhost", 1234);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        out.write("PROTOCOL:JSON");

        Thread messageHandler = new Thread(this::messageListener);


    }

    private void messageListener() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                String line = in.readLine();
                ClientMessage clientMessage = gson.fromJson(line, ClientMessage.class);
                handleMessage(clientMessage);
            } catch (IOException e) {
                System.out.println("Error reading socket");
            }
        }
    }

    public void handleMessage(ClientMessage clientMessage) {
        switch (clientMessage.type) {
            case "message" -> readMessage(clientMessage);
            case "success" -> System.out.println("[SUCCESS]" + clientMessage.message);
            case "error" -> System.out.println("[ERROR]" + clientMessage.message);
            case "disconnect" -> System.out.println("[DISCONNECT]" + clientMessage.message);
        }
    }

    private void readMessage(ClientMessage clientMessage) {
        if (clientMessage.name != null) {
            System.out.println(clientMessage.name + ": " + clientMessage.message);
        }
        else {
            System.out.println(clientMessage.message);
        }
    }

}