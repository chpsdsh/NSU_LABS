package onlinechat.exceptions;

public class ClientException extends ChatException {
    public ClientException(String message, Throwable cause) {
        super(message,cause);
    }
}
