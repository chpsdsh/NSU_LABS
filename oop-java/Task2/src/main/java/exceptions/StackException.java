package exceptions;

import exceptions.CommandExceptions;

public class StackException extends CommandExceptions {
    public StackException(String message) {
        super(message);
    }

    public StackException(String message, Throwable cause) {
        super(message, cause);
    }
}