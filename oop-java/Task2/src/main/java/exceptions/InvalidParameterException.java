package exceptions;

import exceptions.CommandExceptions;

public class InvalidParameterException extends CommandExceptions {
    public InvalidParameterException(String message) {
        super(message);
    }

    public InvalidParameterException(String message, Throwable cause) {
        super(message, cause);
    }
}