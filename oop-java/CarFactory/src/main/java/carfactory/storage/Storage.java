package carfactory.storage;

import java.util.LinkedList;
import java.util.Queue;

public class Storage<T>{
    private final Queue<T> details = new LinkedList<>();
    private final int size;
    private int detailsCount = 0;
    private boolean isFull = false;

    public Storage(int size){
        this.size = size;
    }

    public boolean isEmpty(){
        return detailsCount == 0;
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
        System.out.println("Details: " + details.size());
        details.add(detail);
        detailsCount++;
        if (detailsCount == size) {
            isFull = true;
        }
        notifyAll();
    }

    public synchronized T get() throws InterruptedException {
        System.out.println("before wait");
        while(detailsCount == 0){
            System.out.println(detailsCount +" "+ size);
            wait();
        }
        System.out.println("get");
        detailsCount--;
        if(detailsCount != size){
            isFull = false;
        }
        notifyAll();
        return details.remove();
    }
}
