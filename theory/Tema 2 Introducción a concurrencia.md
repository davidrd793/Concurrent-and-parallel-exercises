Se correspondería al peor paradigma de programación paralela, la *memoria compartida*, en que el paralelismo es parcial y se ejecuta solo en las partes posibles, ya que muchos aspectos del programa se ejecutan secuencialmente por acceso a secciones críticas que solo un hilo puede gestionar a la vez. Este punto a medio camino del paralelismo se denomina `concurrencia`.
## Conceptos

###### DIspositivos físicos
Estructura de una CPU:

![[Pasted image 20250523175344.png]]

**Ley de Moore**: en una placa del mismo tamaño, cada dos años se duplica la cantidad de transistores que caben en ella.

###### Medidas
**Fórmula del consumo de energía CPU**: 

![[Pasted image 20250523175729.png]]

**Latencia**: tiempo requerido en la primera respuesta del sistema. La concurrencia puede reducir la latencia o ocultarla (ejecutar otros procesos durante uno con alta latencia).
**Rendimiento**: cantidad de eventos que se pueden realizar por unidad de tiempo, una buena métrica para el rendimiento es la *latencia* (la concurrencia aumenta el rendimiento de un sistema). 
**Escalabilidad**: 
	- *Vertical*: mejorando los recursos existentes.
	- *Horizontal*: aumentando el número de recursos disponibles.

###### Multiprocesamiento simétrico (SMP)
Nos basaremos en programar siguiendo un modelo SMP, es decir, que deriva una tarea en subtareas a los distintos núcleos de procesamiento disponible, aumentando el *rendimiento* y reduciendo la *latencia*. La mayoría de ordenadores modernos con procesador multinúcleo utilizan SMP gestionado por el OS, es decir, SMP es principalmente aplicado a arquitecturas multiprocesador.

*Simétrico* -> Los núcleos de procesamiento poseen las mismas capacidades, así como las mismas características de acceso a memoria.

> El desafío en la programación está en gestionar los accesos a la memoria compartida.

## Programación de un Thread
< Se supone dominio de conceptos de interfaz, extensión e implementación > 
###### Concepto de Thread
 - Es la unidad mínima de ejecución dentro de un proceso, comparte memoria con el resto de threads (con los que se ejecuta de forma paralela en la medida de lo posible)de su mismo proceso pero ejecuta una secuencia de instrucciones independiente. El lanzamiento de varios threads en núcleos distintos es lo que crea el paralelismo, pero deben estar debidamente *sincronizados*.

- Un Thread posee varias características (nombre, identificador, prioridad) extraíbles mediante métodos de la clase Thread (Java), pero más importante aún, *atraviesa estados*:

![[Pasted image 20250524165759.png]]

###### Creación Thread en Java
Dos métodos: 

**Clase abstracta Thread**: herencia directa sobreescribiendo el método run() de la clase, se llama mediante start().

**Interfaz Runnable**: requiere escritura de un método run() y la creación de un Thread() pasandole nuestra interfaz como plantilla, pero permite *herencia múltiple* dado que es una interfaz.

###### Paso de datos a un Thread
- *Inyección por constructor*: la clase workerThread tiene un constructor con los datos necesarios para ser inicializados en la instanciación.
- *Setters*: los datos del constructor del workerThread son privados y solo accesibles por getters y setters.

#### Elementos comunmente utilizados

###### Métodos
*Thread.currentThread()*: referencia al mismo hilo en que se llama, sirve para la extracción de datos sobre el thread actual.

*Thread.sleep(n)*: detener la ejecución en ese punto n milisegundos. -> La JVM elimina el hilo de la lista de activos durante ese tiempo.
*Bloques try-catch*: obligatorios para manejar sleep(), en concreto para manejar su InterruptedException().

*ThreadX.isAlive*: comprueba si ThreadX sigue en ejecución -> bool
*ThreadX.join()*: detiene el hilo hasta que ThreadX acabe su ejecución.

###### Arrays para Threads
Para lanzar un gran número de hilos, podemos crear arrays de Threads e iterar sobre ellos para inicializarlos, hacer joins()...

**Arrays estáticos**: array nativo de Java, con tamaño invariable.
**Arrays dinámicos**: ofrecido por la API de ArrayList, permiten más flexibilidad gestionando internamente el redimensionado del array. Se complementa con otros objetos de la API de Java como Iterator:
- *Iterator*: permite recorrer uno a uno los elementos de una sucesión, abstrayendose de su estructura interna y habilitando métodos como .remove().


