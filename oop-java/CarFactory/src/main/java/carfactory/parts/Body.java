package carfactory.parts;

import java.util.concurrent.atomic.AtomicInteger;

public class Body extends CarPart{
    private static final AtomicInteger id  = new AtomicInteger(1);

    public Body(){
        super(id.getAndIncrement());
    }
}
