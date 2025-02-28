package commands;

import context.Context;
import exceptions.CommandExceptions;
import exceptions.InvalidParameterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DefinitionTests {
    private Definition definition;
    private Context context;

    @BeforeEach
    void setDefinition() {
        context = new Context();
    }

    @Test
    void definitionConstructorTests() throws CommandExceptions {
        String[] notEnough = {"a"};
        Exception exception = assertThrows(InvalidParameterException.class, () -> new Definition(notEnough));
        assertEquals("Not enough arguments", exception.getMessage());
        String[] invValue = {"a", "a"};
        exception = assertThrows(InvalidParameterException.class, () -> new Definition(invValue));
        assertEquals("Invalid value for DEFINE a", exception.getMessage());
        String[] realArgs = {"a", "10.0"};
        definition = new Definition(realArgs);
        assertEquals("a", definition.getVariable());
        assertEquals(10.0, definition.getValue());
    }

    @Test
    void definitionTest() throws CommandExceptions {
        context.getVariables().put("a", 2.0);
        String[] exceptionArgs = {"a", "10.0"};
        definition = new Definition(exceptionArgs);
        Exception exception = assertThrows(InvalidParameterException.class, () -> definition.apply(context));
        assertEquals("Variable a already exists", exception.getMessage());
        String[] realArgs = {"b", "7.0"};
        definition = new Definition(realArgs);
        definition.apply(context);
        assertTrue(context.getVariables().containsKey("b"));
    }
}
