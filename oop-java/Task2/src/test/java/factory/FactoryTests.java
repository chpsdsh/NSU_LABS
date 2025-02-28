package commands;

import context.Context;
import exceptions.CommandExceptions;
import exceptions.StackException;
import factory.Factory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;


import static org.junit.jupiter.api.Assertions.*;

public class FactoryTests {
    private Context context;
    private Factory factory;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();


    @BeforeEach
    void setUp() {
    String mockConfig = "";
    }

    @Test
    void factoryExceptionTest() {
        Exception exception = assertThrows(StackException.class, () -> factory.apply(context));
        assertEquals("Empty stack nothing to factory", exception.getMessage());
    }

    @Test
    void factoryTest() throws CommandExceptions {
        context.getStack().push(5.0);
        factory.apply(context);
        assertEquals("5.0" + System.lineSeparator(), outputStream.toString());
    }
}
