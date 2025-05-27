/*Los locks son una alternativa de los bloques synchronized que ofrece más control sobre la exclusión mutua, a cambio de ser más complejos de implementar. 
 * Esto se debe a que a través de estos podemos acceder a métodos para poner en espera a threads hasta que otros threads hagan lo que tienen que hacer.
*/

/*Este código consistirán en dos threads que jugarán al PingPong, donde el que grita PING avisará al que grita PONG para que lo haga, y viceversa*/
class SharedResource {
    Object lock = new Object();
    boolean ping = false;
}

class Ping extends Thread {
    private final SharedResource sharedResource; 
    
    Ping (SharedResource sharedResource) {
        this.sharedResource = sharedResource;
    }

    @Override
    public void run() {
        for (int i = 0 ; i < 20; i++) {
            synchronized(sharedResource.lock) {
                while(sharedResource.ping) {
                    try {
                        sharedResource.lock.wait();
                    } catch (InterruptedException e) {
                        System.out.println("Hubo un problema");
                    }
                }
                System.out.print("Ping...    ");
                sharedResource.ping = true;
                sharedResource.lock.notifyAll();
            }
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
            synchronized(sharedResource.lock) {
                while (!sharedResource.ping) {
                    try {
                        sharedResource.lock.wait();
                    } catch (InterruptedException e) {
                        System.out.println("Hubo un problema");
                    }
                }
                System.out.println("Pong!");
                sharedResource.ping = false;
                sharedResource.lock.notify(); //Al solo ser un thread se puede notificar uno 'al azar' (porque siempre va a tocar el otro claro)
            }
        }
    }
}



public class Ej04_Locks {
    public static void main(String[] args) {
        SharedResource sr = new SharedResource();
        Ping ping = new Ping(sr);
        Pong pong = new Pong(sr);
        ping.start();
        pong.start();
    }
}
