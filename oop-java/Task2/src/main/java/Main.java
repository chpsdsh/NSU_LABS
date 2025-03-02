import context.Context;
import commands.*;
import configuration.*;
import factory.Factory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.function.BiConsumer;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        logger.info("Program started");
        String configFileName = null;
        if (args != null) {
            configFileName = args[0];
        }

        Factory factory = new Factory("factoryConfiguration.txt");
        Context context = new Context();
        BiConsumer<String, String[]> commandProcessor = (commandName, arguments) -> {
            try {
                Command command = factory.createCommand(commandName, arguments);
                command.apply(context);
            } catch (Exception e) {
                logger.error("Error creating command {}", commandName);
                throw new RuntimeException("Exception creating command " + commandName);
            }
        };
        ConfigParser parser = new ConfigParser(configFileName);
        parser.ParseAndExecute(commandProcessor);
        logger.info("Program finished");

    }
}
