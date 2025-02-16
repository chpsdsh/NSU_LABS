package commands;

import context.Context;
import exceptions.CommandExceptions;

abstract class Command {
    abstract void apply(Context context) throws CommandExceptions;
}