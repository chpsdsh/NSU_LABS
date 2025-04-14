package commands;

import context.Context;
import exceptions.StackException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AdditionTests {
    private Addition addition;
    private Context context;

    @BeforeEach
    void setUp() {
        context = new Context();
        addition = new Addition();
    }

    @Test
    void addExceptionTest() {
        context.getStack().push(5.0);
        Exception exception = assertThrows(StackException.class, () -> addition.apply(context));
        assertEquals("Not enough elements in stack", exception.getMessage());
    }

    @Test
    void addTest() throws ArithmeticException {
        context.getStack().push(5.0);
        context.getStack().push(8.0);
        addition.apply(context);
        assertEquals(13.0, context.getStack().pop());
    }
}
