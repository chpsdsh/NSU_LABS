package carfactory.main;

import carfactory.exceptions.CarFactoryException;
import carfactory.gui.MainScreen;

public class Main {
    public static void main(String[] args) {
        try {
            new MainScreen();
        } catch (CarFactoryException e) {
            System.out.println("Error creating factory" + e);
        }
    }
}
