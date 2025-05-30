import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;


class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("Mi nombre es: " + this.getName());
        System.out.println("Finalizado el proceso " + this.getName());
    }
}
public class ListaDinamica {
    public static void main(String[] args) {
        final int NUMERO_THREADS = 5;
        List<Thread> threadList = new ArrayList<>(NUMERO_THREADS);

        for (int i = 1; i <= NUMERO_THREADS; ++i) { // IMPORTANTE: No podemos usar el iterador para llenar el array de nuevos threads (no posee indice int i;)
            threadList.add(new MyThread());
            System.out.println("Thread " + i + " creado");
        }

        Iterator<Thread> iterator = threadList.iterator();
        while (iterator.hasNext()) {
            iterator.next().start();
        }

        iterator = threadList.iterator(); // IMPORTANTE: Resetear el iterador para el join()
        while(iterator.hasNext()) {
            Thread t = iterator.next();
            try {
                t.join();
                System.out.println("Terminado realmente " + t.getName());
            } catch(InterruptedException e) {
                System.out.println("Error");
            }
        }

        System.out.println("El programa ha terminado");
    }
}