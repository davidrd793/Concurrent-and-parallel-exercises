class SharedResource {    
    
    public synchronized void methodOne(String name) { //Una forma seria implementar aquí sun método synchronized, pero tambien se puede meter la llamada en un bloque
        System.out.println("Thread " + name + " is entering the Task");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {}

        System.out.println("Thread " + name + " has completed his task");
    }

        public synchronized void methodTwo(String name) {
        System.out.println("Thread " + name + " is entering the Task");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {}

        System.out.println("Thread " + name + " has completed his task");
    }
}

class MyTaskThread extends Thread {
    private SharedResource sharedResource;
    private boolean runFirstMethod;

    public MyTaskThread(SharedResource sharedResource, boolean runFirstMethod) {
        this.sharedResource = sharedResource;
        this.runFirstMethod = runFirstMethod;
    }

    @Override
    public void run() {
        if (runFirstMethod) {
            sharedResource.methodOne(Thread.currentThread().getName());
        } else {
            sharedResource.methodTwo(Thread.currentThread().getName());
        }
    }
}

public class lab2prob05 {
    public static void main(String[] args) {
        SharedResource sharedResource = new SharedResource();

        MyTaskThread thread1 = new MyTaskThread(sharedResource, true);
        MyTaskThread thread2 = new MyTaskThread(sharedResource, false);

        thread1.start();
        thread2.start();
    }
}