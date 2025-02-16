package exceptions;

import exceptions.CommandExceptions;

public class InvalidCommandException extends CommandExceptions {
    public InvalidCommandException(String message) {
        super(message);
    }

    public InvalidCommandException(String message, Throwable cause) {
        super(message, cause);
    }
}