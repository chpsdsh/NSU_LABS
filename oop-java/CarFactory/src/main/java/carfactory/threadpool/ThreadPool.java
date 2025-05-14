package carfactory.threadpool;


import carfactory.gui.ChangesListener;

import javax.swing.*;
import java.util.ArrayList;

public class ThreadPool {
    private final TaskQueue taskQueue = new TaskQueue();
    private final ArrayList<Thread> threads;
    private ChangesListener taskQueueListener;

    public void setTaskQueueListener(ChangesListener listener){
        this.taskQueueListener = listener;
    }

    public ThreadPool(int numThreads) {
        threads = new ArrayList<>(numThreads);
        for (int i = 0; i < numThreads; i++) {
            Thread t = new WorkerThread(taskQueue);
            t.start();
            threads.add(t);
        }
    }

    public void execute(Runnable task) {
        taskQueue.put(task);
        SwingUtilities.invokeLater(() -> taskQueueListener.onTimeChange(taskQueue.getSize()));
    }

    public void shutdown() {
        for (Thread t : threads) {
            t.interrupt();
        }
    }
}

