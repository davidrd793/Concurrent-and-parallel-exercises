#!/usr/bin/env python3

from mpi4py import MPI
import numpy as np

#Declaraciones básicas
comm = MPI.COMM_WORLD
rank = comm.rank
num_processes = comm.size

data = np.array([rank], dtype=np.int32) #IMPORTANTE: En una operación Reduce() tambien participa el proceso root

if (rank != 0):
    buffer = None
else:
    buffer = np.empty(1, dtype=np.int32)

comm.Reduce(data, buffer, op=MPI.SUM, root=0)

if (rank == 0):
    print("Mis datos tras el reduce son: ", buffer)