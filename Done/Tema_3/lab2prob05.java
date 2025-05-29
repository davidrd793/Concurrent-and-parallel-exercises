// Voy por aqui para mañana 

class SharedResource {       
    /* 
        ADD CODE:    A synchronized block or method
        methodOne()  
        1.  prints Name of the current thread
         2.  Sleeps for 2000 ms
         3.  prints message that current thread is done 

    */
       
    /* 
        ADD CODE:   A synchronized block or method
        methodTwo()  
        1.  prints Name of the current thread
         2.  Sleeps for 2000 ms
         3.  prints message that current thread is done 

    */
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
            sharedResource.methodOne();
        } else {
            sharedResource.methodTwo();
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