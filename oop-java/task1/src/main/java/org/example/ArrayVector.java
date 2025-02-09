package org.example;

public class ArrayVector extends AbstractVector {
    private double arr[];

    public ArrayVector(double x, double y, double z) {
        arr = new double[]{x, y, z};
    }

    @Override
    public double component(int n) {
        return arr[n];
    }

    @Override
    public double length() {
        return Math.sqrt(arr[0] * arr[0] + arr[1] * arr[1] + arr[2] * arr[2]);
    }

    @Override
    public Vector plus(Vector other) {
        return new FieldVector(arr[0] + other.component(0), arr[1] + other.component(1), arr[2] + other.component(2));
    }
}