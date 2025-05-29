#!/usr/bin/env python
from mpi4py import MPI
import numpy as np

NFilas = 4
NCols = 4
comm = MPI.COMM_WORLD
numProcs = comm.size
miRango = comm.rank

if numProcs != (NFilas*NCols):
    if miRango == 0:
        print("** ERROR: lanzar el programa con %d procesos **" % (NFilas*NCols))
    quit()

commCartesiano = comm.Create_cart((NFilas, NCols))

if commCartesiano.Get_topology() != MPI.CART:
    if miRango==0:
        print("** Error creando topología cartesiana **")
    quit()

miNum = np.random.randint(low=0, high=10, size=1, dtype=np.int32)

def mostrarDatos() :
    matriz = np.zeros(NFilas*NCols, dtype = np.int32).reshape(NFilas, NCols)
    commCartesiano.Gather(miNum, matriz, root = 0)
    if miRango == 0:
        print(matriz)

if miRango==0:
    print("Datos iniciales:")
mostrarDatos()

col1=np.empty(1, dtype=int)
fil1=np.empty(1, dtype = int)
col2=np.empty(1, dtype = int)
fil2=np.empty(1, dtype = int)
if miRango==0:
    print("Vamos a intercambiar los datos de dos procesos, dame sus coordenadas")
    fil1[0] = int(input("Fila elemento 1: "))
    col1[0] = int(input("Columna elemento 1: "))
    fil2[0] = int(input("Fila elemento 2: "))
    col2[0] = int(input("Columna elemento 2: "))

commCartesiano.Bcast(col1, root=0)
commCartesiano.Bcast(fil1, root=0)
commCartesiano.Bcast(fil2, root=0)
commCartesiano.Bcast(col2, root=0)

proc1 = commCartesiano.Get_cart_rank([col1[0],fil1[0]])
proc2 = commCartesiano.Get_cart_rank([col2[0],fil2[0]])
if miRango == 0:
    print("Intercambiando datos entre procesos con rangos %d, %d" % (proc1, proc2))

#dejamos aux como variable vacia para mandar el numero de uno a otro pudiendo sobreescribirlos
aux = np.empty(1,dtype = np.int32)

if miRango == proc1:
    # Enviar mi valor a proc2
    commCartesiano.Send([miNum, MPI.INT], dest=proc2)
    # Recibir el valor de proc2
    commCartesiano.Recv([aux, MPI.INT], source=proc2)

elif miRango == proc2:
    # Enviar mi valor a proc1
    commCartesiano.Send([miNum, MPI.INT], dest=proc1)
    # Recibir el valor de proc1
    commCartesiano.Recv([aux, MPI.INT], source=proc1)



if miRango == proc1 or miRango == proc2:
    miNum = aux

if miRango == 0:
    print("Tras el intercambio: ")
mostrarDatos()