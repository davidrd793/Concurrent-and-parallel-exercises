/*
 * Un método útil es join, que hace que el hilo que llama al método espere hasta que el hilo indicado termine su ejecución.
 * Se usa a menudo para hacer que el hilo principal termine después de que lo hagan sus hilos hijo
*/

class Ej05_Joins {
    public static void main(String[] args) {
        System.out.println("Papá Thread creando a su hijo");
        Thread thread = new JoinedThread();
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            System.out.println("Thread interrumpido a la fuerza");
        }
        System.out.println("Soy Papá hijo y esperé por mi hijo para terminar");    
    }
}

class JoinedThread extends Thread {
    @Override
    public void run() {
    System.out.println("Soy el thread hijo y voy a hacer que el hilo principal espere muuucho por mi");
    try {
        Thread.sleep(5000);
    } catch (InterruptedException e) {
        System.out.println("Thread interrumpido a la fuerza");
    }
    System.out.println("Soy el thread hijo y terminé");
    }
}