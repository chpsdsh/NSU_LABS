package carfactory.parts;

import java.util.concurrent.atomic.AtomicInteger;

public class Engine extends CarPart{
    private static final AtomicInteger id  = new AtomicInteger(1);

    public Engine(){
        super(id.getAndIncrement());
    }
}
