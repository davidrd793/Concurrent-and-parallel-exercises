import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


class WorkerThread implements Runnable {
    private static final ReentrantLock lock = new ReentrantLock();
    private static final Condition turnCondition = lock.newCondition(); //IMPORTANTE: Uso de Condition para monitores con lock()
    private static int currentMaxId;
    private final int threadId;

    public WorkerThread(int id, int maxId) {
        this.threadId = id;
        currentMaxId = maxId;
    }

    @Override
    public void run() {
        lock.lock(); //IMPORTANTE: Recordar uso de bloques try-catch para manejar lock()
        try {
            while (threadId != currentMaxId) {
                turnCondition.await(); //Ponemos al primer hilo a esperar (threadId = 5) y luego a cada uno de los otros restando currentMaxId
            }
            // Critical section
            System.out.println("Thread with ID " + threadId + " entering critical section.");
            Thread.sleep(1000);
            System.out.println("Thread with ID " + threadId + " leaving critical section.");
            currentMaxId--;
            turnCondition.signalAll();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }
}
public class Monitor_lock {
    public static void main(String[] args) {
        final int numberOfThreads = 5;
        List<Thread> threads = new ArrayList<>();

        for (int i = numberOfThreads; i > 0; i--) {
            Thread thread = new Thread(new WorkerThread(i, numberOfThreads));
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("All threads have executed.");
    }
}