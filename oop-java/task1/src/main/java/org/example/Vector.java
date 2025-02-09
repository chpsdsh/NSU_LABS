package org.example;

public interface Vector {
    double component(int n);
    double length();
    String toString();
    Vector plus(Vector other);
}