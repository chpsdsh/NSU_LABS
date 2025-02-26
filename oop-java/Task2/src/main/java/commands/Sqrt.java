package commands;

import context.Context;
import exceptions.CommandExceptions;
import exceptions.StackException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Sqrt extends Command {
    private static final Logger logger = LogManager.getLogger(Sqrt.class);

    @Override
    public void apply(Context context) throws CommandExceptions {
        logger.info("Applying SQRT command started");
        if (context.getStack().empty()) {
            logger.error("Not enough elements in stack");
            throw new StackException("Empty stack nothing to print");
        }
        double res = Math.sqrt(context.getStack().pop());
        context.getStack().push(res);
        logger.info("Applying SQRT command ended");
    }
}
