import exceptions.CalculatorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class MainTests {
    @BeforeEach
    void testMainLogsInFile() throws Exception {
        File logFile = new File("logs/app.log");
        if (logFile.exists()) {
            new FileWriter(logFile, false).close();
        }

        String[] args = {"config.txt"};
        Main.main(args);

        StringBuilder logContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(logFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                logContent.append(line).append("\n");
            }
        }

        assertTrue(logContent.toString().contains("Program started"));
        assertTrue(logContent.toString().contains("Program finished"));
    }

    @Test
    void BiConsumerException() {
        String[] args = {"invalidConfig.txt"};
        Exception exception = assertThrows(CalculatorException.class, () -> Main.main(args));
        assertTrue(exception.getMessage().contains("Exception creating command "));
    }

    @Test
    void noArgsTest() throws Exception {
        File logFile = new File("logs/app.log");
        if (logFile.exists()) {
            new FileWriter(logFile, false).close();
        }

        String[] args ={};
        Main.main(args);

        StringBuilder logContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(logFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                logContent.append(line).append("\n");
            }
        }

        assertTrue(logContent.toString().contains("Program started"));
        assertTrue(logContent.toString().contains("Program finished"));
    }
}
