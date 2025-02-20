package configuration;

import java.io.*;
import java.util.Arrays;
import java.util.function.BiConsumer;

public final class ConfigParser {
    private final InputStream inputStream;

    public ConfigParser(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void ParseAndExecute(BiConsumer<String, String[]> commandProcessor) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                String[] parts = line.split(" ");
                commandProcessor.accept(parts[0], Arrays.copyOfRange(parts, 1, parts.length));
            }
        } catch (IOException e) {
            throw new IOException("Error while reading file: ", e);
        }
    }
}