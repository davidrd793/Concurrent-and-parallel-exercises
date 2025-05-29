#!/usr/bin/env python3
from mpi4py import MPI
import numpy as np

comm = MPI.COMM_WORLD
rank = comm.rank
size = comm.size

grupo = comm.Get_group()
RangosPar = [i for i in range(size) if i%2 == 0]
RangosImpar = [i for i in range(size) if i%2 != 0]

grupoPar = grupo.Incl(RangosPar)
grupoImpar = grupo.Incl(RangosImpar)

comPar = comm.Create(grupoPar)
comImpar = comm.Create(grupoImpar)

numero = np.random.randint(low=0, high=100, dtype=np.int32)

if rank == 0:
    result = np.zeros(1, dtype= np.int32)
elif rank == 1:
    result = np.zeros(1, dtype = np.int32)
else:
    result = None

if rank in RangosPar:
    comPar.Reduce(numero, result, op=MPI.SUM, root=0)

if rank in RangosImpar:
    comImpar.Reduce(numero, result, op=MPI.SUM, root=0)

if rank == 1 or rank == 0:
    print(f"[Proceso {rank}] Resultado de sumatorio de mi grupo: {result}")