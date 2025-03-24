package minesweeper.exceptions;

public class BadGameSettingsParameter extends GameModelException {
    public BadGameSettingsParameter(String message) {
        super(message);
    }
    public BadGameSettingsParameter(String message, Throwable cause) {
        super(message, cause);
    }
}
