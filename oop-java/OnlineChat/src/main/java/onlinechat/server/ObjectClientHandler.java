package onlinechat.server;

import onlinechat.exceptions.ClientHandlerException;

import java.net.Socket;

public class ObjectClientHandler extends ClientHandler{

    public ObjectClientHandler(Socket socket, Server server){
        super(socket,server);
    }

    @Override
    public void sendMessage(ClientMessage clientMessage){

    }
    @Override
    public void run(){

    }

    @Override
    public void handleLogIn(ClientMessage clientMessage) throws ClientHandlerException{}
    @Override
    public void handleLogOut() throws ClientHandlerException{}
    @Override
    public void handleListRequest() throws ClientHandlerException{}
    @Override
    public void sendSuccess(String message) throws ClientHandlerException{}
    @Override
    public void sendFailure(String message) throws ClientHandlerException{}
    @Override
    public void sendLoginInformation(String username, String sessionId) throws ClientHandlerException{}

}
