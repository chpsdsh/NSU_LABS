package carfactory.car;

import carfactory.parts.Accessory;
import carfactory.parts.Body;
import carfactory.parts.CarPart;
import carfactory.parts.Engine;

import java.util.concurrent.atomic.AtomicInteger;

public class Car extends CarPart {
    private static final AtomicInteger id = new AtomicInteger(1);
    private final Body body;
    private final Engine engine;
    private final Accessory accessory;

    public Car(Body body, Engine engine, Accessory accessory) {
        super(id.getAndIncrement());
        this.body = body;
        this.engine = engine;
        this.accessory = accessory;
    }

    public String getDetailsId(){
        return "Body "+body.getId() + "Engine "+ engine.getId() + "Accessory " + accessory.getId();
    }

}
