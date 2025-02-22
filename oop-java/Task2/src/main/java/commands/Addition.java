package commands;

import context.Context;
import exceptions.CommandExceptions;
import exceptions.StackException;

public final class Addition extends Command {
    @Override
    public void apply(Context context) throws CommandExceptions {
        if (context.getStack().size() < 2) {
            throw new StackException("Not enough elements in stack");
        }
        double operand2 = context.getStack().pop();
        double operand1 = context.getStack().pop();

        double result = operand1 + operand2;
        context.getStack().push(result);
    }
}