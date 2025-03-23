import context.Context;
import commands.*;
import configuration.*;
import exceptions.*;
import factory.Factory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.ArithmeticException;
import java.util.function.BiConsumer;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws CalculatorException {
        logger.info("Program started");
        String configFileName = null;
        if (args.length == 1) {
            configFileName = args[0];
        }

        Factory factory = new Factory("factoryConfiguration.txt");
        Context context = new Context();
        BiConsumer<String, String[]> commandProcessor = (commandName, arguments) -> {
            try {
                Command command = factory.createCommand(commandName, arguments);
                command.apply(context);
            } catch (ReflectiveOperationException | ArithmeticException | FactoryException
                     | OperationException e) {
                logger.error("Error creating command {}", commandName);
                throw new CalculatorException("Exception creating command " + commandName, e);
            }
        };

        try {
            ConfigParser parser = new ConfigParser(configFileName);
            parser.ParseAndExecute(commandProcessor);
        } catch (ParserException e) {
            throw new CalculatorException("Error while parsing", e);
        }

        logger.info("Program finished");
    }
}
