package commands;

import context.Context;
import exceptions.CommandExceptions;

abstract public class Command {
    abstract public void apply(Context context) throws CommandExceptions;
}