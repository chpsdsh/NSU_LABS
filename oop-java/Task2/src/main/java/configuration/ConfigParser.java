package configuration;

import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public final class ConfigParser {
    private final String configFileName;
    private final List<List<String>> commands;

    public ConfigParser(String configFileName) {
        this.configFileName = configFileName;
        commands = new ArrayList<>();
    }

    public void ParseConfig() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(configFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                String[] parts = line.split(" ");
                commands.add(Arrays.asList(parts));
            }
        } catch (IOException e) {
            throw new IOException("Error while reading file: " + configFileName, e);
        }
    }
}