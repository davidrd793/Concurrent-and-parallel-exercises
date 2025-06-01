MPI (Message Passing Interface) es una especificación para paso de mensajes, esto significa, es un conjunto de procedimientos y estándares que definen con que elementos debemos jugar en nuestros programas para realizar la comunicación entre procesos. Al comprender que MPI *es una especificación*, resulta natural deducir que la implementación en python que utilizaremos (librería mpi4py), es una colección de funciones dads para nuestro desarrollo que siguen los estándares de MPI. 

Para ubicarnos, recordemos que un **programa paralelo** es aquel que cuenta con múltiples procesos ejecutándose con espacios en memoria separados, por lo que aquí, la tarea primordial para el programador es: distribuir la carga a nuestros procesos, coordinarlos, realizar pasos de mensajes entre ellos...

> PREGUNTA DE EXAMEN: En que tipo de arquitectura/programa se requiere el uso de MPI? Dado que comunica procesos, en aquellos que tengan múltiples procesos, por tanto es para arquitecturas multicomputador y programas MIMD.

## Conceptos de MPI

*Paso de mensajes*: acción de copiar los datos en memoria de un proceso a la memoria del proceso destino, operación cooperativa en que se realizan **envío** y **recepción**.
*Comunicador*: conjunto de procesos agrupados entre los que se puede realizar paso de mensajes, los veremos más en profundidad.
*Rango*: identificador de cada proceso dentro de un comunicador: 1, 2, ...
*Etiqueta*: parámetro adicional de un envío o recepción para hacer más segura la transacción (evitar recibir datos incorrectos).

## Comunicación punto a punto
Primero, veremos como realizar envíos y recepciones de un proceso a otro.
###### Modos de comunicación
- El **envío** se puede realizar de forma estándar, síncrona o tamponada.
	-> *Síncrona*: el emisor espera a que el receptor llegue a su punto de recepción (a la línea del código) para empezar a realizar el paso de mensajes, bloqueando al emisor hasta dicho momento.
	->*Tamponada*: cuando el emisor llega a la línea de envío, se le debe definir un buffer temporal en el que este dejará los datos y continuará su ejecución independientemente del receptor.
	->*Estándar*: trata de realizar un envío tamponado (más eficiente), pero si el paquete supera cierto tamaño, ejecuta el paso de mensajes en modo síncrono (uso de buffer no rentable).
	
- La **recepción**: es siempre síncrona.

Al momento de pasar los datos, el emisor y el receptor se pueden comportar de dos formas:

- **Bloqueante**: no pueden avanzar hasta que se haya realizado la copia/recepción total de los datos.
- **No bloqueante**: emisor y receptor avanzan tan pronto como sea posible, delegando la copia/recepción a segundo plano.

## Comunicación colectiva
Un modo más avanzado de comunicación dentro de un comunicador de procesos, es el envío/recepción múltiples. Estas comunicaciones afectan a *todos los procesos.*

> Importante: estas llamadas son ejecutadas por todos los procesos -> No dentro de un if
###### Difusión (Broadcast):
Un proceso envía un dato a todos los procesos del comunicador, por lo que se debe especificar el proceso de salida de datos.

``` Python
data = np.vector(1, 2)
comm.Bcast(data, root=0)
```

###### Reducción (Reduce)
Todos los procesos envían un dato que es combinado en uno, el especificado como *root*:

``` Python
data = np.vector(1, dtype=np.int32)
buffer = np.empty(0, dtype=np.int32)

comm,Reduce(data, buffer, op=MPI.SUM, root=0)
```

###### Dispersión (Scatter)
Un proceso comparte un dato, que es repartido por los demás equitativamente.

``` Python
data = np.arange(100, 110, dtype=np.int32)
buffer_recv = np.empty(10, dtype=np.int32)

comm.Scatter(data, buffer, root=0)
```

###### Reunión (Gather)
Opuesto a scatter, todos los procesos dan al proceso raíz un dato, el cual une.

``` Python
data = np.arange(100, 110, dtype=np.int32)
buffer_recv = np.empty(10, dtype=np.int32)
comm.Gather(data, buffer, root=0)
```

## Datos complejos en MPI
De entre un array gigantesco, podemos solo querer tomar de el ciertas partes concretas, contiguas o no contiguas, por esto es que existen los datos complejos, una especificación para recibir solo un patrón concreto de datos, creando formas como estas: 

![[Pasted image 20250528194955.png]]

###### Datos contiguos
Para coger una serie de datos seguidos en cuanto a índices.

``` Python

patron = MPI.INT64_T.Create_contiguous(count=6)

#Declarariamos la matriz, el buffer, etc...
comm.Send([data, (x, y), patron], dest=0, tag=500)

comm.Recv([buffer, (x,y), patron], tag=500)
```

###### Datos vectoriales
Para crear patrones complejos

``` Python

patron = MPI.INT64_T.Create_vector(count=3, blocklenght=2, stride=1)

#Declarariamos la matriz, el buffer, etc...
comm.Send([data, (x, y), patron], dest=0, tag=500)

comm.Recv([buffer, (x,y), patron], tag=500)
```

###### Datos indexados
Para especificar los índices y el número de datos exactos.

``` Python

patron = MPI.INT64_T.Create_indexed([1, 2, 3], [0, 7, 10])

#Declarariamos la matriz, el buffer, etc...
comm.Send([data, (x, y), patron], dest=0, tag=500)

comm.Recv([buffer, (x,y), patron], tag=500)
```