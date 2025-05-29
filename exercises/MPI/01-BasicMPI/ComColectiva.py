#!/usr/bin/env python3

from mpi4py import MPI
import numpy as np

#Determinar tamaño del bloque
L = 3

comm = MPI.COMM_WORLD
rank = comm.rank
size = comm.size


if rank == 0: #Si es el proceso 0
    A = np.random.randint(low=0, high=10, size=(L, L*size), dtype=np.int32) # Se crea la matriz A
    print("Matriz A:")
    print(A)
    A_t = np.empty(0, dtype=np.int32) #Y se crea un array vacio que se llena después
    for i in range(size): #Tantas veces como cantidad de procesos que hay
        bloque = A[:,i*L:(i+1)*L] #Se recoge el bloque i por columnas
        A_t = np.concatenate([A_t, bloque.ravel()]) #Aplana el bloque a 1 Dimension con Ravel y lo concatena a A_t
else:
    A_t = None #Si no es del proceso 0 es una variable nula

#Se crea la variable vacia bloque y se usa para almacenar la parte difundida de la cadena concatenada del proceso 0
bloque = np.empty(shape=(L,L), dtype=np.int32)
comm.Scatter(A_t, bloque, root=0)

#Debug: Comprobar bloque recibido por cada proceso
print(f"[Proceso {rank}] Bloque recibido>\n{bloque}")

#Hacer sumatorio de los valores de las filas de los bloques en cada proceso (SALEN 3 VALORES DE AQUI)
bloque_sum = np.sum(bloque, axis=1, dtype=np.int32)

#Debug: Comprobar valor resultado en cada proceso
print(f"[Proceso {rank}] Suma de filas: {bloque_sum}")

#El proceso 0 prepara un array en el que guardar el resultado tras el reduce 
if rank ==0: 
    result = np.zeros(shape=(L,1), dtype=np.int32) 
else:
    result = None

#Se realiza la operación colectiva de reducción
comm.Reduce(bloque_sum, result, op=MPI.SUM, root=0)

if rank == 0:
    print("Resultado final del procesamiento")
    print(result)

