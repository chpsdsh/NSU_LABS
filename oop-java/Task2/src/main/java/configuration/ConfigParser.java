package configuration;

import java.io.*;
import java.util.Arrays;
import java.util.function.BiConsumer;

public final class ConfigParser {
    private final String configFileName;

    public ConfigParser(String configFileName) {
        this.configFileName = configFileName;
    }

    public void ParseAndExecute(BiConsumer<String, String[]> commandProcessor) throws IOException {
        String line;
        String[] commandParts;
        if (configFileName != null) {
            try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(configFileName);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

                while ((line = reader.readLine()) != null) {
                    commandParts = parseLine(line);
                    commandProcessor.accept(commandParts[0], Arrays.copyOfRange(commandParts, 1, commandParts.length));
                }
            } catch (IOException e) {
                throw new IOException("No such configuration file" + configFileName);
            }
        } else {
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