package minesweeper.exceptions;

public class GameModelException extends MinesweeperException {
    public GameModelException(String message) {
        super(message);
    }
    public GameModelException(String message, Throwable cause) {
        super(message, cause);
    }
}
