package minesweeper.exceptions;

public class MinesweeperException extends RuntimeException {
    public MinesweeperException(String message, Throwable cause) {
        super(message, cause);
    }

    public MinesweeperException(String message) {
        super(message);
    }
}
