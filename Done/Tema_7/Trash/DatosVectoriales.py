#!/usr/bin/env python3

from mpi4py import MPI
import numpy as np

comm = MPI.COMM_WORLD
rank = comm.rank
num_processes = comm.size

# Los datos vectoriales requieren un número de datos a coger, un inicio y un stride de separación entre ellos

pattern = MPI.INT32_T.Create_vector(count=10, blocklength=1, stride=2)
pattern.Commit()

if (rank == 1):
    data = np.arange(36, dtype=np.int32).reshape(6, 6)
    print("Array on sender: ")
    print(data)
    print()
    comm.Send([data, (1, None), pattern], dest=0)
else:
    buffer = np.zeros(36, dtype=np.int32).reshape(6, 6)
    comm.Recv([buffer, (1, None), pattern])
    print(buffer)

