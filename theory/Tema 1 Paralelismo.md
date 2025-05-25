**Sistema de procesamiento paralelo**: conjunto de procesadores capaces de trabajar de forma simultánea para resolver un problema conjunto (a menudo subdividido). En IA es necesario recurrir al paralelismo para la realización de cálculos intensivos e.j. Deep Learning.

## Nociones de Arquitectura
###### Arquitectura Von Neunman
Modelo clásico para computadores de un solo procesador, en que la memoria (que contiene tanto instrucciones como datos) se conecta por un único bus a la unidad lógica, produciendo su mayor fallo, el "cuello de botella de Von Neunman" y ejecutando las instrucciones de forma *secuencial*.

###### Arquitecturas multiprocesador
En ella, múltiples procesadores (normalmente con caché propia), acceden a la misma *memoria compartida* y su comunicación ocurre a través de ella (lectura y escritura de datos). Dos tipos:
	- *Memoria monolítica*: arquitectura con una sola memoria para todos los procesadores.
	- *Memoria en bancos*: arquitectura escalada que tiene una memoria para cada n procesadores. 

* Estas disposiciones dan lugar a problemas como: condiciones de carrera, lecturas sucias...

###### Arquitectura multicomputador
Disposición en que cada nodo constituye un ordenador completo (unidad de procesamiento y memoria privadas), de forma que no existen problemas de concurrencia y los problemas de cuello de botella pueden ser gestionados por diseños eficientes de la red. Dada esta arquitectura, el desafío del programador es la comunicación y gestión de los nodos -> El estándar para esta tarea es MPI.

###### Arquitecturas vectoriales
Especializadas en el procesamiento de vectores y la realización de operaciones sobre ellos mediante el uso de *pipelines*. Son las precursoras conceptuales de las GPUs.

## Modelos paralelos
Conceptualización de las características de una tarea en base a un criterio, de cara a su adaptación para las arquitecturas anteriores.
###### Clasificación de Flynn -> Tareas
Describe las tareas como flujo de datos y flujo de instrucciones:
	- *SISD*: flujo clásico secuencial, perfecto para las arquitecturas estilo Von Neunman.
	- *SIMD*: operaciones sobre un vector, ideal para arquitecturas vectoriales o GPUs modernas.
	- *MISD*: uso de pipelines para encadenamiento de instrucciones, arquitecturas vectoriales.
	- *MIMD*: tipo de tarea más complejo que requiere arquitecturas multiprocesador o multicomputador, tambien es el que permite mayor grado de paralelismo.

###### Clasificación según organización de la memoria -> Arquitecturas
- *Memoria compartida* -> sistema multiprocesador.
- *Memoria distribuida* -> sistemas multicomputador.

## Paradigmas de programación paralela
Enfoques conceptuales para el diseño de programas paralelos y su ejecución (realmente todos los temas del curso van sobre ver los 3 tipos de paradigmas, te odio Leandro aprende a redactar).

**Paralelismo en datos**: la tarea es ejecutada de forma secuencial, se optimiza la velocidad mediante el paralelismo en el cálculo (en la ejecución de operaciones sobre los datos). Respecto a Flynn, se corresponderia con SIMD o incluso MIMD. -> Nvidia Cuda y lanzamiento de cálculo a GPU.
**Memoria compartida**: la tarea es ejecutada en múltiples threads, que comparten direcciones en memoria. Encaja con tareas MIMD. -> Concurrencia
**Paso de mensajes**: la tarea es ejecutada en en varios procesos (memoria independiente), por lo que se gestiona el intercambio de mensajes entre procesos. -> MPI.

## Técnicas de paralelización
Esto es, las formas que tenemos de *descomponer un problema en partes para su paralelización* para posteriormente ejecutarlas en un dispositivo físico (*arquitectura*) de acuerdo a un *paradigma*, para lo que debemos comprender la naturaleza de la tarea (*tipo seǵun modelo paralelo*).
Tipos de descomposición:

- Geométrica: división del espacio de datos en subconjuntos más pequeños. Orientado a datos.
- Iterativa: identificación de bucles (parte repetida múltiples veces) independientes entre sí para distribuir la ejecución.
- Recursiva: subdivisión por estrategia similar a "divide y vencerás" y su posterior unión de resultados.
- Especulativa: lanzamiento de la tarea en todos los procesadores dado un elemento desconocido que influye en el resulltado, para obtener el que mejor funcione -> modo competición.
- Funcional: división de un programa en subprocesos. Orientado a tareas.

## Conceptos
**Granularidad**: se refiere al tamaño relativo de las subtareas con respecto a la tarea completa, se trata de balancear o seleccionar un tamaño de tarea según nuestras necesidades concretas, un *grano grueso* puede no estar explotando el paralelismo todo lo que podría, un *grano fino* puede tener elevados requerimientos para la comunicación entre procesos.

###### Medidas de éxito
**Aceleración**: medida de cuantas veces mejora el tiempo de ejecución de nuestro programa paralelo respecto a ejecutarlo de forma secuencial: 

![[Pasted image 20250523173808.png]]

Esta aceleración puede crecer en igual medida que el número de procesadores (*aceleración lineal*) o no hacerlo (*aceleración sublineal o superlineal*). 
**Eficiencia**: mide el uso de los procesadores, eficiencia = 1 indicaría una aceleración lineal.

![[Pasted image 20250523174203.png]]
###### Leyes teóricas
**Ley de Amdahl**: establece un límite para la aceleración máxima de un programa basada en la fracción no paralelizable de un programa.
**Ley de Gustafson**: modificando esta fracción paralelizable del programa (entre 0 y 1), podemos obtener cualquier eficiencia posible.
