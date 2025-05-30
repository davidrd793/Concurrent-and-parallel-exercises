#!/usr/bin/env python3

from mpi4py import MPI
import numpy as np

comm = MPI.COMM_WORLD
rank = comm.rank
num_processes = comm.size

# Gather: todos los procesos poseen un dato, el cual es "reunido" en el proceso raíz, incluyendo el dato del proceso raíz

data = np.array([1, 2], dtype=np.int32)

if (rank == 0): #Creamos el buffer para el proceso raíz
    buffer = np.empty((num_processes*2), dtype=np.int32)
else:
    buffer = None

comm.Gather(data, buffer, root=0)

if (rank == 0):
    print("El proceso 0 ha recibido los datos reunidos, el resultado es: ", buffer)