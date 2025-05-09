package carfactory.supplier;

import carfactory.parts.CarPart;
import carfactory.storage.Storage;


public class Supplier<T extends CarPart> implements Runnable{
    private final Storage<T> storage;
    private final Class<T> classPart;
    private int delay;

    public Supplier(Storage<T> storage, Class<T> classPart) {
        this.storage = storage;
        this.classPart = classPart;
    }

    private synchronized void setDelay(int delay) {
        this.delay = delay;
    }

    @Override
    public void run() {

    }
}
