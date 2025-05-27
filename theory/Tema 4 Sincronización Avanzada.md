Una vez vistas las primitivas básicas para controlar las secciones críticas, podemos movernos hacia un mayor nivel de control sobre el flujo de nuestros programas, añadiendo una forma de *comunicación entre threads diferentes* mediante *variables de condición* añadidas a los mecanismos ya vistos, esta mezcla se conoce como `Monitores`.

**Monitor**: mecanismo de concurrencia a alto nivel que aplica *mutex + variables de condición* para comunicar hilos con diferentes tareas. 

---
## Monitores
**Idea**: ofrecer coordinación de hilos y eliminar el problema de `espera activa` mediante la suspensión temporal de hilos (pasan a estado WAITING y liberan el bloqueo del objeto) mediante wait() hasta ser notificados por notify(), notifyAll(), normalmente desde otro hilo.

![[Pasted image 20250525174430.png]]

> Podemos utilizar monitores tanto con 'synchronized' como con 'lock'. 

#### Monitores con *Synchronized*
Utilizaremos bloques 'synchronized' para que el hilo obtenga el bloqueo del objeto sobre el que se va a realizar (Un objeto de java.lang.Object trae los métodos que utilizaremos por defecto), entonces nos basaremos en un juego de banderas (una variable booleana decide que hilo espera y cual realiza tarea y notifica) para realizar  Object.wait(), Object.notify() y Object.notifyAll().

- Esto es principalmente práctico, no muy visible desde la teoría.

> PREGUNTA DE EXAMEN:  por que es necesario que wait()/notify()  esten dentro de un bloque sincronizado?      Pueden producirse condiciones de carrera y derivar en problemas como señal perdida si no se hace.

#### Monitores con *Lock* PENDIENTE
Al igual que los locks eran mejores que los bloques synchronized en cuanto a exclusión mutua, tambien son más completos a la hora de realizar monitores con ellos, tanto por las razones ya vistas como porque permiten manejar mejor las [señales perdidas] y [despertares espurios].
Al igual que usabamos 'Object' para el uso de .wait()/.notify() ahora utilizaremos el objeto Condition, que posee los métodos .await()/.signall() para el mismo propósito, pero teniendo las ventajas de lock y permitiendo separar la lógica de sincronización de la lógica de señalización.

``` Java
Condition miCondicion = new lock.newCondition();
```


#### Problemas a enfrentar
El uso de esta poderosa estructura requiere del manejo de nuevos errores por parte del programador:

- **Señal perdida**: sin sincronización sobre el monitor del Objeto que realiza wait() y notifiy(), se pueden dar casos de notificación antes de que un hilo llegue a wait(), quedando atascado ahí indefinidamente.
- **Despertares Espurios**: un hilo puede salir de wait() inesperadamente, por lo que la comprobación de variables que lo induzcan deben ser comprobadas por un *while(bandera)* en vez de con if(bandera), pese a inducir *polling*.


## Patrones con monitores
Este manejo de condiciones puede organizarse de distintas formas para realizar distintas tareas, veremos la clásica arquitectura Productor-Consumidor orientada a hilos:

###### Productor consumidor
Arquitectura clásica en diversos problemas en que ambos hilos se comunican mediante un buffer de datos (uno "produce" y añade datos al buffer y el otro los elimina). La exclusión mutua es necesaria para evitar corrupción del buffer, mientras que las variables condicionales de control sirven para evitar que el productor añada algo cuando el buffer está lleno o el consumidor tome algo si el buffer está vacío (los induce a estado WAITING).

--- 

## Más mecanismos de sinc. complejos

###### Semaphores
- Controlan el acceso a una sección crítica, pero permitiendo el paso de n hilos en vez de "uno a la vez" como en el caso de los mutexes. Funcionan ed forma similar a lock() pero con un número de permisos determinado:

``` Java
Semaphore miSemaforo = new Semaphore(3); // 3 hilos pueden entrar

semaphore.acquire();
// Some code
semaphore.release();


```

###### CountDownLatch
- Contador múltiple que detiene la ejecución de hilos en un punto hasta que se reduzca el contador a 0:

```Java
CountDownLatch miLatch = new CountDownLatch(n); //requiere n hilos
latch.await(); // Se detiene la ejecución

class MiThread extends Thread {
	//Code
	miLatch.countDwn(); //Reduce 1 el contador (n-1)
	//COde
}
```

###### Cyclic Barrier
- Barrera que detiene a los hilos hasta que hayan llegado a ella n hilos para "pasarla":

``` Java
CyclicBarrier miBarrier = new CyclicBarrier(4);

class miThread extends Thread {
	//Code
	miBarrier.await() //Detiene aqui hasta uqe llegaran 4 hilos
	// Code
}
```

###### Exchanger
- Para realizar intercambios de datos entre dos hilos, si uno llega al punto antes que otro, este espera hasta que el otro llegue al punto para realizar el intercambio.

## Productor consumidor con mecanismos avanzados
- Veremos como, aplicando *semáforos*, podemos crear un modelo productor-consumidor en el que se puede producir/consumir más de un elemento por unidad de tiempo.

IMPORTANTE: no se utiliza un semáforo estricto de Java en ninguno, se utiliza un semáforo específico diseñado para tomar valor 0 o 1 y tener métodos .up() y .down() que veremos en ejercicios.

###### Modelo de cambio de estados
- Utiliza un 'semProd' y un 'semCon' para forzar alternancia entre productor y consumidor, ideal para modelos de 1 productor y consumidor -> Ver ejemplos prácticos.
###### Enfoque con semáforos de conteo

