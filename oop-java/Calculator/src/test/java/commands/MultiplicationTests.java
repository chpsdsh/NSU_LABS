package commands;

import context.Context;
import exceptions.StackException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MultiplicationTests {
    private Multiplication multiplication;
    private Context context;

    @BeforeEach
    void setUp() {
        context = new Context();
        multiplication = new Multiplication();
    }

    @Test
    void mulExceptionTest() {
        context.getStack().push(5.0);
        Exception exception = assertThrows(StackException.class, () -> multiplication.apply(context));
        assertEquals("Not enough elements in stack", exception.getMessage());
    }

    @Test
    void mulTest() throws ArithmeticException {
        context.getStack().push(5.0);
        context.getStack().push(8.0);
        multiplication.apply(context);
        assertEquals(40.0, context.getStack().pop());
    }
}
