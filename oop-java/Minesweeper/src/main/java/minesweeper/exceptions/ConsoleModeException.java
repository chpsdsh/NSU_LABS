package minesweeper.exceptions;

public class ConsoleModeException extends MinesweeperException{
    public ConsoleModeException(String message) {
        super(message);
    }
    public ConsoleModeException(String message, Throwable cause) {
        super(message, cause);
    }
}
