package commands;

import context.Context;
import exceptions.InvalidParameterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PushTests {
    private Push push;
    private Context context;

    @BeforeEach
    void setUp() {
        context = new Context();
    }

    @Test
    void pushConstructorTest() throws ArithmeticException {
        String[] invArgs = {"a", "b"};
        Exception exception = assertThrows(InvalidParameterException.class, () -> new Push(invArgs));
        assertEquals("More then one parameter in args", exception.getMessage());
        String[] args = {"a"};
        push = new Push(args);
        assertEquals("a", push.getParameter());
    }

    @Test
    void pushTest() throws ArithmeticException {
        context.getVariables().put("a", 10.0);
        String[] invArgs = {"b"};
        push = new Push(invArgs);
        Exception exception = assertThrows(InvalidParameterException.class, () -> push.apply(context));
        assertEquals("Invalid parameter for PUSH b", exception.getMessage());
        String[] argsVar = {"a"};
        push = new Push(argsVar);
        push.apply(context);
        assertEquals(10.0, context.getStack().pop());
        String[] argsNum = {"12.0"};
        push = new Push(argsNum);
        push.apply(context);
        assertEquals(12.0, context.getStack().pop());
    }
}
