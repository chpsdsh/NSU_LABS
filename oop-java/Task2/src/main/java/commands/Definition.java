package commands;

import context.Context;
import exceptions.CommandExceptions;
import exceptions.InvalidParameterException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Definition extends Command {
    private final String variable;
    private final double value;

    private static final Logger logger = LogManager.getLogger(Definition.class);

    public Definition(String[] args) throws CommandExceptions {
        if (args.length < 2) {
            logger.error("Not enough arguments");
            throw new InvalidParameterException("Not enough arguments");
        }
        this.variable = args[0];
        try {
            this.value = Double.parseDouble(args[1]);
        } catch (NumberFormatException e) {
            logger.info("Invalid value for DEFINE");
            throw new InvalidParameterException("Invalid value for DEFINE" + args[1]);
        }
    }

    @Override
    public void apply(Context context) throws CommandExceptions {
        logger.info("Applying DEFINE command started");
        if (context.getVariables().containsKey(variable)) {
            logger.error("Variable {} already exists", variable);
            throw new InvalidParameterException("Variable" + variable + "already exists");
        } else {
            context.getVariables().put(variable, value);
        }
        logger.info("Applying addition command finished");
    }
}