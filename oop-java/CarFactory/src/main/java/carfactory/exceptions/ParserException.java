package carfactory.exceptions;

public class ParserException extends CarFactoryException {
    public ParserException(String message){
      super(message);
    }
    public ParserException(String message, Throwable cause) {
        super(message,cause);
    }
}
