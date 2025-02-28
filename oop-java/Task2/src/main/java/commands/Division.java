package commands;

import context.Context;
import exceptions.CommandExceptions;
import exceptions.StackException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Division extends Command {
    private static final Logger logger = LogManager.getLogger(Division.class);

    @Override
    public void apply(Context context) throws CommandExceptions {
        logger.info("Applying DIV command started");
        if (context.getStack().size() < 2) {
            logger.error("Not enough elements in stack");
            throw new StackException("Not enough elements in stack");
        }
        double operand2 = context.getStack().pop();
        double operand1 = context.getStack().pop();
        if (operand2 == 0.0) {
            logger.error("Division by zero");
            throw new CommandExceptions("Division by zero");
        }
        double result = operand1 / operand2;
        context.getStack().push(result);
        logger.info("Applying DIV command ended");
    }
}