import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
/*En ciertas ocasiones, es beneficioso usar arrays dinámicos para esto, lo que se hace de la siguiente manera */

class Ej04_ListaDinamicaThread {
    public static void main(String[] args) {
        // Creamos el array dinámico de Threads
        final int numero_hilos = 10;
        List<Thread> threadList = new ArrayList<>(numero_hilos);
        
        //Bucle para rellenar la lista de Threads
        for (int i = 0; i < numero_hilos; i++) {
            threadList.add(new Thread(new HilosDinamicos()));
        } 

        //Iterator para recorrer la lista rellenada y comenzar los threads
        Iterator<Thread> l1 = threadList.iterator();
        while (l1.hasNext()) {
            l1.next().start();
        }
    }    
}

class HilosDinamicos implements Runnable {
    @Override
    public void run() {
        System.out.println("Soy un miembro del array dinámico");
    }
}