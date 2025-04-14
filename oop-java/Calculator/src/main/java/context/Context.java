package context;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public final class Context {
    private final Map<String, Double> variables;
    private final Stack<Double> stack;

    public Context() {
        variables = new HashMap<>();
        stack = new Stack<>();
    }

    public Stack<Double> getStack() {
        return stack;
    }

    public Map<String, Double> getVariables() {
        return variables;
    }
}