package commands;

import context.Context;
import exceptions.CommandExceptions;
import exceptions.StackException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Addition extends Command {
    private static final Logger logger = LogManager.getLogger(Addition.class);

    @Override
    public void apply(Context context) throws CommandExceptions {
        logger.info("Applying ADD command started");

        if (context.getStack().size() < 2) {
            logger.error("Not enough elements in stack");
            throw new StackException("Not enough elements in stack");
        }
        double operand2 = context.getStack().pop();
        double operand1 = context.getStack().pop();

        double result = operand1 + operand2;
        context.getStack().push(result);
        logger.info("Applying addition command finished");
    }
}