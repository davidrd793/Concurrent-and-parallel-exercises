Veremos distintas estructuras de datos que podemos crear en Java para almacenar nuestros datos, haciendo incapié en como crear las estructuras enlazadas a nivel de memoria.

**Nodo**: encapsularemos cada valor de las linkedLists en un objeto para añadir información de control adicional, [como un puntero] al siguente elemento.

**Puntero**: referencia a otro nodo de la linkedList, sirve tanto para identificar los elementos de una misma estructura como para identificar el siguiente elemento de la lista cuando uno dado sea eliminado. 

> El interés de todas estas estructuras (crearlas pero de forma sincronizada), se implementa haciendo que cada una de sus acciones (push(), pop(), ...) tenga un bloque sincronizado para realizar sus acciones sin corromper la estructura.

## LinkedList
- Conjunto de datos que están unidos mediante referencias entre sí, de forma que forma una estructura conjunta pese a poder encontrarse en *espacios de memoria disjuntos*, esta idea es la que permite crear otras estructuras abstractas de utilidad como [stacks] y [queues]. Podemos distinguir dos tipos de linkedLists: 
	- *Simplemente enlazadas*: cada elemento tiene un puntero al siguiente.
	- *Doblemente enlazadas:* cada elemento tiene un puntero al siguiente y al anterior elemento.

-> Este tipo de enlaces presenta serias limitaciones, principalmente que deben ser recorridas secuencialmente para encontrar un elemento.

## Stack (Pila)
Funciona siguiendo el *patrón LIFO*, almacenando en **nodo** su valor así como una referencia al último objeto de la lista antes de añadir el actual.

![[Pasted image 20250526160858.png]]

-> La *pila interna* de un stack, almacena `top = null` siendo null al iniciarlo y pasando a referenciar al último nodo del stack en cada momento para retirarlo.

## Queue (Cola)
Funciona siguiendo el *patrón FIFO*, almacenando en **nodo** su valor así como una referencia al siguiente elemento de la lista (inicializado a null y cambiado cuando hay un *nodo siguiente* en la lista).

![[Pasted image 20250526161510.png]]
