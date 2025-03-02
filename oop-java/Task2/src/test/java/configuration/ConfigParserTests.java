package configuration;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.io.IOException;
import java.util.function.BiConsumer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class ConfigParserTests {

    private BiConsumer<String, String[]> commandProcessor;

    @BeforeEach
    void setUp() {
        commandProcessor = mock(BiConsumer.class);
    }

    @Test
    void testInvalidConfigurationFileName() {
        ConfigParser confParser = new ConfigParser("invalid.txt");
        Exception exception = assertThrows(IOException.class, () -> confParser.ParseAndExecute(commandProcessor));
        assertTrue(exception.getMessage().contains("Configuration file not found"));
    }

    @Test
    void testConfigFileProcessing() throws IOException {
        String configData = "#Comment\n\nDEFINE a 4\nPUSH a\nSQRT\nPRINT\n#Comment";
        InputStream mockInputStream = new ByteArrayInputStream(configData.getBytes());
        ConfigParser parser = new ConfigParser("mockConfig.txt", mockInputStream);
        parser.ParseAndExecute(commandProcessor);
        verify(commandProcessor, times(4)).accept(anyString(), any(String[].class));
    }

    @Test
    void testConsoleMode() throws IOException {
        String consoleInput = "#Comment\n\nPUSH 10\nPRINT\nEXIT";
        InputStream mockInputStream = new ByteArrayInputStream(consoleInput.getBytes());
        System.setIn(mockInputStream);
        ConfigParser configParser = new ConfigParser(null);
        configParser.ParseAndExecute(commandProcessor);
        verify(commandProcessor, times(2)).accept(anyString(), any(String[].class));
        System.setIn(System.in);
    }

    @Test
    void testConsoleEXIT() throws IOException {
        String consoleInput = "EXIT\n";
        InputStream mockInputStream = new ByteArrayInputStream(consoleInput.getBytes());
        System.setIn(mockInputStream);
        ConfigParser configParser = new ConfigParser(null);
        configParser.ParseAndExecute(commandProcessor);
        verify(commandProcessor, never()).accept(anyString(), any(String[].class));
        System.setIn(System.in);
    }
}
