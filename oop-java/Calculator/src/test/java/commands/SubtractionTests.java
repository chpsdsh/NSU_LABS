package commands;

import context.Context;
import exceptions.StackException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SubtractionTests {
    private Subtraction subtraction;
    private Context context;

    @BeforeEach
    void setUp() {
        context = new Context();
        subtraction = new Subtraction();
    }

    @Test
    void subExceptionTest() {
        context.getStack().push(5.0);
        Exception exception = assertThrows(StackException.class, () -> subtraction.apply(context));
        assertEquals("Not enough elements in stack", exception.getMessage());
    }

    @Test
    void subTest() throws ArithmeticException {
        context.getStack().push(5.0);
        context.getStack().push(8.0);
        subtraction.apply(context);
        assertEquals(-3.0, context.getStack().pop());
    }
}
