package commands;

import context.Context;
import exceptions.CommandExceptions;
import exceptions.StackException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DivisionTests {
    private Division division;
    private Context context;

    @BeforeEach
    void setUp() {
        context = new Context();
        division = new Division();
    }

    @Test
    void divExceptionTest() {
        context.getStack().push(5.0);
        Exception exception = assertThrows(StackException.class, () -> division.apply(context));
        assertEquals("Not enough elements in stack", exception.getMessage());
    }

    @Test
    void divByZeroTest() {
        context.getStack().push(5.0);
        context.getStack().push(0.0);
        Exception exception = assertThrows(CommandExceptions.class, () -> division.apply(context));
        assertEquals("Division by zero", exception.getMessage());
    }

    @Test
    void divTest() throws CommandExceptions {
        context.getStack().push(40.0);
        context.getStack().push(8.0);
        division.apply(context);
        assertEquals(5.0, context.getStack().pop());
    }
}
