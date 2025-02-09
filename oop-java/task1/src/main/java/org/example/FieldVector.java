package org.example;

public class FieldVector extends AbstractVector {
    double x, y, z;

    FieldVector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public double component(int n) {
        switch (n) {
            case 0:
                return x;
            case 1:
                return y;
            case 2:
                return z;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public double length() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    @Override
    public Vector plus(Vector other) {
        return new FieldVector(x + other.component(0), y + other.component(1), z + other.component(2));
    }
}