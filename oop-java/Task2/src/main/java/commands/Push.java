package commands;

import context.Context;
import exceptions.CommandExceptions;
import exceptions.InvalidParameterException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Push extends Command {
    private final String parameter;

    private static final Logger logger = LogManager.getLogger(Push.class);

    public Push(String[] args) {
        this.parameter = args[0];
    }

    @Override
    public void apply(Context context) throws CommandExceptions {
        logger.info("Applying PUSH command started");
        double value;
        if (context.getVariables().containsKey(parameter)) {
            value = context.getVariables().get(parameter);
        } else {
            try {
                value = Double.parseDouble(parameter);
            } catch (NumberFormatException e) {
                logger.error("Invalid parameter for PUSH {}", parameter);
                throw new InvalidParameterException("Invalid parameter for PUSH: " + parameter, e);
            }
        }
        context.getStack().push(value);
        logger.info("Applying PUSH command started");

    }
}