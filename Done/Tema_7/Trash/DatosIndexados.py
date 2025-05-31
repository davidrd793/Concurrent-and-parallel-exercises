#!/usr/bin/env python3

from mpi4py import MPI
import numpy as np

comm = MPI.COMM_WORLD
rank = comm.rank
num_processes = comm.size

# Los datos indexados funcionan especificando los índices en que se quiere tomar datos y el número de datos a tomar en cada índice

pattern = MPI.INT32_T.Create_indexed([2, 2, 2], [0, 4, 8]) #IMPORTANTE: orden, [blocklength] [index]
pattern.Commit()

if (rank == 1):
    data = np.arange(36, dtype=np.int32).reshape(6, 6)
    comm.Send([data, (1, None), pattern], dest = 0)
else:
    buffer = np.zeros(36, dtype=np.int32).reshape(6, 6)
    comm.Recv([buffer, (1, None), pattern])
    print(buffer)
