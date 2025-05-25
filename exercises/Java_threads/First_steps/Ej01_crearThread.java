//FUNDAMENTOS

/* Para trabajar con threads en java, la arquitectura más básica que podemos hacer consiste en hacer dos clases:
- Una clase principal, con el método main (que se ejecuta al momento de ejecución del código)  
- Una clase trabajadora thread, que contiene la info y las instrucciones que los threads realizarán 

En cuanto a la creación de la clase trabajadora, Java ofrece soporte para la creación automática de Threads, en el paquete 'java.lang.Thread'.
Esta clase puede recibir los argumentos que se ven en teoría, veremos, por ahora, dos formas de crear clases trabajadoras en Java 
- Que la clase herede de la clase Thread, que facilita Java
- Que la clase implemente de la interfaz Runnable
*/

class Ej01_crearThread {
    public static void main (String[] args) {
        //Los threads se crean instanciándolos
        Thread threadHeredado = new threadHeredado();
        Thread threadImplementado = new Thread(new threadImplementado());
        
        //Una vez instanciados los threads, se inician con el método start de la clase Thread
        threadHeredado.start();
        threadImplementado.start();
    }
}

//Crear Thread mediante herencia
class threadHeredado extends Thread{
    //Se sobrescribe el método run para que se ejecute cuando el método principal lo llame
    @Override
    public void run() {
        System.out.println("Soy aquel que hereda");
    }
}

class threadImplementado implements Runnable {
    //Lo mismo
    @Override
    public void run() {
        System.out.println("Soy aquel que implementa");
    }
}

