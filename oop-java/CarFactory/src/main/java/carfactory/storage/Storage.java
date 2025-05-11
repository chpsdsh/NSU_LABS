package carfactory.storage;

import java.util.LinkedList;
import java.util.Queue;

public class Storage<T>{
    private Queue<T> details = new LinkedList<>();
    private final int size;
    private int detailsCount = 0;
    private boolean isFull = false;

    public Storage(int size){
        this.size = size;

    }

    public synchronized int getDetailsCount() {
        return detailsCount;
    }

    public boolean isFull() {
        return isFull;
    }

    public synchronized void put(T detail) throws InterruptedException {
        while (isFull){
            wait();
        }

        details.add(detail);
        detailsCount++;
        if (detailsCount == size) {
            isFull = true;
        }
        notifyAll();
    }

    public synchronized T get() throws InterruptedException {
        while(details.isEmpty()){
            System.out.println(detailsCount);
            wait();
        }

        detailsCount--;
        if(detailsCount != size){
            isFull = false;
        }
        notifyAll();
        return details.remove();
    }
}
