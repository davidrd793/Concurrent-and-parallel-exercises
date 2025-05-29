class lab2prob03 {
    public static void main (String [] args)  {
	 FinTrans ft = new FinTrans();
       // Shared lock object
       Object lock = new Object();
	   TransThread tt1 = new TransThread(ft, "Deposit Thread", lock);
       TransThread tt2 = new TransThread(ft, "Withdrawal Thread", lock);
       tt1.start ();
       tt2.start ();
    }
 }



class FinTrans{
    public String transName;
    public double amount;
}



class TransThread extends Thread {
    private FinTrans ft;
    private Object lock; // IMPORTANTE: Como objeto para obtener el monitor o crear el lock, se suele emplear un Object genérico

    TransThread(FinTrans ft, String name, Object lock) {
        super(name);
        this.ft = ft;
        this.lock = lock;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            if (getName ().equals ("Deposit")){
                synchronized(lock) {
                    ft.transName = "Deposit";
                    try {
                        Thread.sleep ((int) (Math.random () * 1000));
                    } catch (InterruptedException e) {}
                    
                    ft.amount = 2000.0;
                    System.out.println (ft.transName + " " + ft.amount);
                }
            } else {
                synchronized(lock) {
                    ft.transName = "Withdrawal";
                    try {
                        Thread.sleep ((int) (Math.random () * 1000));
                    } catch (InterruptedException e)  {}
                    ft.amount = 250.0;
                    System.out.println (ft.transName + " " + ft.amount);
                }
            }
        }
    }
}