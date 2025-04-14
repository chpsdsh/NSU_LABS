package commands;

import context.Context;
import exceptions.NegativeSqrtValueException;
import exceptions.OperationException;
import exceptions.StackException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Sqrt extends Command {
    private static final Logger logger = LogManager.getLogger(Sqrt.class);

    @Override
    public void apply(Context context) throws OperationException, ArithmeticException {
        logger.info("Applying SQRT command started");
        if (context.getStack().empty()) {
            logger.error("Not enough elements in stack");
            throw new StackException("Not enough elements in stack");
        }
        double operand = context.getStack().pop();
        if (operand < 0) {
            logger.error("Trying to get a SQRT of a negative value {}", operand);
            throw new NegativeSqrtValueException("Trying to get a SQRT of a negative value " + operand);
        }
        double res = Math.sqrt(operand);
        context.getStack().push(res);
        logger.info("Applying SQRT command ended");
    }
}
