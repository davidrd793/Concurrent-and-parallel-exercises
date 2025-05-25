Este es el lanzamineto básico de un kernel a GPU:

``` python
@cuda.jit #Decorador a la ufunc
def increment_func(an_array):
	# Do some stuff
	
threadsPerBLock = 32
blocksPerGrid = an_array.size()
increment_func[blocksPerGrid, threadsPerBlock](an_array)
```

`threadsPerBlock` determina cuantos threads van a tener una *memoria compartida*. Debe ser suficiente para procesar el tamaño de la entrada, todos los threads ejecutarán el código de la función, se puede/debe controlar el flujo de programa mediante otras técnicas.

## Control de flujo de programa
#### Identificadores de la API

//Pendiente

#### Uso de Strides y grids multidiimensionales

**Stride**: división del trabajo requerido entre los threads. Dado un thread x y n threads, este realizara las tareas x, (n+1)x, (n+2)x... Hasta agotar la entrada.

**Grid multidimensional**: se puede moldear el posicionamiento a nivel de grid o de bloque para ajustarse a la entrada (más eficiente para topologías de datos n-dimensionales).

``` python
@cuda.jit
def function(a, b, out):
	# Uso de strides
	start = cuda.grid(1)
	stride = cuda.gridsize(1) # Obtenemos el tamaño total
	for i in range (start, a.shape[0], stride): #shape[0] = eje x
		out[i] = a[i] + b[i]
	
	# Uso de Grids multidimensionales
	x, y = cuda.grid(2)
	out[x][y] = aþ[x][y] + b[x][y]
```

// Pendiente la parte de la API correspondiente a asignación de memoria ...