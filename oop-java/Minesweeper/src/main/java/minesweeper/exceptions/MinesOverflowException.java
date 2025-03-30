package minesweeper.exceptions;

public class MinesOverflowException extends GameModelException {
    public MinesOverflowException(String message) {
        super(message);
    }
    public MinesOverflowException(String message, Throwable cause) {
        super(message, cause);
    }

}
