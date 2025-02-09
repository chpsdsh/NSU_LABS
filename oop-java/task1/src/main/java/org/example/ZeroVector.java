package org.example;

public class ZeroVector extends AbstractVector {
    public static final ZeroVector INSTANCE = new ZeroVector();

    public ZeroVector() {
    }

    @Override
    public double component(int n) {
        return 0;
    }

    @Override
    public double length() {
        return 0;
    }

    @Override
    public Vector plus(Vector other) {
        return other;
    }

}