package factory;

import commands.Command;
import exceptions.InvalidCommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public final class Factory {
    private final Map<String, String> commandMap = new HashMap<>();

    private static final Logger logger = LogManager.getLogger(Factory.class);

    public Factory(String factoryConfigurationFileName) throws IOException {
        loadClassMap(factoryConfigurationFileName);
    }

    private void loadClassMap(String factoryConfigurationFileName) throws IOException {
        logger.info("Config parsing is started {}", factoryConfigurationFileName);

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(factoryConfigurationFileName);
        if (inputStream == null) {
            logger.error("Can not create inputStream from {}", factoryConfigurationFileName);
            throw new IOException("Could not open factory configuration file" + factoryConfigurationFileName);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                String[] parts = line.split("=", 2);
                if (parts.length != 2) {
                    logger.error("Invalid config format");
                    throw new IOException("Invalid config file format");
                }
                commandMap.put(parts[0], parts[1]);
            }
        } catch (IOException e) {
            logger.error("Error while reading file {}", factoryConfigurationFileName);
            throw new IOException("Error while reading file" + factoryConfigurationFileName);
        }
    }

    public Command createCommand(String commandName, String[] args) throws Exception {
        logger.info("Command creation is started {}",commandName);

        String className = commandMap.get(commandName);
        if (className == null) {
            logger.error("Invalid command name");
            throw new InvalidCommandException("Command not found " + commandName);
        }
        Class<?> commandClass = Class.forName(className);
        if (args == null || args.length == 0) {
            return (Command) commandClass.getDeclaredConstructor().newInstance();
        } else {
            return (Command) commandClass.getDeclaredConstructor(String[].class).newInstance((Object) args);
        }
    }
}
