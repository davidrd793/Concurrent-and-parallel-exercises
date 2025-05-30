import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorExample {
    public static void main(String[] args) {
        ExecutorService ex = Executors.newSingleThreadExecutor();


        Callable<Integer> task = new Callable<Integer>() {
            public Integer call() {
                try {
                    Thread.sleep(1000);
                } catch ( InterruptedException e) {}
                return 123;
            }
        };

        Runnable task2 = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hola");
            }
        };

        Future<Integer> future = ex.submit(task);
        ex.submit(task2);

        // Some work
        try {
            try {
                Integer value = future.get();
                System.out.println(value);
            } catch (ExecutionException e) {}
        } catch (InterruptedException e) {}
        ex.shutdown();
    }
}
