package commands;

import context.Context;
import exceptions.CommandExceptions;
import exceptions.StackException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PopTests {
    private Pop pop;
    private Context context;

    @BeforeEach
    void setUp() {
        context = new Context();
        pop = new Pop();
    }

    @Test
    void popExceptionTest() {
        Exception exception = assertThrows(StackException.class, () -> pop.apply(context));
        assertEquals("Stack is empty nothing to POP", exception.getMessage());
    }

    @Test
    void popTest() throws CommandExceptions {
        context.getStack().push(5.0);
        int size = context.getStack().size();
        pop.apply(context);
        assertEquals(size-1,context.getStack().size());
    }
}
