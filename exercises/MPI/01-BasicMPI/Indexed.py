#!/usr/bin/env python3

from mpi4py import MPI
import numpy as np
from time import sleep

comm = MPI.COMM_WORLD
size = comm.size
rank = comm.rank

height = 6
width = 6
num_data = 1

type = MPI.DOUBLE.Create_indexed([2,2,6,6,2,2], [2,8,12,18,26,32])
type.Commit()

if rank == 0:
    matriz = (10+np.arange(width*height, dtype=np.float64)).reshape(height,width)
    print("Array on sender")
    print(matriz)
    print()
    comm.Send([matriz, num_data, type], dest=1)

if rank == 1:
    hueco = np.zeros(width*height, dtype=np.float64).reshape(height,width)
    sleep(1)
    print("Hueco preparado para recibir el array")
    print(hueco)
    print()
    sleep(1)
    comm.Recv([hueco, 1, type])
    print("Array on receiver")
    print(hueco)
    print()

