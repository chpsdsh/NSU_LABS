package carfactory.parts;

import java.util.concurrent.atomic.AtomicInteger;

public class Acessory extends CarPart {
    private static final AtomicInteger id  = new AtomicInteger(1);
    public Acessory(){
        super(id.getAndIncrement());
    }
}
