
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/*Ahora se va a realizar el ejercicio de Locks del tema anterior pero con ReentrantLock y Condition, para que se reconozcan las similitudes y los cambios
 * en la forma de usarlos
 */

class Ej01_ReentrantLock {
    public static void main(String[] args) {
        SharedResource sr = new SharedResource();
        Ping ping = new Ping(sr);
        Pong pong = new Pong(sr);
        ping.start();
        pong.start();
    }
}

class SharedResource {
    ReentrantLock lock = new ReentrantLock(); //Se crea el objeto lock, que se va a usar como se usaba el bloque synchronize con un objeto Object
    Condition condition = lock.newCondition(); //Un objeto Condition creado a partir de un metodo de lock y que se usa para los await y signal
    boolean flag = false; 
}

class Ping extends Thread {
    private final SharedResource sharedResource; 
    
    Ping (SharedResource sharedResource) {
        this.sharedResource = sharedResource;
    }

    @Override
    public void run() {        
        for (int i = 0 ; i < 20; i++) {
            sharedResource.lock.lock(); 
            while(sharedResource.flag) {
                try {
                    sharedResource.condition.await();
                } catch (InterruptedException e) {
                    System.out.println("Hubo un problema");                    
                }
            }
            System.out.print("Ping...    ");
            sharedResource.flag = true;
            sharedResource.condition.signalAll();
            sharedResource.lock.unlock();
        }
    }
}

class Pong extends Thread {
    private final SharedResource sharedResource;

    Pong (SharedResource sharedResource) {
        this.sharedResource = sharedResource;
    }

    @Override
    public void run() {
        for (int i = 0; i<20; i++) {
            sharedResource.lock.lock();
                while (!sharedResource.flag) {
                    try {
                        sharedResource.condition.await();
                    } catch (InterruptedException e) {
                        System.out.println("Hubo un problema");
                    }
                }
                System.out.println("Pong!");
                sharedResource.flag = false;
                sharedResource.condition.signal(); //Al solo ser un thread se puede notificar uno 'al azar' (porque siempre va a tocar el otro claro)
                sharedResource.lock.unlock();
            }
        }
    }