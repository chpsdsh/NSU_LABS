package factory;

import commands.Command;
import commands.Definition;
import commands.Sqrt;
import exceptions.ClassNotFoundException;
import exceptions.FactoryConfigFormatException;
import exceptions.FactoryConfigLoadException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FactoryTests {
    private Factory factory;
    private Command command;


    @Test
    void loadInvalidFactoryConfig() {
        Exception exception = assertThrows(FactoryConfigLoadException.class, () -> new Factory("invalid.txt"));
        assertTrue(exception.getMessage().contains("Could not open factory configuration file"));
    }

    @Test
    void testInvalidConfigFormat() {
        Exception exception = assertThrows(FactoryConfigFormatException.class, () -> new Factory("invalidConfig.txt"));
        assertTrue(exception.getMessage().contains("Invalid config file format"));
    }

    @Test
    void createCommandTest() throws Exception {
        factory = new Factory("factoryConfiguration.txt");
        String InvalidCommand = "GET";
        Exception exception = assertThrows(ClassNotFoundException.class, () -> command = factory.createCommand(InvalidCommand, null));
        assertTrue(exception.getMessage().contains("Unknown command:"));

        String commandWithArgsName = "DEFINE";
        String[] args = {"a", "4.0"};
        command = factory.createCommand(commandWithArgsName, args);
        assertNotNull(command);
        assertInstanceOf(Definition.class, command);

        String commandWithoutArgsName = "SQRT";
        String[] argsEmpty = {};
        command = factory.createCommand(commandWithoutArgsName, argsEmpty);
        assertNotNull(command);
        assertInstanceOf(Sqrt.class, command);
    }


}
