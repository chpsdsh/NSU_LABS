package carfactory.threadpool;


import java.util.ArrayList;

public class ThreadPool {
    private final TaskQueue taskQueue = new TaskQueue();
    private final ArrayList<Thread> threads;


    public ThreadPool(int numThreads) {
        threads = new ArrayList<>(numThreads);
        for (int i = 0; i < numThreads; i++) {
            Thread t = new Thread(new WorkerThread(taskQueue), "WorkerThread - " + i);
            t.start();
            threads.add(t);
        }
    }

    public void execute(Runnable task) {
        taskQueue.put(task);
    }

    public void shutdown() {
        for (Thread t : threads) {
            t.interrupt();
        }
    }
}

