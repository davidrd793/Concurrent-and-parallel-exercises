
class SharedResource {
    final Object lock = new Object();
    boolean flag = false;  
}


class FirstThread extends Thread {
    static int iInteger;
    private final SharedResource sharedResource;

    public FirstThread(SharedResource sharedResource) {
        this.sharedResource = sharedResource;
    }

    @Override
    public void run() {
        while (true) {
            firstCoffee();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}
        }

    }

    void firstCoffee() {
        synchronized(sharedResource.lock) {
             while (sharedResource.flag) {
                try {
                    sharedResource.lock.wait();
                } catch (InterruptedException e) {}
            }
            System.out.println("Thread FirstCoffee is making coffee " + iInteger);
            iInteger++;
            //Al terminar notifica al otro hilo
            sharedResource.flag = true;
            sharedResource.lock.notifyAll();
        }   
        
    }
}

class SecondThread extends Thread {
    private final SharedResource sharedResource;

    public SecondThread(SharedResource sharedResource) {
        this.sharedResource = sharedResource;
    }

    @Override
    public void run() {
        while(true) {
            secondCoffee();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}
        }
    }

    void secondCoffee() {
        synchronized(sharedResource.lock) {
            while (!sharedResource.flag) {
                try {
                    sharedResource.lock.wait();
                } catch (InterruptedException e) {}
            }
            System.out.println("Thread SecondCoffee is making coffee ");
            
            sharedResource.flag = false;
            sharedResource.lock.notifyAll();
        }
    }
}
public class MakingCoffee {
    public static void main(String[] args) {
        SharedResource sr = new SharedResource();
        System.out.println("Coffee maker:");
        Thread a = new FirstThread(sr);
        Thread b = new SecondThread(sr);
        a.start();
        b.start();

    }
}
