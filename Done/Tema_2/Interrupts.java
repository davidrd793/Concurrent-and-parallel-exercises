
class MyInterruptThread implements Runnable {
 
    @Override
    public void run() {
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            System.out.println("[" + Thread.currentThread().getName() + "] Interrupted by exception!");
        }
        while (!Thread.interrupted()) {
        }
        System.out.println("[" + Thread.currentThread().getName() + "] Interrupted for the second time.");
    }
}


public class lab1prog11 {
    public static void main(String[] args) throws InterruptedException {
        Thread myThread = new Thread(new MyInterruptThread(), "myThread");
        myThread.start();
         
        System.out.println("[" + Thread.currentThread().getName() + "] Sleeping in main thread for 5s...");
        Thread.sleep(5000);
         
        System.out.println("[" + Thread.currentThread().getName() + "] Interrupting myThread");
        myThread.interrupt(); //IMPORTANTE: .interrupt() saca al thread llamado del bucle en que está
         
        System.out.println("[" + Thread.currentThread().getName() + "] Sleeping in main thread for 5s...");
        Thread.sleep(5000);
         
        System.out.println("[" + Thread.currentThread().getName() + "] Interrupting myThread");
        myThread.interrupt();
    }
}

