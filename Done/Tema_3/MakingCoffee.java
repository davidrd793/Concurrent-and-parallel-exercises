
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
        if (sharedResource.flag) {
            try {
                sharedResource.lock.wait();
            } catch (InterruptedException e) {}
        }
        System.out.println("Thread FirstCoffee is making coffee " + iInteger);
        iInteger++;
        //Al terminar notifica al otro hilo
        sharedResource.flag = true;
        
    }
}

class SecondThread extends Thread {
    private final SharedResource sharedResource;

    public SecondThread(SharedResource sharedResource) {
        this.sharedResource = sharedResource;
    }

    @Override
    public void run() {
        secondCoffee();
        while(true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}
        }
    }

    void secondCoffee() {
        System.out.println("Thread SecondCoffee is making coffee ");
    }
}
public class MakingCoffee {
    public static void main(String[] args) {
        SharedResource sr = new SharedResource();
        System.out.println("Coffee maker:");
        Thread a = new FirstThread(sr);
        Thread b = new FirstThread(sr);
        a.start();
        b.start();

    }
}
