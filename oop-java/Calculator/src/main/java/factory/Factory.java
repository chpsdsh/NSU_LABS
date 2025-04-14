package factory;

import commands.Command;
import exceptions.*;
import exceptions.ClassNotFoundException;
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

    public Factory(String factoryConfigurationFileName) throws FactoryException {
        loadClassMap(factoryConfigurationFileName);
    }

    public void loadClassMap(String factoryConfigurationFileName) throws FactoryException {
        logger.info("Factory config parsing is started {}", factoryConfigurationFileName);

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(factoryConfigurationFileName);
        if (inputStream == null) {
            logger.error("Could not create inputStream from {}", factoryConfigurationFileName);
            throw new FactoryConfigLoadException("Could not open factory configuration file " + factoryConfigurationFileName);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                String[] parts = line.split("=");
                if (parts.length != 2) {
                    logger.error("Invalid config format");
                    throw new FactoryConfigFormatException("Invalid config file format");
                }
                commandMap.put(parts[0], parts[1]);
            }
        } catch (IOException e) {
            logger.error("Error while reading file {}", factoryConfigurationFileName);
            throw new FactoryConfigFormatException("Error while reading file" + factoryConfigurationFileName);
        }
    }

    public Command createCommand(String commandName, String[] args) throws FactoryException, ReflectiveOperationException {
        logger.info("Command creation is started {}", commandName);

        String className = commandMap.get(commandName);
        if (className == null) {
            logger.error("Invalid command name");
            throw new ClassNotFoundException("Unknown command: " + commandName);
        }

        Class<?> commandClass = Class.forName(className);
        if (args.length == 0) {
            return (Command) commandClass.getDeclaredConstructor().newInstance();
        } else {
            return (Command) commandClass.getDeclaredConstructor(String[].class).newInstance((Object) args);
        }
    }
}
