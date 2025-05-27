package Java_threads.Sync_Structures;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Ej03_SynchQueue {
    public static void main(String[] args) {
        System.out.println("Lo mismo que el anterior!");
    }
}

class Node {
    int value;
    Node next;

    public Node(int value) {
        this.value = value;
        this.next = null;
    }
}
class Synchronized_Queue {
    private Node head, tail;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();

    public Synchronized_Queue() {
        this.head = this.tail = null;
    }

    public void enqueue(int value) {
        lock.lock();
        Node newNode = new Node(value);
        if (this.tail == null) {
            this.head = this.tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        notEmpty.signalAll();
        lock.unlock();
    }

    public int dequeue() {
        lock.lock();
        while (this.head == null) {
            try {
                notEmpty.await();
            } catch (InterruptedException e) {
                System.out.println("Hubo un problema");
            }
        }
        int value = this.head.value;
        this.head = this.head.next;
        if (this.head == null) {
            this.tail = null;
        }
        lock.unlock();
        return value;
    }

    public int peek() {
        lock.lock();
        while (this.head == null) {
            try {
                notEmpty.await();
            } catch (InterruptedException e) {
                System.out.println("Hubo un problema");
            }
        }
        int value = this.head.value;
        return value;
    }
}
