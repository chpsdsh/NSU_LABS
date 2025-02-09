package org.example;

public class Main {
    public static void main(String[] args) {
        Vector p = new ArrayVector(1, 2, 3);
        Vector p1 = new ZeroVector();
        Vector p2 = new FieldVector(2, 3, 4);
        System.out.println(p.toString());
        Vector sum = p.plus(p1).plus(p2);
        System.out.println(sum.toString());
    }
}