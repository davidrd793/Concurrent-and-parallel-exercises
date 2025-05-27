/* Para entrar un poco en contexto, primero se enseña un ejemplo de ejercicio en el que el acceso al objeto compartido no se restringe, y de los problemas que
 * eso termina ocasionando. Las razones de dichos problemas son demasiado extensas como para explicarlo aquí, por lo que se encuentra en la parte de teoría
 */

class Ej01_ProblemaSinSincronizacion {
    public static void main(String[] args) {
        FinTrans ft = new FinTrans();
        TransThread tt1 = new TransThread(ft, "Deposit");
        TransThread tt2 = new TransThread(ft, "Withdrawal");
        tt1.start();
        tt2.start();
    }
}

class FinTrans {
    public static String transName;
    public static double amount;
}

class TransThread extends Thread {
    private FinTrans ft;

    TransThread (FinTrans ft, String name) {
        super(name);
        this.ft = ft;
    }
    public void run() {
        for (int i = 0 ; i < 100; i++) {
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