package exceptions;

public class ClassNotFoundException extends FactoryException {
    public ClassNotFoundException(String message) {
        super(message);
    }
}
