package carfactory.factory;

import carfactory.car.Car;
import carfactory.storage.Storage;
import carfactory.storagecontroller.StorageController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Dealer implements Runnable {
    private final Storage<Car> carStorage;
    private final StorageController storageController;
    private int delay = 3;
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
            while (!Thread.currentThread().isInterrupted()) {
                if(carStorage.isEmpty()){
                    System.out.println("isEMPTY");
                    storageController.notifySales();
                }
                System.out.println("DEALER THREAD " +Thread.currentThread().getName());
                Car car = carStorage.get();
                System.out.println("car");
                storageController.notifySales();

                if (logging) {
                    logger.info("Dealer: " + number + "; " + "Car ID: " + car.getId() + " (" + car.getDetailsId() + ").");
                }

                Thread.sleep(delay * 1000L);

            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
