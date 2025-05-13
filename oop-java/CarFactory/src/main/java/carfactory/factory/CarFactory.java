package carfactory.factory;

import carfactory.car.Car;
import carfactory.configparser.ConfigParser;
import carfactory.exceptions.CarFactoryException;
import carfactory.exceptions.ParserException;
import carfactory.gui.MainScreen;
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
        List<PartSupplier<Accessory>> accessorySuppliers = new ArrayList<>(parser.getAccessorySuppliers());

        for (int i = 0; i < parser.getAccessorySuppliers(); i++) {
            PartSupplier<Accessory> accessoryPartSupplier = new PartSupplier<>(accessoryStorage, Accessory::new);
            accessorySuppliers.add(accessoryPartSupplier);
        }

        ThreadPool threadPool = new ThreadPool(parser.getWorkers());
        StorageController storageController = new StorageController(threadPool,
                bodyStorage,
                engineStorage,
                accessoryStorage,
                carStorage);
        Thread storageControllerThread = new Thread(storageController);

        List<Dealer> dealers = new ArrayList<>(parser.getDealers());


        for (int i = 0; i < parser.getDealers(); i++) {
            Dealer dealer = new Dealer(carStorage, storageController, i, parser.isLogSale());
            dealers.add(dealer);
        }

        MainScreen mainScreen = new MainScreen(bodyStorage, engineStorage,
                accessoryStorage,carStorage, bodyPartSupplier,
                enginePartSupplier, accessorySuppliers,
                dealers, threadPool);
        storageControllerThread.start();
        for(PartSupplier<Accessory> a: accessorySuppliers){
            a.start();
        }
        for(Dealer d: dealers){
            d.start();
        }
        bodyPartSupplier.start();
        enginePartSupplier.start();

    }
}
