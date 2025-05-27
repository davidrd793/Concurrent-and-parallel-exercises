import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.List;
import java.util.ArrayList;

public class Ej02_FutureYCallable {
    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        List<Callable<Integer>> tareas = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            final int numero = i;
            tareas.add(() -> {
                System.out.println("Soy el hilo " + Thread.currentThread().getName() + " y mi número es: " + numero);
                Thread.sleep(500);
                return numero;
            });
        }

        List<Future<Integer>> resultados = executor.invokeAll(tareas);

        for (Future<Integer> resultado : resultados) {
            System.out.println("Resultado recibido: " + resultado.get());
        }

        executor.shutdown();
    }
}