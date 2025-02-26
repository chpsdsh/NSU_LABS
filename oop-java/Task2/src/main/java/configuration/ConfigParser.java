package configuration;

import java.io.*;
import java.util.Arrays;
import java.util.function.BiConsumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class ConfigParser {

    private static final Logger logger = LogManager.getLogger(ConfigParser.class);

    private final String configFileName;

    public ConfigParser(String configFileName) {
        this.configFileName = configFileName;
    }

    public void ParseAndExecute(BiConsumer<String, String[]> commandProcessor) throws IOException {
        logger.info("Config parsing is started {}", configFileName);
        String line;
        String[] commandParts;
        if (configFileName != null) {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(configFileName);
            if (inputStream == null) {
                logger.error("Can not create inputStream from {}", configFileName);
                throw new IOException("Configuration file not found" + configFileName);
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                while ((line = reader.readLine()) != null) {
                    commandParts = parseLine(line);
                    if (commandParts != null) {
                        commandProcessor.accept(commandParts[0], Arrays.copyOfRange(commandParts, 1, commandParts.length));
                    }
                }
            } catch (Exception e) {
                logger.error("Can not create BufferedReader from {}", configFileName);
                throw new IOException("Error reading console", e);
            }
        } else {
            logger.info("Switching to console mode");
            System.out.println("Console mode write EXIT to finish");
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
                while ((line = reader.readLine()) != null) {
                    if (line.equals("EXIT")) break;
                    commandParts = parseLine(line);
                    if (commandParts != null) {
                        commandProcessor.accept(commandParts[0], Arrays.copyOfRange(commandParts, 1, commandParts.length));
                    }
                }
            } catch (Exception e) {
                logger.error("Error reading console");
                throw new IOException("Error reading console", e);
            }
        }
    }

    private String[] parseLine(String line) {
        line = line.trim();
        if (line.isEmpty() || line.startsWith("#")) {
            return null;
        }
        return line.split(" ");
    }
}