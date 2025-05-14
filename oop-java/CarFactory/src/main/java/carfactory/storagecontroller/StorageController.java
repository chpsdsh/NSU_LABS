package carfactory.storagecontroller;

import carfactory.car.Car;
import carfactory.threadpool.WorkerTask;
import carfactory.parts.Accessory;
import carfactory.parts.Body;
import carfactory.parts.Engine;
import carfactory.storage.Storage;
import carfactory.threadpool.ThreadPool;

public class StorageController implements Runnable {
    private final Storage<Body> bodyStorage;
    private final Storage<Engine> engineStorage;
    private final Storage<Accessory> accessoryStorage;
    private final Storage<Car> carStorage;
    private final ThreadPool threadPool;
    private boolean saleFlag = false;
    private final Object saleLock = new Object();

    public StorageController(ThreadPool threadPool, Storage<Body> bodyStorage, Storage<Engine> engineStorage, Storage<Accessory> acessoryStorage, Storage<Car> carStorage) {
        this.threadPool = threadPool;
        this.bodyStorage = bodyStorage;
        this.engineStorage = engineStorage;
        this.accessoryStorage = acessoryStorage;
        this.carStorage = carStorage;
    }

    public void notifySales() {
        synchronized (saleLock) {
            saleLock.notify();
            saleFlag = true;
        }
    }

    @Override
    public void run() {
        {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    synchronized (saleLock) {
                        while (!saleFlag){
                            saleLock.wait();
                        }
                        saleFlag  = false;
                    }
                    threadPool.execute(new WorkerTask(bodyStorage, engineStorage, accessoryStorage, carStorage));
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
