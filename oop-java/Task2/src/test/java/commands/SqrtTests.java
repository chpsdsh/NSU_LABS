package commands;

import context.Context;
import exceptions.CommandExceptions;
import exceptions.StackException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SqrtTests {
    private Sqrt sqrt;
    private Context context;

    @BeforeEach
    void setUp() {
        context = new Context();
        sqrt = new Sqrt();
    }

    @Test
    void sqrtStackExceptionTest() {
        Exception exception = assertThrows(StackException.class, () -> sqrt.apply(context));
        assertEquals("Not enough elements in stack", exception.getMessage());
    }

    @Test
    void sqrtOperandExceptionTest() {
        context.getStack().push(-10.0);
        Exception exception = assertThrows(RuntimeException.class, () -> sqrt.apply(context));
        assertEquals("Trying to get a SQRT of a negative value " + "-10.0", exception.getMessage());
    }

    @Test
    void sqrtTest() throws CommandExceptions {
        context.getStack().push(16.0);
        sqrt.apply(context);
        assertEquals(4, context.getStack().pop());
    }
}
