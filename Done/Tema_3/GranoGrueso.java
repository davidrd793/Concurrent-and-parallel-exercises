class FinTrans{
    public String transName;
    public double amount;
}
class TransThread extends Thread {
    private FinTrans ft;
   
    TransThread (FinTrans ft, String name)  {
        super (name); 
	    this.ft = ft; 
    }
    
    public void run () {
        for (int i = 0; i < 10; i++) {
            synchronized(ft) {
                if (getName ().equals ("Deposit")){
                    ft.transName = "Deposit";
                    try {
                        Thread.sleep ((int) (Math.random () * 1000));
                    } catch (InterruptedException e) {}
                    
                    ft.amount = 2000.0;
                    System.out.println (ft.transName + " " + ft.amount);
                } else {
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

class GranoGrueso {
    public static void main (String [] args)  {
        FinTrans ft = new FinTrans ();
        TransThread tt1 = new TransThread (ft, "Deposit");
        TransThread tt2 = new TransThread (ft, "Withdrawal");
        tt1.start ();
        tt2.start ();

        try {
            tt1.join();
            tt2.join();
        } catch (InterruptedException e) {}

        System.out.println("--- En este ejemplo el paralelismo es mínimo y no aprovechamos bien esta herramienta ---");
    }
 }
