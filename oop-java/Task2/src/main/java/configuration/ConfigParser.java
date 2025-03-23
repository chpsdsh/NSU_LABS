package configuration;

import java.io.*;
import java.util.Arrays;
import java.util.function.BiConsumer;

import exceptions.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ConfigParser {

    private static final Logger logger = LogManager.getLogger(ConfigParser.class);

    private final String configFileName;
    private final InputStream customInputStream;

    public ConfigParser(String configFileName) {
        this.configFileName = configFileName;
        this.customInputStream = null;
    }

    public ConfigParser(String configFileName, InputStream customInputStream) {
        this.configFileName = configFileName;
        this.customInputStream = customInputStream;
    }

    public void ParseAndExecute(BiConsumer<String, String[]> commandProcessor) throws ParserException {
        logger.info("Config parsing is started {}", configFileName);
        String line;
        String[] commandParts;
        InputStream inputStream;
        if (configFileName != null) {
            if (customInputStream != null) {
                inputStream = customInputStream;
            } else {
                inputStream = getClass().getClassLoader().getResourceAsStream(configFileName);
            }
            if (inputStream == null) {
                logger.error("Can not create inputStream from {}", configFileName);
                throw new ParserConfigLoadException("Configuration file not found" + configFileName);
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                while ((line = reader.readLine()) != null) {
                    commandParts = parseLine(line);
                    if (commandParts != null) {
                        commandProcessor.accept(commandParts[0], Arrays.copyOfRange(commandParts, 1, commandParts.length));
                    }
                }
            } catch (IOException e) {
                throw new ParserConfigException("Error reading configuration file");
            }
        } else {
            logger.info("Switching to console mode");
            System.out.println("Console mode write EXIT to finish");
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
                while ((line = reader.readLine()) != null) {
                    if (line.equals("EXIT")) {
                        break;
                    }
                    commandParts = parseLine(line);
                    if (commandParts != null) {
                        commandProcessor.accept(commandParts[0], Arrays.copyOfRange(commandParts, 1, commandParts.length));
                    }
                }
            } catch (IOException e) {
                throw new ParserException("Error parsing Console input");
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