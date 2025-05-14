package carfactory.factory;

import carfactory.parts.Car;
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
    private final ThreadPool threadPool;
    private final Thread storageControllerThread;
    private final PartSupplier<Body> bodyPartSupplier;
    private final PartSupplier<Engine> enginePartSupplier;
    private final List<PartSupplier<Accessory>> accessorySuppliers;
    private final List<Dealer> dealers;

    public CarFactory() throws CarFactoryException {
        ConfigParser parser;
        try {
            parser = new ConfigParser();
        } catch (ParserException e) {
            throw new CarFactoryException("Exception parsing file", e);
        }
        Storage<Body> bodyStorage = new Storage<>(parser.getStorageBodySize());
        Storage<Engine> engineStorage = new Storage<>(parser.getStorageEngineSize());
        Storage<Accessory> accessoryStorage = new Storage<>(parser.getStorageAccessorySize());
        Storage<Car> carStorage = new Storage<>(parser.getStorageAutoSize());

        bodyPartSupplier = new PartSupplier<>(bodyStorage, Body::new);
        enginePartSupplier = new PartSupplier<>(engineStorage, Engine::new);
        accessorySuppliers = new ArrayList<>(parser.getAccessorySuppliers());

        for (int i = 0; i < parser.getAccessorySuppliers(); i++) {
            PartSupplier<Accessory> accessoryPartSupplier = new PartSupplier<>(accessoryStorage, Accessory::new);
            accessorySuppliers.add(accessoryPartSupplier);
        }

        threadPool = new ThreadPool(parser.getWorkers());

        StorageController storageController = new StorageController(threadPool,
                bodyStorage,
                engineStorage,
                accessoryStorage,
                carStorage);

        storageControllerThread = new Thread(storageController,"STORAGE CONTROLLER");

        dealers = new ArrayList<>(parser.getDealers());

        for (int i = 0; i < parser.getDealers(); i++) {
            Dealer dealer = new Dealer(carStorage, storageController, i, parser.isLogSale());
            dealers.add(dealer);
        }

        new MainScreen(bodyStorage, engineStorage,
                accessoryStorage,carStorage, bodyPartSupplier,
                enginePartSupplier, accessorySuppliers,
                dealers, threadPool,this);

        storageControllerThread.start();
        storageController.notifySales();

        for(Thread d: dealers){
            d.start();
        }

        for(Thread a: accessorySuppliers){
            a.start();
        }

        bodyPartSupplier.start();
        enginePartSupplier.start();
    }

    public void stopFactory(){
        threadPool.shutdown();

        storageControllerThread.interrupt();

        for(Thread dealer: dealers){
            dealer.interrupt();
        }

        bodyPartSupplier.interrupt();
        enginePartSupplier.interrupt();
        for (Thread accessorySupplier: accessorySuppliers){
            accessorySupplier.interrupt();
        }
    }
}
