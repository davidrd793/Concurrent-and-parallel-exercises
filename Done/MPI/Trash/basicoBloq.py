#!/usr/bin/env python3

from mpi4py import MPI
import numpy as np

#Declaraciones básicas
comm = MPI.COMM_WORLD
rank = comm.rank
num_processes = comm.size

#Podemos crear un limitador de procesos
if (num_processes != 2):
    if (rank == 0):
        print("Debe ejecutarse el programa con 2 procesos!!")
        exit(0)

if (rank == 1):
    #Creamos un dato
    data = np.arange(rank*100, rank*100+6, dtype=np.int32)
    comm.Send(data, dest=0)

else:
    #Creamos el espacio en memoria en que recibiremos los datos
    buffer = np.empty(6, dtype=np.int32)
    #Recibimos los datos
    comm.Recv(buffer, source=1)
    print("He recibido: ", buffer)