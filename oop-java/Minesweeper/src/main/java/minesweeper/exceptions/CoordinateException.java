package minesweeper.exceptions;

public class CoordinateException extends RuntimeException {
    public CoordinateException(String message) {
        super(message);
    }
    public CoordinateException(String message, Throwable cause) {
        super(message,cause);
    }

}
