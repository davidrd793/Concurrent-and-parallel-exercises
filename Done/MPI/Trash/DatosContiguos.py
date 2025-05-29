#!/usr/bin/env python3

from mpi4py import MPI
import numpy as np

comm = MPI.COMM_WORLD
rank = comm.rank
num_processes = comm.size

# Los datos contiguos requieren una dirección de inicio y la cantidad de datos a tomar.

if (num_processes != 2):
    print("tonto")
    exit(1)

start_index = 1 * 10 +2
#IMPORTANTE: Crear el patrón y commitearlo
pattern = MPI.INT32_T.Create_contiguous(count=6)
pattern.Commit()


if (rank == 1):
    total_data = np.arange(40, dtype=np.int32).reshape(4, 10)
    
    #Debemoss modificar el envío
    comm.Send([total_data, start_index, pattern], dest=0, tag=500)
else:
    buffer = np.empty(40, dtype=np.int32).reshape(4, 10)
    comm.Recv([buffer, start_index, pattern], source=1, tag=500)
    print("He recibido: ", buffer)