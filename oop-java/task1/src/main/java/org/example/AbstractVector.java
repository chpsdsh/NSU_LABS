package org.example;

abstract class AbstractVector implements Vector {

    @Override
    public String toString() {
        return component(0) + "," + component(1) + "," + component(2);
    }
}