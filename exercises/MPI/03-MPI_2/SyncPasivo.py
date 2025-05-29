#!/usr/bin/env python3
from mpi4py import MPI
import numpy as np
import time

comm = MPI.COMM_WORLD
rank = comm.rank
size = comm.size

if size != 3:
    if rank == 0:
        print("ERROR: Lanzar programa con 3 procesos")
    quit()

if rank == 0:
    a = np.arange(9, dtype= np.int16)
    print("Proceso 0 comparte", a)
    window = MPI.Win.Create(a, comm=comm)
else:
    window = MPI.Win.Create(None, comm=comm)

if rank == 1:
    b = (10 + np.arange(9, dtype = np.int16))
    window.Lock(0)
    time.sleep(3)
    window.Put(b, target_rank=0)
    window.Unlock(0)

window.Free()
comm.Barrier()

if rank == 0:
    print("Proceso 0 resultado: ", a)

