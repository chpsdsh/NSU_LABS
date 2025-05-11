package carfactory.factory;

import carfactory.car.Car;
import carfactory.storage.Storage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Dealer implements Runnable {
    private final Storage<Car> carStorage;
    private int delay = 3;
    private final int number;
    private final boolean logging;
    private static final Logger logger = LogManager.getLogger(Dealer.class);

    public Dealer(Storage<Car> carStorage, int number, boolean logging) {
        this.carStorage = carStorage;
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
                Thread.sleep(delay * 1000L);
                System.out.printf(Boolean.toString(logging));
                Car car = carStorage.get();
                System.out.printf("car");
                if (logging) {
                    logger.info("Dealer: " + number + "; " + "Car ID: " + car.getId() + " (" + car.getDetailsId() + ").");
                }
                synchronized (carStorage) {
                    carStorage.notifyAll();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
