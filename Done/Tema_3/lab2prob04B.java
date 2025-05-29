
// Este otro logra un sharedCounter sincronizando las llamadas a .increment() mediante synchronized().

class CounterThread extends Thread {
    private SharedCounter sharedCounter;

    public CounterThread(SharedCounter sharedCounter) {
        this.sharedCounter = sharedCounter;
    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            synchronized(sharedCounter) {
                sharedCounter.increment();
            }
        }
    }
}



class SharedCounter {
    private int count = 0; //IMPORTANTE: Crear un AtomicInteger es una instancia de clase y debe ser declarada con 'newa'
    
    
    public void increment() {
        count++;
    }


    public int getCount() {
        return count;
    }
}

public class lab2prob04B {
    public static void main(String[] args) throws InterruptedException {
        SharedCounter sharedCounter = new SharedCounter();
        Thread[] threads = new Thread[10];

        for (int i = 0; i < threads.length; i++) {
            Thread t = new CounterThread(sharedCounter);
            t.start();
            threads[i] = t;
        }

        for (Thread thread : threads) {
            thread.join();
        }
        System.out.println("Final Counter Value: " + sharedCounter.getCount());
    }
}