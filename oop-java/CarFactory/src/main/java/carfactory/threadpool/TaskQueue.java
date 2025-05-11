package carfactory.threadpool;

import java.util.LinkedList;
import java.util.Queue;

public class TaskQueue {
    private final Queue<Runnable> queue = new LinkedList<>();

    public synchronized void put(Runnable task){
        queue.add(task);
        notifyAll();
    }

    public synchronized Runnable take() throws InterruptedException {
        while (queue.isEmpty()){
            wait();
        }
        return queue.remove();
    }
}
