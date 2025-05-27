import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Ej01_Executor {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit(() -> {
            System.out.println("Hola desde un hilo del executor" + Thread.currentThread().getName());
        });

        executor.shutdown();
    }
}
