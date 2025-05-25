/* Otro recurso utilizado es el uso de variables atómicas, como este AtomicInteger para un correcto del recurso compartido de un contador */
import java.util.concurrent.atomic.AtomicInteger;

class Ej03_VariableAtomica {

    public static void main(String[] args) {
    Counter counter = new Counter();
    Thread[] threads = new Thread[100];

    for (int i = 0; i < 100; i++) {
        threads[i] = new CounterThread(counter);
        threads[i].start();
    }
    for (int i = 0; i < 100; i++) {
        try {
            threads[i].join();
        } catch (InterruptedException e) {
            System.out.println("Hubo un error");
        }
    }
    int conteo = counter.getCount();
    System.out.println(conteo);
}
}

class Counter {
    private final AtomicInteger count = new AtomicInteger(0);

    public void increment() {
        count.incrementAndGet();
    }

    public int getCount() {
        return count.get();
    }
}

class CounterThread extends Thread {
    Counter counter;
    CounterThread (Counter counter) {
        this.counter = counter;
    }
    @Override
    public void run() {
    counter.increment();
    }
}