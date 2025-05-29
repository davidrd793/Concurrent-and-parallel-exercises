public class lab1prog02 extends Thread {
    public lab1prog02(String name) {
        super(name);
    }
    @Override
    public void run() {
        System.out.println("Executing thread: " + Thread.currentThread().getName());
        System.out.println("priority:" + Thread.currentThread().getPriority());
        // You can add more code here to demonstrate the thread's functionality
    }
    public static void main(String[] args) {
        lab1prog02 myThread = new lab1prog02("myThread");
        myThread.start();
    }
}