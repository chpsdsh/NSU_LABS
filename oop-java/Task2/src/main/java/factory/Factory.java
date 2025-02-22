package factory;

import commands.Command;
import exceptions.InvalidCommandException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public final class Factory {
    private final Map<String, String> commandMap = new HashMap<>();

    public Factory(String factoryConfigurationFileName) throws IOException {
        loadClassMap(factoryConfigurationFileName);
    }

    private void loadClassMap(String factoryConfigurationFileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream(factoryConfigurationFileName)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                String[] parts = line.split("=", 2);
                if (parts.length != 2) {
                    throw new IOException("Invalid config file format");
                }
                commandMap.put(parts[0], parts[1]);
            }
        } catch (IOException e) {
            throw new IOException("Error while reading file" + factoryConfigurationFileName);
        }
    }

    public Command createCommand(String commandName, String[] args) throws Exception {
        String className = commandMap.get(commandName);
        if (className == null) {
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
