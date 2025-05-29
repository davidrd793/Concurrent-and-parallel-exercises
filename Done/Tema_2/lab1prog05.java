class NewThread extends Thread {

    NewThread(String threadName) {
        // Set the thread name in the constructor
        super(threadName);
    }

    @Override
    public void run() {
        try {
            for (int i = 5; i > 0; i--) {
                System.out.println(getName() + ": " + i);
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            System.out.println(getName() + " interrupted.");
        }
        System.out.println(getName() + " exiting and will be destroyed.");
    }
}

class lab1prog05 {
    public static void main(String args[]) {
        NewThread nt = new NewThread("Demo Thread");
        nt.start(); // Directly start the thread

        try {
            for (int i = 5; i > 0; i--) {
                System.out.println(Thread.currentThread().getName() + ": " + i);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted");
        }
        System.out.println("Main thread exiting");
    }
}