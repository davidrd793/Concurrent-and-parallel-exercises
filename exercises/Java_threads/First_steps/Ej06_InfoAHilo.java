/*
 * A la hora de aplicar estos conocimientos a un ejercicio útil, lo más normal es que sea necesario pasarle datos a hilos para que los manejen.
 * Esto es un proceso que se puede hacer tanto mediante Inyección por Constructor como Métodos setter 
*/

class Ej06_InfoAHilo {
    public static void main(String[] args) {
        
        //Necesario para inyección por constructor
        String data = "Datos de ejemplo"; 
        Thread thread = new Thread(new HiloInformado1(data));
        thread.start();

        //Necesario para método setter
        HiloInformado2 thread2 = new HiloInformado2();
        thread2.setData("Datos de ejemplo 2");
        thread2.start();
    } 
}

class HiloInformado1 implements Runnable {
    private String data;

    //Inyección por constructor
    public HiloInformado1(String data) {
        this.data = data;
    }

    @Override
    public void run() {
        System.out.println("Datos recibidos: " + data);
    }
}

class HiloInformado2 extends Thread {
    private String data2;

    public void setData(String data2) {
        this.data2 = data2;
    }

    @Override
    public void run() {
        System.out.println("Datos recibidos: " + data2);
    }
}