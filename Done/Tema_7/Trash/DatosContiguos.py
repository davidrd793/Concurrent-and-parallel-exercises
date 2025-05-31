#!/usr/bin/env python3

from mpi4py import MPI
import numpy as np

comm = MPI.COMM_WORLD
rank = comm.rank
num_processes = comm.size

# Los datos contiguos requieren una dirección de inicio y la cantidad de datos a tomar.

pattern = MPI.INT32_T.Create_contiguous(count = 6)
pattern.Commit()

if rank == 0:
    data = np.arange(36, dtype=np.int32).reshape(6, 6)
    print("Array on sender: ")
    print(data)
    print()
    comm.Send([data, (1, 2), pattern], dest = 1) #IMPORTANTE: (1, 2) indica 1 bloque en la posición 2 (6x2 en este caso)
else:
    buffer = np.zeros(36, dtype=np.int32).reshape(6, 6) #IMPOORTANTE: Usar np.zeros mejor para evitar errores raros
    comm.Recv([buffer, (1, 1), pattern])
    print(buffer)