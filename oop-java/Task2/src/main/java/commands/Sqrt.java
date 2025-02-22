package commands;

import context.Context;
import exceptions.CommandExceptions;
import exceptions.StackException;

public class Sqrt extends Command {
    @Override
    public void apply(Context context) throws CommandExceptions {
        if (context.getStack().empty()) {
            throw new StackException("Empty stack nothing to print");
        }
        double res = Math.sqrt(context.getStack().pop());
        context.getStack().push(res);
    }
}
