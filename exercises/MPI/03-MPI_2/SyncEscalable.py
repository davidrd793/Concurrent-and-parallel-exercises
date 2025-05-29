#!/usr/bin/env python3

from mpi4py import MPI
import numpy as np

com = MPI.COMM_WORLD
size = com.size
rank = com.rank

if size != 2:
    if rank == 0:
        print("ERROR: Lanzar programa con 2 procesos")
    quit()

if rank == 1:
    a = np.zeros(9, dtype = np.int16)
    print("Proceso 1 comparte", a)
    window = MPI.Win.Create(a, comm=com)
elif rank == 0:
    window = MPI.Win.Create(None, comm=com)

if rank == 1:
    g1 = com.group.Incl([0])
    window.Post(group=g1)
    window.Wait()
elif rank== 0:
    g2 = com.group.Incl([1])
    b = np.full(9,1,dtype=np.int16)
    print("Proceso 0 escribe ", b)
    window.Start(group=g2)
    window.Put(b, target_rank=1)
    window.Complete()

if rank == 1:
    print("Proceso 1 resultado ", a)
window.Free()