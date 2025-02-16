package commands;

import context.Context;
import exceptions.CommandExceptions;
import exceptions.StackException;

public class Pop extends Command {
    @Override
    void apply(Context context) throws CommandExceptions {
        if (context.getStack().empty()) {
            throw (new StackException("Stack is empty nothing to POP"));
        }
        context.getStack().pop();
    }
}