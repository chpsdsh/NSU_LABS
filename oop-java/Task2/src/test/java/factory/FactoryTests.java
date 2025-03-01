package factory;

import commands.Command;
import commands.Definition;
import commands.Print;
import exceptions.InvalidCommandException;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class FactoryTests {
    private Factory factory;
    private Command command;


    @Test
    void loadInvalidFactoryConfig() {
        Exception exception = assertThrows(IOException.class, () -> new Factory("invalid.txt"));
        assertTrue(exception.getMessage().contains("Could not open factory configuration file"));
    }

    @Test
    void testInvalidConfigFormat() {
        Exception exception = assertThrows(IOException.class, () -> new Factory("invalidConfig.txt"));
        assertTrue(exception.getMessage().contains("Error while reading file"));
    }

    @Test
    void createCommandTest() throws Exception {
        factory = new Factory("factoryConfiguration.txt");
        String InvalidCommand = "GET";
        Exception exception = assertThrows(InvalidCommandException.class, () -> command = factory.createCommand(InvalidCommand, null));
        assertTrue(exception.getMessage().contains("Command not found"));

        String commandWithArgsName = "DEFINE";
        String[] args = {"a", "4.0"};
        command = factory.createCommand(commandWithArgsName, args);
        assertNotNull(command);
        assertInstanceOf(Definition.class, command);

        String commandWithoutArgsName = "PRINT";
        command = factory.createCommand(commandWithoutArgsName, null);
        assertNotNull(command);
        assertInstanceOf(Print.class, command);
    }


}
