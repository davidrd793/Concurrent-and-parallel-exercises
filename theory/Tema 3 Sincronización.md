Una vez visto el concepto de concurrencia y como implementarlo mediante Threads en Java, ahora hay que abarcar sus problemas y soluciones, es decir, aquello que el programador debe implementar para trabajar con hilos correctamente.

> Sincronización: conjunto de mecanismos que previenen los *problemas de la concurrencia* y permiten a los hilos trabajar de forma coordinada y eficiente manteniendo el máximo nivel de paralelismo posible.

## Problemas de la concurrencia
Ocurren principalemente entorno a la *memoria compartida*.

**Condiciones de carrera**: cuando varios hilos leen y modifican el mismo dato, pueden solapar su ejecución y leer datos erróneos.
**Deadlocks**: cuando se cruzan dos necesidades de hilos bloqueandose mutuamente de forma indefinida.
**Coordinación de hilos**: gestión de dependencias entre threads.
**Sobrecarga de rendimiento**: la creación/destrucción de hilos así como el uso de mecanismos de espera afecta a los beneficios del paralelismo y pueden reducir la rentabilidad de trabajar concurrentemente. 


## Mutexes (MUTual EXclusion)
Son el mecanismo más básico de gestión de threads, se basan en el principio de "uno a la vez" haciendo un bloqueo explícito a todos los demás threads en ejecución (pasan a estado WAITING). Para entender su funcionamiento, hay que hacer referencia al término que aluden:

**Sección crítica**: zona específica del código en que se produce el acceso a memoria compartida por parte de varios hilos, es la zona donde se producen los *problemas de la concurrencia* y en donde los mutexes deben actuar.

#### Synchronized
Es el mecanismo más básica, se trata de declarar implícitamente un bloque o función como "synchronized", de modo que un hilo no entrará a esa sección hasta que otro que estuviera dentro salga.
