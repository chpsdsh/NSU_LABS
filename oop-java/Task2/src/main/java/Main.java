import context.Context;
import commands.*;
import configuration.*;
import factory.Factory;


import java.util.function.BiConsumer;

public class Main {
    public static void main(String[] args) throws Exception {
        String configFileName = null;
        if (args.length > 0) {
            configFileName = args[0];
        }
        Factory factory = new Factory("factoryConfiguration.txt");
        Context context = new Context();
        BiConsumer<String, String[]> commandProcessor = (commandName, arguments) -> {
            try {
                Command command = factory.createCommand(commandName, arguments);
                command.apply(context);
            } catch (Exception e) {
                throw new RuntimeException("Exception creating command " + commandName);
            }
        };
        ConfigParser parser = new ConfigParser(configFileName);
        parser.ParseAndExecute(commandProcessor);
    }
}
