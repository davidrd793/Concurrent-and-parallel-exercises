Hemos aprendido hasta el momento, como realizar hilos y manejar todo tipo de coordinaciones para hilos *manualmente*, pero los problemas de esto crecen exponencialmente a medida que nuestras aplicaciones crecen en tamaño y necesidades, es por ello que existen estructuras que sirven como "gestores automáticos" sobre nuestros hilos para optimizar aun más las tareas y la creación/destrucción de hilos.

##### TAREA PRELIMINAR
- Por cuestiones de manejo de parámetros y  simplicidad, es muy recomendable escribir nuestros métodos run() mediante funciones lambda o tambien llamados *Runnable anónimos*, aquí se deja un rápido ejemplo de equivalencia:

``` Java
class workerThread implements Runnable {
	public void run() {
		//Some work
	}
}

// Dentro de main ...
int variable = 0;
Runnable r = new Runnable() {
	public void run() {
		doWork(variable); //Podemos pasar variable más facilmente
	}
r.start();
}
```

---

## Interfaz Executor
- **Explicación**: interfaz de *ejecución asíncrona* ofrecida por Java para el desacoplamiento de tarea y ejecución de la tarea y gestión eficiente de hilos en segundo plano. Otros beneficios secundarios son:
	- Ofrece servicio de apagado secuencial para hilos.
	- Reutiliza hilos en base a su capacidad de n hilos.
	- Pueden organizarse los hilos en grupos para resolver tareas conjuntas.

> Asíncrono: el hilo que lanza 'Executor' continua su ejecución sin esperar por el resultado.

![[Pasted image 20250527121822.png]]

-> ExecutorService extiende a Exercutor y añade métodos más avanzados de control.

- **Como implementarlo**: debemos crear un objeto 'ExecutorService' con una pool de hilos (lo veremos más adelante) y cuando queramos utilizarlo, pasarle objetos Runnable o [Callable] (no vale Thread) para que el executor administre su ejecución adecuadamente en su capacidad.

Los executors pueden tener un número estático o dinámico de hilos, estos son los tipos para crearlos: 

![[Pasted image 20250527123220.png]]

> Nota: esta organización que aprovecha el polimorfismo aprovecha el patrón Factory Method, lo cual simplifica la adición e implementación al máximo.

## Future y Callable
Hasta ahora, hemos implementado threads por Runnable o Thread, pero a veces requerimos que nuestros hilos no solo ejecuten una serie de instrucciones, sino que devuelvan un resultado, el método void run() debe no devolver nada, pero existe una alternativa para lograrlo:

###### Callable
Interfaz de igual implementación que Runnable, que permite la devolución de un valor y que *puede ser implementado a executors en su método .submit()*. Dado que las tareas Callable se mandan a un executor y estas se ejecutan de manera asíncrona, debemos definir un objeto especial que reciba el valor de retorno o detenga al programa esperando por el.

```Java
public class CallableExample {
	ExecutorService ex = new Executors.newSingleThreadExecutor();

	Callable<Integer> task = () -> {
		Thread.sleep(1000);
		return(1);
	}

	Future<Integer> future = ex.submit(task);
	Integer result = future.get(); //Blocked there until callable task is done

	// Do something with result
}
```

###### Future
Interfaz que permite obtener el resultado de la tarea asíncrona lanzada por Callable.

``` Java

// Métodos

Future<Integer> future = executor.submit(someTask);

Ingeter returned = future.get(); //Espera al retorno del valor
future.cancell(); // Cancela la tarea asíncrona
bool state = future.isCancelled()
```