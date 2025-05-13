package carfactory.factory;

import carfactory.car.Car;
import carfactory.configparser.ConfigParser;
import carfactory.exceptions.CarFactoryException;
import carfactory.exceptions.ParserException;
import carfactory.parts.Accessory;
import carfactory.parts.Body;
import carfactory.parts.Engine;
import carfactory.partsupplier.PartSupplier;
import carfactory.storage.Storage;
import carfactory.storagecontroller.StorageController;
import carfactory.threadpool.ThreadPool;

import java.util.ArrayList;
import java.util.List;

public class CarFactory {
    private final ConfigParser parser;

    public CarFactory() throws CarFactoryException {
        try {
            parser = new ConfigParser();
        } catch (ParserException e) {
            throw new CarFactoryException("Exception parsing file", e);
        }
        Storage<Body> bodyStorage = new Storage<>(parser.getStorageBodySize());
        Storage<Engine> engineStorage = new Storage<>(parser.getStorageEngineSize());
        Storage<Accessory> accessoryStorage = new Storage<>(parser.getStorageAccessorySize());
        Storage<Car> carStorage = new Storage<>(parser.getStorageAutoSize());

        PartSupplier<Body> bodyPartSupplier = new PartSupplier<>(bodyStorage, Body::new);
        PartSupplier<Engine> enginePartSupplier = new PartSupplier<>(engineStorage, Engine::new);
        List<Thread> accessorySuppliers = new ArrayList<>(parser.getAccessorySuppliers());

        for (int i = 0; i < parser.getAccessorySuppliers(); i++) {
            PartSupplier<Accessory> accessoryPartSupplier = new PartSupplier<>(accessoryStorage, Accessory::new);
            Thread accessorySupplier = new Thread(accessoryPartSupplier, "Accessory thread " + i);
            accessorySuppliers.add(accessorySupplier);
            accessorySupplier.start();
        }

        ThreadPool threadPool = new ThreadPool(parser.getWorkers());
        StorageController storageController = new StorageController(threadPool,
                bodyStorage,
                engineStorage,
                accessoryStorage,
                carStorage);
        Thread storageControllerThread = new Thread(storageController);

        List<Thread> dealers = new ArrayList<>(parser.getDealers());
        storageControllerThread.start();

        for (int i = 0; i < parser.getDealers(); i++) {
            Dealer dealer = new Dealer(carStorage,storageController,i,parser.isLogSale());
            Thread dealerThread = new Thread(dealer, "Dealer " + i);
            dealers.add(dealerThread);
            dealerThread.start();
        }

        Thread bodySupplier = new Thread(bodyPartSupplier, "BodySupplier");
        Thread engineSupplier = new Thread(enginePartSupplier,"EngineSupplier");
        bodySupplier.start();
        engineSupplier.start();
    }
}
