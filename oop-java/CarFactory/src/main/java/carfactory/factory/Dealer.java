package carfactory.factory;

import carfactory.car.Car;
import carfactory.storage.Storage;
import carfactory.storagecontroller.StorageController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Dealer extends Thread {
    private final Storage<Car> carStorage;
    private final StorageController storageController;
    private int delay = 1500;
    private final int number;
    private final boolean logging;
    private static final Logger logger = LogManager.getLogger(Dealer.class);

    public Dealer(Storage<Car> carStorage,StorageController storageController, int number, boolean logging) {
        this.carStorage = carStorage;
        this.storageController = storageController;
        this.number = number;
        this.logging = logging;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    @Override
    public void run() {
        try {
            while (!this.isInterrupted()) {
                Car car = carStorage.get();
                storageController.notifySales();
                if (logging) {
                    logger.info("Dealer: " + number + "; " + "Car ID: " + car.getId() + " (" + car.getDetailsId() + ").");
                }
                Thread.sleep(delay);
            }
        } catch (InterruptedException e) {
            this.interrupt();
        }
    }
}
