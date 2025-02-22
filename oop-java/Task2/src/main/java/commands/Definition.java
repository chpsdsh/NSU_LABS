package commands;

import context.Context;
import exceptions.CommandExceptions;
import exceptions.InvalidParameterException;

public class Definition extends Command {
    private final String variable;
    private final double value;

    public Definition(String[] args) throws CommandExceptions {
        if (args.length < 2) {
            throw new InvalidParameterException("Not enough arguments");
        }
        this.variable = args[0];
        try {
            this.value = Double.parseDouble(args[1]);
        } catch (NumberFormatException e) {
            throw new InvalidParameterException("Invalid value for DEFINE" + args[1]);
        }
    }

    @Override
    public void apply(Context context) throws CommandExceptions {
        if (context.getVariables().containsKey(variable)) {
            throw new InvalidParameterException("Variable" + variable + "already exists");
        } else {
            context.getVariables().put(variable, value);
        }
    }
}