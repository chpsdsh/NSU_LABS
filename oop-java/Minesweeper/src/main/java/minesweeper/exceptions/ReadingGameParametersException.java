package minesweeper.exceptions;

public class ReadingGameParametersException extends ConsoleModeException {
    public ReadingGameParametersException(String message, Throwable cause) {
        super(message,cause);
    }
    public ReadingGameParametersException(String message) {
        super(message);
    }
}
