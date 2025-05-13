package carfactory.partsupplier;

import carfactory.parts.CarPart;
import carfactory.storage.Storage;

import java.util.function.Supplier;


public class PartSupplier<T extends CarPart> extends Thread {
    private final Storage<T> storage;
    private final Supplier<T> partSupplier;
    private int delay = 1500;

    public PartSupplier(Storage<T> storage, Supplier<T> partSupplier) {
        this.storage = storage;
        this.partSupplier = partSupplier;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                T part = partSupplier.get();
                storage.put(part);
                Thread.sleep(delay);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
