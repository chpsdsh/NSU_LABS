package commands;

import context.Context;
import exceptions.CommandExceptions;
import exceptions.StackException;

public class Print extends Command {
    @Override
    public void apply(Context context) throws CommandExceptions {
        if (context.getStack().empty()) {
            throw new StackException("Empty stack nothing to print");
        }
        System.out.println(context.getStack().pop());
    }
}