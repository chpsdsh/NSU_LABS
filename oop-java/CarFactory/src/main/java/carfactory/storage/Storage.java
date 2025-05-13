package carfactory.storage;

import carfactory.gui.ChangesListener;

import javax.swing.*;
import java.util.LinkedList;
import java.util.Queue;

public class Storage<T>{
    private final Queue<T> details = new LinkedList<>();
    private final int size;
    private int detailsCount = 0;
    private boolean isFull = false;
    private int detailsProduced = 0;
    private ChangesListener storageChangesListener;
    private ChangesListener detailsProducedListener;

    public Storage(int size){
        this.size = size;
    }

    public synchronized boolean isEmpty(){
        return detailsCount == 0;
    }

    public synchronized void setStorageChangesListener(ChangesListener listener){
        this.storageChangesListener = listener;
    }

    public synchronized void setDetailsProducedListener(ChangesListener listener){
        this.detailsProducedListener = listener;
    }

    public synchronized void put(T detail) throws InterruptedException {
        while (isFull){
            wait();
        }
        System.out.println("Details: " + details.size());
        details.add(detail);
        detailsCount++;
        SwingUtilities.invokeLater(() -> storageChangesListener.onTimeChange(detailsCount));
        detailsProduced++;
        SwingUtilities.invokeLater(() -> detailsProducedListener.onTimeChange(detailsProduced));

        if (detailsCount == size) {
            isFull = true;
        }
        notifyAll();
    }

    public synchronized T get() throws InterruptedException {
        while(detailsCount == 0){
            wait();
        }
        detailsCount--;
        SwingUtilities.invokeLater(() -> storageChangesListener.onTimeChange(detailsCount));
        if(detailsCount != size){
            isFull = false;
        }
        notifyAll();
        return details.remove();
    }
}
