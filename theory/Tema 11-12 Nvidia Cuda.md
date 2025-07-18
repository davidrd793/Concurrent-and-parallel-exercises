
## GPU
GPU -> Graphical Proccesing Units.

Dispositivos con numerosos núcleos y alta capacidad de paralelismo y ancho de banda, usadas para procesamiento matemático.
#### Partes de la GPU
GPU -> Múltiples SMs | Memoria Dedicada

**SM**(Streaming Multiprocessor): Subunidad de procesamiento, la ejecución los bloques de hilos de un kernel  son asignada a un SM.
	- *Cuda Core* (Streaming Processors): unidades de procesamiento lógico y aritmético.
	- *Shared Memory*: memoria a la que tienen acceso todos los hilos del SM. Similar a L1. Cada bloque tiene una porción disjunta de esta memoria.
	- *Registros*: memoria local de cada thread (132 Kb).

**Memoria Dedicada**:
	- *Memoria Global*:  memoria principal accesible para todos los hilos y *para la CPU*. Es la memoria más grande (48-64GB) y más lenta.
	- *Memoria Local*: asignada dinámicamente a cada thread cuando su registro se desborda, menor eficiencia que registros.
	- *Memoria constante*: solo lectura, para almacenamiento de constantes.

#### Flujo de datos con GPU
1. Los datos se copian de CPU a GPU (implica la reserva de espacio en memoria para los datos del kernel).
2. La CPU ordena el procesamiento a la GPU.
3. La GPU realiza los cálculos.
4. Los datos son copiados nuevamente a la CPU.

## Nvidia Cuda
Plataforma de computación paralela que permite la designación de piezas de código (kernels) para su procesamiento en GPU, diseñada para C/C++. Consta de:
- *Driver* para el uso de la GPU.
- *Toolkit* que permite el desarrollo para compilar en CUDA.

Para el uso con Python utilizamos librerías como **Numba** (compila un subconjunto de python a código CUDA JIT) que permiten hacer de `puente`.

#### Modelo de ejecución en Cuda
Al lanzar un kernel, la GPU se organiza en 3 capas (lógicas):

- **Grids**: un kernel constituye un grid, el cual contiene bloques y estos contienen threads (ambos especificados por el programador).
- **Bloques**: los bloques de threads se distribuyen en los SM físicos, estos pueden albergar varios bloques en función de sus capacidades.
- **Threads**: unidad lógica ejecutada en un Cuda core, cada thread ejecuta el código del kernel.
- **Warps**: agrupaciones de 32 threads pertenecientes a un bloque, los threads de un warp se ejecutan en paralelo y sus cambios son *gestionados por Cuda de forma automática e inapreciable*. 
- **Half-Warps**: 16 threads que acceden a memoria de forma conjunta.

Esta distribución ajusta las demandas del kernel a los menores SMs posibles y maximizando la concurrencia. Cada bloque actúa de forma concurrente o serial con los demás, pero dentro de él los hilos pueden actuar de forma paralela o concurrente.

Nota: estas unidades lógicas pueden organizarse unidimensionalmente, bidimensionalmente, etc... De esta forma, se pueden adaptar mejor a los datos del cálculo. 

#### Consideraciones del kernel
Sabiendo esta adaptación de la capa lógica a la capa física, a la hora del lanzamiento se trata de ajustar los tamaños para mayor eficiencia, como lanzar los bloques con un múltiplo de 32 (adaptación a warps) o lanzar un número de bloques que se ajuste a los SMs.

#### Arquitectura Nvidia Hopper
Arquitectura de última generación basada en el uso de Tensor Cores, unidades capaces de procesar matrices enteras en un solo ciclo de reloj.
Mejoran el cálculo intensivo, vitales para Deep Learning y Redes Neuronales.

#### Coalescencia
Los half-warps poseen una característica muy importante a tener en cuenta, cuando todos los hilos de un half-warps acceden a posiciones seguidas de la memoria en memoria global, esta se realiza mediante un solo acceso. Con esto, tenemos que, realizar nuestros programas para aprovechar la coalescencia puede mejorar significativamente las prestaciones, solo mediante el control de los idx de cada thread:

![[Pasted image 20250601105233.png]]
En la imagen podemos ver como el primer ejemplo supone un *acceso divergente* de los hilos de un mismo half-warp y por tanto no coalescente (hilo con idx-1 no accede a una posición en memoria seguida de la posición de memoria del dato que accede el thread con idx-2).
El segundo ejemplo produce que todos los threads de un mismo warp sigan el mismo flujo de programa y, por tanto, todos los threads de un half-warp toman la misma dirección accediendo a posiciones seguidas de memoria.

> Una técnica utilizada es el apoyo en memoria compartida para realizar transacciones no-coalescentes, esto debido a que realizar la copia a memoria compartida de las operaciones de forma no-coalescente en dicha memoria resulta mucho menos costoso en tiempo que la no coalescencia en memoria global.

#### Acceso a bancos de memoria
Dentro de la *shared memory*, la información se encuentra organizada en bancos (una memoria compartida se divide típicamente en 32 bancos)


#### Definiciones:
- **kernel**: pieza de código que será procesada por la GPU (en Numba, las que tienen el decorador oportuno).
- **GPGPU**: General Purpose GPU, uso de la potencia computacional de la GPU para fines distintos al renderizado de gráficos.


