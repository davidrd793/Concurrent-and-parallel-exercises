Una vez visto el concepto de concurrencia y como implementarlo mediante Threads en Java, ahora hay que abarcar sus problemas y soluciones, es decir, aquello que el programador debe implementar para trabajar con hilos correctamente.

>  Sincronización: conjunto de mecanismos que previenen los *problemas de la concurrencia* y permiten a los hilos trabajar de forma coordinada y eficiente manteniendo el máximo nivel de paralelismo posible.

## Problemas de la concurrencia
Ocurren principalemente entorno a la *memoria compartida*.

**Condiciones de carrera**: cuando varios hilos leen y modifican el mismo dato, pueden solapar su ejecución y leer datos erróneos.
**Deadlocks**: cuando se cruzan dos necesidades de hilos bloqueandose mutuamente de forma indefinida. Soluciones: aplicar tryLocks() o hacer bloqueos en orden no cruzado.
**Coordinación de hilos**: gestión de dependencias entre threads.
**Sobrecarga de rendimiento**: la creación/destrucción de hilos así como el uso de mecanismos de espera afecta a los beneficios del paralelismo y pueden reducir la rentabilidad de trabajar concurrentemente. 

---
# Mecanismo vs Primitiva de sincronización
- *Mecanismo de sincronización*: se refiere a cualquier técnica implementada para realizar sincronización de hilos.
- *Primitiva de sincronización*: los bloques más básicos que Java ofrece para implementar la sincronización como programador. -> mutex, semáforos, variables atómicas ...

A continuación, veremos una serie de primitivas de sincronización aumentando su complejidad.
### Mutexes (MUTual EXclusion)
Se basan en el principio de "uno a la vez" haciendo un bloqueo a todos los demás threads en ejecución (pasan a estado WAITING). Para entender su funcionamiento, hay que hacer referencia al término que aluden:

**Sección crítica**: zona específica del código en que se produce el acceso a memoria compartida por parte de varios hilos, es la zona donde se producen los *problemas de la concurrencia* y en donde los mutexes deben actuar.

##### Synchronized
Es el mecanismo más básico, se trata de declarar implícitamente un bloque o función como "synchronized", de modo que un hilo no entrará a esa sección hasta que otro que estuviera dentro salga, es un mecanismo sin orden de entrada, sin apenas control y que produce *polling*.

*Funcionamiento*: cada objeto en Java tiene un monitor (cerrojo) asociado, de forma que realizamos un synchronized sobre un objeto y cada hilo que entre a la sección de código va a adqurirlo para poder entrar, no pudiendo adquirirlo si otro hilo lo tiene en ese momento determinado. -> Importante que el objeto para el bloqueo sea un `objeto compartido por todos los threads`.

>  PREGUNTA DE EXAMEN:  por que falla la sincronización de run() o sincronizar con this() ?? -> variables de instancia.

###### Implementación de synchronized
El funcionamiento es similar, pero podemos declarar un método entero como 'synchronized' o definir bloques explícitos de sincronización:

``` Java
public void synchronized miMetodo1() {
	//Critical section
}

public void miMetodo2() {
	// Some code without critical instructions
	synchronized(objetoMonitor) {
		//Critical section
	}
}
```


##### Locks (*Reentrant Lock*) 
Mecanismo de mutex más robusto, que permite operaciones más complejas que 'synchronized', como establecer un orden, hacer trylocks() que solo entran al lock si está disponible o permiten la reentrancia para mitigar el deadlocking. Entre los más usados, tenemos dos tipos de lock:

- **Reentrant Lock**: permite que un hilo que libera el monitor del objeto vuelta a adquirirlo sin restricción.
- **ReadAndWriteLock**: distingue acciones de lectura o escritura (las acciones de lectura pueden ocurrir de forma paralela, es la escritura la que debe ser secuencial), útil para casos con muchos threads lectores.

###### Implementación de lock
``` Java 
lock.lock();
try { // O .trylock(n) para n segundos de intento
	//Critical section
} finally {
	lock.unlock();
}
```

### Variables atómicas
Una herramienta mejor para sincronizar *variables individuales*, se basa en encapsular un valor dado y dotar de métodos para su modificación con concurrencia gestionada automáticamente por el *mecanismo CAS*.
Útil en casos como contadores o banderas de modificación por múltiples hilos, ya que es más simple y genera menos sobrecarga que un bloqueo explícito.

``` Java 
AtomicInteger counter = new AtomicInteger(0); //o AtomicBoolean ...

int value1 = counter.incrementAndGet();
int value2 = counter.decrementAndGet();
int value3 = counter.get();
int value 4 = counter.getAndAdd(n);
int value 5 = counter.addAndGet(n);
counter.compareAndSet(3, 100);
```

###### CAS
Es una instrucción atómica que lee 3 valores:
 - La dirección en memoria (V)
 - El valor esperado (A)
 - El valor nuevo (B)
De esta forma, el valor de V se actualiza a B solo si el valor que lee es A (es decir, si otro thread no lo ha cambiado), en caso contrario vuelve a empezar la comprobación.
Sus mayores desafíos son la gestión del problema ABA y el spin-waiting.
