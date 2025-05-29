class A {
    public void EnterAndWait(int nh){
        try{
            System.out.println("Start Thread " + nh);
            Thread.sleep((int)(Math.random() * 100));
            System.out.println("Finish Thread " + nh);
        } catch(InterruptedException e){
            System.out.println(e.getMessage());}   
    }
}
class B implements Runnable{
    A a;
    int nh;
    
    B(int nh, A a){
        this.nh = nh;
        this.a = a;    
    }
    
    public void run() {
        a.EnterAndWait(nh);
	}  
}

public class prob2 {
    public static void main(String[] args) {
        A objetoA = new A();
        int nh = 5;
        B bs[] = new B[nh];
        Thread ts[] = new Thread[nh];
        for(int i = 0; i < nh; i++){
            bs[i] = new B(i, objetoA);
            ts[i] = new Thread(bs[i]);
            ts[i].start();
        }
        
        try{
            for(int i = 0; i < nh; i++){
                ts[i].join();
            }
        }catch(InterruptedException e){
            System.out.println(e.getMessage());
        }
        System.out.println("Finished.");}
}