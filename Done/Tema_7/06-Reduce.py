#!/usr/bin/env python3

from mpi4py import MPI
import numpy as np

comm = MPI.COMM_WORLD
my_rank = comm.rank
num_processes = comm.size

num = np.zeros(2)
num[0] = my_rank+1 #IMPORTANTE: En una operación Reduce() tambien participa el proceso root con su valor de la variable
num[1] = my_rank+2

if my_rank == 0:
    buffer = np.zeros(2)
else:
    buffer = None

comm.Reduce(num, buffer, op=MPI.PROD, root=0)

if my_rank == 0:
    print("Proceso %d/%d: fact(%d)=%d, %d" % (
        my_rank, num_processes, num_processes ,buffer[0], buffer[1]))