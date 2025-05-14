package carfactory.parts;

import java.util.concurrent.atomic.AtomicInteger;

public class Accessory extends CarPart {
    private static final AtomicInteger id  = new AtomicInteger(1);

    public Accessory(){
        super(id.getAndIncrement());
    }
}
