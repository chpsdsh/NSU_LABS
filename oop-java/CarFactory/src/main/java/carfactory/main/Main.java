package carfactory.main;

import carfactory.exceptions.CarFactoryException;
import carfactory.factory.CarFactory;
import carfactory.gui.MainScreen;

public class Main {
    public static void main(String[] args) {
        try {
//            new MainScreen();
            new CarFactory();
        } catch (CarFactoryException e) {
            System.out.println("Error creating factory" + e);
        }
    }
}
