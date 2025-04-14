package commands;

import context.Context;
import exceptions.OperationException;

abstract public class Command {
    abstract public void apply(Context context) throws OperationException;
}