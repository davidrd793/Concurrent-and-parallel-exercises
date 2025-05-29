class FinTrans{
    public String transName;
    public double amount;
}


class TransThread extends Thread {
    private FinTrans ft;
    
    TransThread (FinTrans ft, String name)  {
        super (name); // Save thread's name
        this.ft = ft; // Save reference to financial transaction object
    }
    
    public void run () {
        for (int i = 0; i < 100; i++) {
           ft.transName = Thread.currentThread().getName();
           System.out.println(ft.transName);
            // Ya está hecho en GranoX.java
        }
    }
}


class lab2prob02 {
    public static void main (String [] args)  {
        FinTrans ft = new FinTrans ();
        TransThread tt1 = new TransThread (ft, "Deposit");
        TransThread tt2 = new TransThread (ft, "Withdrawal");
        tt1.start ();
        tt2.start ();
    }
 }
