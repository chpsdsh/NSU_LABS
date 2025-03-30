package minesweeper.exceptions;

public class NegativeMinesNumberException extends GameModelException {
    public NegativeMinesNumberException(String message) {
        super(message);
    }
    public NegativeMinesNumberException(String message, Throwable cause) {
        super(message, cause);
    }

}
