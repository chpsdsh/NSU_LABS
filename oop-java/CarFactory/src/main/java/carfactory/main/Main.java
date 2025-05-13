package carfactory.main;

import carfactory.exceptions.CarFactoryException;
import carfactory.factory.CarFactory;

public class Main {
    public static void main(String[] args) {
        try {
            new CarFactory();
        } catch (CarFactoryException e) {
            System.out.println("Error creating factory" + e);
        }
    }
}
