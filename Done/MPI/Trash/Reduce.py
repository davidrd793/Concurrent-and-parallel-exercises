#!/usr/bin/env python3

from mpi4py import MPI
import numpy as np

#Declaraciones básicas
comm = MPI.COMM_WORLD
rank = comm.rank
num_processes = comm.size


if (rank == 0):
    data = np.array([1, 2], dtype=np.int32)
else:
    data = np.empty(2, dtype=np.int32)


comm.Bcast(data, root=0)

if (rank != 0):
    print("Soy el proceso %d y he recibido: ", data, rank)