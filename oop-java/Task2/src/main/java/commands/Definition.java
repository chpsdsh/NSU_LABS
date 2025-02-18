package commands;

import context.Context;
import exceptions.CommandExceptions;

public class Definition extends Command {
    private final String variable;
    private final double value;

    public Definition(String variable, double value) {
        this.variable = variable;
        this.value = value;
    }

    @Override
    public void apply(Context context) throws CommandExceptions {
        if (context.getVariables().containsKey(variable)) {
            throw new CommandExceptions("Variable" + variable + "already exists");
        } else {
            context.getVariables().put(variable, value);
        }
    }
}