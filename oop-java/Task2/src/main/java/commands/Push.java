package commands;

import context.Context;
import exceptions.CommandExceptions;
import exceptions.InvalidParameterException;

public class Push extends Command {
    private final String parameter;

    public Push(String[] args) {
        this.parameter = args[0];
    }

    @Override
    public void apply(Context context) throws CommandExceptions {
        double value;
        if (context.getVariables().containsKey(parameter)) {
            value = context.getVariables().get(parameter);
        } else {
            try {
                value = Double.parseDouble(parameter);
            } catch (NumberFormatException e) {
                throw new InvalidParameterException("Invalid parameter for PUSH: " + parameter, e);
            }
        }
        context.getStack().push(value);
    }
}