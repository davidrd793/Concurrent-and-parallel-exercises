import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class Ej02_SynchStack {
    public static void main(String[] args) {
        System.out.println("Este código es para ver como implementar la lista no para ejecutarlo, echale un ojo");
    }
}

class NodeInt {
    int value;
    NodeInt next;

    public NodeInt(int value) {
        this.value = value;
        this.next = null;
    }
}

class Synchronized_Stack {
    private NodeInt top;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notEmptyCondition = lock.newCondition();    

    public Synchronized_Stack() {
        this.top = null;
    }

    public void push(int value) {
        lock.lock();
        NodeInt newNode = new NodeInt(value);
        if (top != null) {
            newNode.next = top; //Si hay nodo en la cabeza de la pila, este es siguiente al nuevo nodo
            //Esto significa que para lo que sirve next es para ver con quien es adyacente cada nodo, es casi el anterior en vez del siguiente
            //Como regla para recordarlo, el next del top va a ser el nuevo top si este desaparece, por eso es next
        }
        top = newNode; //Nueva cabeza de la pila
        notEmptyCondition.signalAll();
        lock.unlock();
    }

    public int pop() {
        lock.lock();
        while(top == null) {
            try {
                notEmptyCondition.await();
            } catch (InterruptedException e) {
                System.out.println("Hubo un error");
            }
        }
        int value = top.value; //Se recoge el valor del top
        top = top.next; //El nuevo top es el next del top anterior
        lock.unlock();
        return value; //Se devuelve el valor
    
    }

    public int peek() {
        lock.lock();
        if (top == null) {
            try {
                notEmptyCondition.await();
            } catch (InterruptedException e) {
                System.out.println("Hubo un error");
            }
        }
        int valor = top.value;
        lock.unlock();
        return valor;
    }

    public boolean isEmpty() {
        return top == null;
    }
}