package commands;

import context.Context;
import exceptions.CommandExceptions;
import exceptions.StackException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Print extends Command {
    private static final Logger logger = LogManager.getLogger(Print.class);

    @Override
    public void apply(Context context) throws CommandExceptions {
        logger.info("Applying PRINT command started");

        if (context.getStack().empty()) {
            logger.error("Not enough elements in stack");
            throw new StackException("Empty stack nothing to print");
        }
        System.out.println(context.getStack().pop());
        logger.info("Applying PRINT command ended");

    }
}