package exceptions;

public class ArithmeticException extends CalculatorException {
    public ArithmeticException(String message) {
        super(message);
    }
    public ArithmeticException(String message, Throwable cause){
        super(message,cause);
    }
}
