package minesweeper.exceptions;

public class HighScoreHandlingException extends MinesweeperException {
    public HighScoreHandlingException(String message) {
        super(message);
    }
    public HighScoreHandlingException(String message, Throwable cause) {
        super(message, cause);
    }

}
