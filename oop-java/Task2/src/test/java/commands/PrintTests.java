package commands;

import context.Context;
import exceptions.CommandExceptions;
import exceptions.StackException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class PrintTests {
    private Print print;
    private Context context;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();


    @BeforeEach
    void setUp() {
        context = new Context();
        print = new Print();
        System.setOut(new PrintStream(outputStream)); // Перенаправляем System.out

    }

    @Test
    void printExceptionTest() {
        Exception exception = assertThrows(StackException.class, () -> print.apply(context));
        assertEquals("Empty stack nothing to print", exception.getMessage());
    }

    @Test
    void printTest() throws CommandExceptions {
        context.getStack().push(5.0);
        print.apply(context);
        assertEquals("5.0" + System.lineSeparator(), outputStream.toString());
    }
}
