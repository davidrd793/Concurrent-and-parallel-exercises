## Comunicadores
Como ya definimos, un comunicador es una agrupación de procesos que permite su comunicación entre sí, pero podemos definir dos clases de comunicadores entre procesos:

###### Intracomunicadores
Como el comunicador global ya visto, permite comunicación entre los procesos que lo forman, aparte del comunicador global, podemos definir nuevos subcomunicadores para crear relaciones más específicas y agrupar procesos.

``` Python
comm = MPI.COMM_WORLD

group = comm.Get_group() #Obtenemos el grupo actual en una variable
ranks = [2, 3, 4] #Integrantes del nuevo subcomunicador
subGroup = group.Incl(ranks)
commSub = com.Create(subGroup)
```

- Existen otras mútiples funciones para la creación de grupos basadas en conjuntos (unión de dos grupos, intersección de dos grupos ...)

- Otra forma importante para creación de subgrupos es **.Split()**, que divide en n grupos igual de grandes:

``` Python
comm = MPI.COMM_WORLD

commSub = comm.Split(color=rank/3) 
#Asocia cada proceso a un subcomunicador en base al resultado de la 
# operación (9 procesos = [0, 1, 2], [3, 4, 5], [6, 7, 8])
```

###### Intercomunicadores
Permiten interacciones para dos conjuntos (comunicadores), habitualmente con el que tenga el mismo rango del otro comunicador.

``` Python
comm = MPI.COMM_WORLD
commSub = comm.Split(rank/3)

commInt = MPI.Intracomm.Create_intercomm(commSub, remote_leader)

# Uff lo veo chungo men
```

## Topologías
Son formas de distribuir nuestro conjunto de procesos, permitiendonos crear flujos de datos específicos para mejorar la capacidad de nuestros programas.
#### Topología en malla
Distribuir los procesos en forma de malla, definiendo un orden de comunicación (horizontal o vertical) para que cada proceso reciba del anterior y se comunique con el siguiente.

``` Python
comm = MPI.COMM_WORLD

comCartesiano = comm.Create_cart((SIZE_X, SIZE_Y), (n, m))

#Definimos la disposición n-dimensional
#

```
#### Topología en grafo
Definimos explícitamente las relaciones entre procesos en forma de grafo:

![[Pasted image 20250529212856.png]]

![[Pasted image 20250529212836.png]]

- Que dios te ayude entendiendo como funciona esto.

## Análisis de prestaciones
Un aspecto fundamental de todo esto, es ver si realmente es rentable en comparación con la ejecución del mismo programa en paralelo, o cuantos procesos (procesadores) distintos deberíamos utilizar para llegar al punto óptimo de eficiencia. Para realizar una evaluación, vamos a rescatar los conceptos de *aceleración* y *eficiencia* del tema 1.

- **Ley de Ahmdal**: nos indica que dada la fracción paralelizable del programa, hay un *pico de la eficiencia* que nos brinda cada procesador, y dada la sobrecarga que implican los procesadores y sus comunicaciones, hay un *pico en la aceleración* posible que podemos lograr. 

![[Pasted image 20250529213635.png]]


## MPI-2
Desde su lanzamiento, MPI ha contado con múltiples actualizaciones que han llegado hasta MPI-5 en la actualidad, cada una de ellas ofreciendo nuevas mejoras en cuanto a las posibilidades de comunicación de procesos, nos centraremos en este punto en las mejoras que ofrece MPI-2 sobre MPI original.

#### Comunicación monolateral
Conocido como acceso a memoria remota (*RMA*), consiste en la definición de una 'ventana' en la memoria de un proceso, un espacio en el que otro proceso puede tomar o añadir datos.

###### Con objeto activo
El objeto que posee la ventana participa en las acciones.

``` Python
comm = MPI.COMM_WORLD
data = np.array([1], dtype=np.int32)

win = MPI.Win.create(data, comm=comm)

recv_data = np.empty(1, dtype=np.int32)

#Acción entre apertura y cierre de ventana 
win.Fence()
win.Get(recv_data, target_rank=0)
win.Fence() #Sincroniza los hilos para salir de la lectura

win.Free()
```

- También tenemos los métodos *.Put()* y *.Accumulate()*.

###### Con objeto pasivo
El objetivo no participa, la acción **no es colectiva** sino que la realiza un solo proceso:

``` Python
comm = MPI.COMM_WORLD
data = np.array([0], dtype=np.int32)

win = MPI.Win.Create(data, comm=comm)

recv_data = np.empty(1, dtype=np.int32)
win.Lock(0)
win.Get(recv_data, target_rank=0)
win.Unlock(0)

win.Free()
```

###### Sincronización escalable
Se especifica explícitamente el número de procesos (y cuales) que participa.

``` Python