import java.util.Random;

class NewThread implements Runnable {
    String name;
    Thread t;
    Random rand = new Random();
    
    // Constructor for the thread class
    NewThread(String threadName) {
        name = threadName;
        t = new Thread(this, name);
        System.out.println("New thread created: " + t);
    }

    public void startThread() {
        t.start();
    }

    public void run() {
        // Simulate some CPU expensive task
        System.out.println("in run: " + Thread.currentThread().getName());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e){}


        /*for (int i = 0; i < 100000000; i++) {
            rand.nextInt();
        } */
        System.out.println("[" + Thread.currentThread().getName() + "] finished.");
    }
}

class ListaEstatica {
    public static void main(String[] args) {
        NewThread[] threads = new NewThread[5];
        
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new NewThread("joinThread-" + i); 
            threads[i].startThread(); 
        }

        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].t.join();  // IMPORTANTE: referencias a objetoRunnable.t.metodoPertinente() para clases que crean Thread dentro de Runnable
            } catch (InterruptedException e) {
                System.out.println("Thread " + threads[i].t.getName() + " interrupted");
            }
        }

        System.out.println("[" + Thread.currentThread().getName() + "] All threads done!");
    }
}