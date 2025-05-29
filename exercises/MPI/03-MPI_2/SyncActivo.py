#!/usr/bin/env python3

from mpi4py import MPI
import numpy as np

comm = MPI.COMM_WORLD
rank = comm.rank
size = comm.size

if rank == 0:
    expo = 3
    cad = "Vamos a calcular: "
    for i in range(1, size+1):
        cad = cad + "(%d^%d)" % (i, expo)
        if i!=size:
            cad = cad +"+"
    print(cad)

if rank == 0:
    expo_mem = np.array([expo])
    result_mem = np.array([0])
else:
    expo_mem = None
    result_mem = None

expo_win = MPI.Win.Create(expo_mem, comm=comm)
result_win = MPI.Win.Create(result_mem, comm=comm)

expo_leido = np.array([0])

expo_win.Fence()
expo_win.Get(expo_leido, target_rank=0)
expo_win.Fence()

miNum = pow((rank+1), expo_leido[0])
print("Proceso %d/%d: exponente = %d - numero = %d" % (rank, size, expo_leido[0], miNum))

result_win.Fence()
result_win.Accumulate(miNum, target_rank=0, op=MPI.SUM)
result_win.Fence()

if rank == 0:
    print("El resultado es: ", result_mem[0])

expo_win.Free()
result_win.Free()