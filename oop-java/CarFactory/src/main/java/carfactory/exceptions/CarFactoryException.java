package carfactory.exceptions;

public class CarFactoryException extends Exception {
    public CarFactoryException(String message){
        super(message);
    }
    public CarFactoryException(String message, Throwable cause){
        super(message,cause);
    }
}
