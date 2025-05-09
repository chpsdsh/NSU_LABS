package carfactory.configparser;

import carfactory.exceptions.ParserException;

import java.io.*;

public class ConfigParser {
    private int storageBodySize;
    private int storageEngineSize;
    private int storageAccessorySize;
    private int storageAutoSize;
    private int accessorySuppliers;
    private int workers;
    private int dealers;
    private boolean logSale;

    public int getStorageBodySize() {
        return storageBodySize;
    }

    public int getAccessorySuppliers() {
        return accessorySuppliers;
    }

    public int getDealers() {
        return dealers;
    }

    public int getStorageEngineSize() {
        return storageEngineSize;
    }

    public int getStorageAutoSize() {
        return storageAutoSize;
    }

    public int getStorageAccessorySize() {
        return storageAccessorySize;
    }

    public int getWorkers() {
        return workers;
    }


    public ConfigParser() throws ParserException {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("config.txt")) {
            if (is == null) throw new ParserException("config.txt not found");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");

                if (parts.length != 2) {
                    throw new ParserException("Invalid config format");
                }
                String key = parts[0].trim();
                String value = parts[1].trim();
                try {
                    switch (key) {
                        case "StorageBodySize" -> storageBodySize = Integer.parseInt(value);
                        case "StorageMotorSize" -> storageEngineSize = Integer.parseInt(value);
                        case "StorageAccessorySize" -> storageAccessorySize = Integer.parseInt(value);
                        case "StorageAutoSize" -> storageAutoSize = Integer.parseInt(value);
                        case "AccessorySuppliers" -> accessorySuppliers = Integer.parseInt(value);
                        case "Workers" -> workers = Integer.parseInt(value);
                        case "Dealers" -> dealers = Integer.parseInt(value);
                        case "LogSale" -> logSale = Boolean.parseBoolean(value);
                        default -> System.err.println("Unknown config key: " + key);
                    }
                } catch (NumberFormatException e) {
                    throw new ParserException("Invalid parameters", e);
                }
            }
        } catch (IOException e) {
            throw new ParserException("Error parsing config file", e);
        }
    }
}

