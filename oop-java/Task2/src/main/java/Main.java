import context.Context;
import commands.*;
import exceptions.*;
import configuration.*;
import factory.Factory;

import java.io.*;
import java.util.Scanner;
import java.util.function.BiConsumer;

public class Main {
    public static void main(String[] args) throws IOException, Exception {
        ConfigParser parser = null;
        String configFileName = args[0];
        Factory factory = new Factory("factory/factoryConfiguration.txt");
        Context context = new Context();
        if (configFileName != null) {
            try (FileInputStream inputStream = new FileInputStream(configFileName)) {
                parser = new ConfigParser(inputStream);
            } catch (IOException e) {
                throw new IOException("No such configuration file" + configFileName);
            }
            BiConsumer<String, String[]> commandProcessor = (commandName, arguments) -> {
                try {
                    Command command = factory.createCommand(commandName, arguments);
                    command.apply(context);
                } catch (Exception e) {
                    throw new RuntimeException("Exception creating command" + commandName);
                }
            };
            parser.ParseAndExecute(commandProcessor);
        } else {
            parser = new ConfigParser(System.in);

        }
    }
}
