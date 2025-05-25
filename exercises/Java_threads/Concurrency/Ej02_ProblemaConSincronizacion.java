/* La primera herramienta para realizar exclusión mutua y así proteger los datos compartidos es el uso de Synchronize, que puede usarse
 * como un bloque (como en este ejemplo) o como un método. Se puede usar tanto el propio objeto a compartir como cualquier otro objeto
 * creado expresamente para que haga de "llave"
 */
class Ej02_ProblemaConSincronizacion {
        public static void main(String[] args) {
        FinTrans ft = new FinTrans();
        TransThread2 tt1 = new TransThread2(ft, "Deposit");
        TransThread2 tt2 = new TransThread2(ft, "Withdrawal");
        tt1.start();
        tt2.start();
    }
}

class FinTrans {
    public static String transName;
    public static double amount;
}

class TransThread2 extends Thread {
    private FinTrans ft;

    TransThread2 (FinTrans ft, String name) {
        super(name);
        this.ft = ft;
    }
    @Override
    public void run() {
        for (int i = 0 ; i < 100; i++) {
            synchronized(ft) {
                if (getName().equals("Deposit")) {
                    ft.transName = "Deposit";
                    try {
                        Thread.sleep((int) (Math.random()*1000));
                    } catch (InterruptedException e) {
                        System.out.println("Hubo un problema");
                    }
                    ft.amount = 2000.0;
                    System.out.println(ft.transName + " " + ft.amount);
                } else {
                    ft.transName = "Withdrawal";
                    try {
                        Thread.sleep((int) (Math.random()*1000));
                    } catch (InterruptedException e) {
                        System.out.println("Hubo un problema");
                    }
                    ft.amount = 250.0;
                    System.out.println(ft.transName + " " + ft.amount);
                }
            }    
        }
    }   
}