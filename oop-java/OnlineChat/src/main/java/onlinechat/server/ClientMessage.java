package onlinechat.server;

import java.io.Serializable;

public class ClientMessage implements Serializable {
    public String type;
    public String name;
    public String message;
    public String session;
}
