package carfactory.threadpool;

public class WorkerThread extends Thread {
    private final TaskQueue taskQueue;

    public WorkerThread(TaskQueue taskQueue) {
        this.taskQueue = taskQueue;
    }

    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Runnable task = taskQueue.take();
                task.run();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
