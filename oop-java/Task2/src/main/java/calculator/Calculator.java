package calculator;

import commands.Command;
import configuration.ConfigParser;
import context.Context;
import exceptions.CalculatorException;
import exceptions.FactoryException;
import exceptions.OperationException;
import exceptions.ParserException;
import factory.Factory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.BiConsumer;

public class Calculator {
    private String configFileName;
    private static final Logger logger = LogManager.getLogger(Calculator.class);

    public Calculator(String configFileName) {
        this.configFileName = configFileName;
    }

    public void execute() {
        try {
            Factory factory = new Factory("factoryConfiguration.txt");
            Context context = new Context();
            BiConsumer<String, String[]> commandProcessor = (commandName, arguments) -> {
                try {
                    Command command = factory.createCommand(commandName, arguments);
                    command.apply(context);
                } catch (ReflectiveOperationException | FactoryException e) {
                    logger.error("Error creating command {}", commandName);
                    throw new CalculatorException("Exception creating command " + commandName, e);
                } catch (ArithmeticException | OperationException e) {
                    logger.error("Error creating command {}", commandName);
                    System.out.println("Error while creating command:\n" + e);
                }
            };

            ConfigParser parser = new ConfigParser(configFileName);
            parser.ParseAndExecute(commandProcessor);
        } catch (ParserException e) {
            throw new CalculatorException("Error while parsing", e);
        } catch (FactoryException e) {
            throw new CalculatorException("Factory Exception happened\n", e);
        }
    }
}
