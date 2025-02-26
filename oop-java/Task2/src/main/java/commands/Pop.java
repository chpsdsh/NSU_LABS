package commands;

import context.Context;
import exceptions.CommandExceptions;
import exceptions.StackException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Pop extends Command {
    private static final Logger logger = LogManager.getLogger(Pop.class);

    @Override
    public void apply(Context context) throws CommandExceptions {
        logger.info("Applying POP command started");
        if (context.getStack().empty()) {
            logger.error("Not enough elements in stack");
            throw (new StackException("Stack is empty nothing to POP"));
        }
        context.getStack().pop();
        logger.info("Applying POP command ended");

    }

}