package exceptions;

public class ParserException extends CalculatorException {
    public ParserException(String message) {
        super(message);
    }
    public ParserException(String message, Throwable cause){
        super(message,cause);
    }
}
