/*En caso de querer crear una cantidad considerable de threads, es recomendable utilizar estructuras como arrays.
 * A continuación se muestra un ejemplo de como se podrían crear 10 threads de golpe con un array estático
*/

class Ej03_ListaEstaticaThread {
    public static void main(String[] args) {
        Thread[] threads = new Thread[10];
        for (int i = 0 ; i < 10 ; i++) {
            threads[i] = new Hilos();
            threads[i].start();
        } 
    }
}

class Hilos extends Thread {
    @Override
    public void run() {
        System.out.println("Soy un thread del array estático");
    }
}