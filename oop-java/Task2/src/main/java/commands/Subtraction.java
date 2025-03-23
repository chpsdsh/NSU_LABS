package commands;

import context.Context;
import exceptions.OperationException;
import exceptions.StackException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Subtraction extends Command {
    private static final Logger logger = LogManager.getLogger(Subtraction.class);

    @Override
    public void apply(Context context) throws OperationException {
        logger.info("Applying SUB command started");
        if (context.getStack().size() < 2) {
            logger.error("Not enough elements in stack");
            throw new StackException("Not enough elements in stack");
        }
        double operand2 = context.getStack().pop();
        double operand1 = context.getStack().pop();
        double result = operand1 - operand2;
        context.getStack().push(result);
        logger.info("Applying SUB command ended");
    }
}