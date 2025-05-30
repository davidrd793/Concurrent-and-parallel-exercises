import java.util.LinkedList; 

class SharedBuffer {
    private LinkedList<Integer> buffer = new LinkedList<>();
    private int capacity = 10;

    public synchronized void add(int value) {
        while(buffer.size() == capacity) {
            try {
                wait();
            } catch (InterruptedException e) {}
        
        buffer.add(value);
        notifyAll();
        }
    }

    public synchronized int remove() {
        while (buffer.isEmpty()) {
            try {
                wait(); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        int value = buffer.removeFirst(); //IMPORTANTE: Los arrays estáticos tienen el método removeFirst() para actuar como una queue
        notifyAll();
        return value;
    }
}


class Producer extends Thread {
    private SharedBuffer buffer;

    public Producer(SharedBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        for (int i = 0; i < 50; i++) {
            buffer.add(i);
            System.out.println("Produced: " + i);
        }
    }
}


class Consumer extends Thread {
    private SharedBuffer buffer;

    public Consumer(SharedBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        for (int i = 0; i < 50; i++) {
          int value;
            value = buffer.remove();
          StringBuilder stringBuilder = new StringBuilder();
          System.out.println("Consumed: " + value);
        }
    }
}


public class ProdCons_metodo {
    public static void main(String[] args) {
        SharedBuffer buffer = new SharedBuffer();
        Producer producer = new Producer(buffer);
        Consumer consumer = new Consumer(buffer);
        producer.start();
        consumer.start();
    }
}