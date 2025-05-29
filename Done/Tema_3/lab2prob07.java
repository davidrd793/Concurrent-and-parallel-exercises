import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class lab2prob07 {
    private AtomicBoolean locked = new AtomicBoolean(false);
    public void lock() {
        while (!this.locked.compareAndSet(false, true)) {
            // busy wait - until compareAndSet() succeeds
        }
    }
    public void unlock() {
        this.locked.set(false);
    }
    public static void main(String[] args) {
        final ReentrantLock lock = new ReentrantLock();
        final AtomicInteger counter = new AtomicInteger(0);
        final int numberOfThreads = 10;
        Thread[] threads = new Thread[numberOfThreads];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new Runnable() { //IMPORTANTE: creación de Runnables genéricos con lambda
                @Override
                public void run() {

                    //IMPORTANTE: Muy recomendado usar lock.lock() con bloques try-catch
                    for (int j = 0; j < 1000; j++) {
                        lock.lock();
                        try {
                            counter.incrementAndGet();
                        } finally {
                            lock.unlock();
                        }
                    }
                }
            });
            threads[i].start();
        }
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Final counter value: " + counter.get());
    }
}