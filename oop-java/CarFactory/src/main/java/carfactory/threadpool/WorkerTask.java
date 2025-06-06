package carfactory.threadpool;

import carfactory.parts.Car;
import carfactory.parts.Accessory;
import carfactory.parts.Body;
import carfactory.parts.Engine;
import carfactory.storage.Storage;


public class WorkerTask implements Runnable {
    private final Storage<Body> bodyStorage;
    private final Storage<Engine> engineStorage;
    private final Storage<Accessory> accessoryStorage;
    private final Storage<Car> carStorage;

    public WorkerTask(Storage<Body> bodyStorage, Storage<Engine> engineStorage, Storage<Accessory> acessoryStorage, Storage<Car> carStorage) {
        this.bodyStorage = bodyStorage;
        this.engineStorage = engineStorage;
        this.accessoryStorage = acessoryStorage;
        this.carStorage = carStorage;
    }

    @Override
    public void run() {
        try {
            Body body = bodyStorage.get();
            Engine engine = engineStorage.get();
            Accessory accessory = accessoryStorage.get();
            Car car = new Car(body, engine, accessory);
            carStorage.put(car);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

}
