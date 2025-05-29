#!/usr/bin/env python3

from mpi4py import MPI
import numpy as np
from time import sleep

comm = MPI.COMM_WORLD
rank = comm.rank
size = comm.size

height = 6
width = 6

type = MPI.FLOAT.Create_vector(count=6, blocklength=2, stride=6)
type.Commit()

if rank == 0:
    matriz = (10+np.arange(height*width, dtype=np.float32)).reshape(height,width)
    print("Array on sender")
    print(matriz)
    print()
    comm.Send([matriz,1, type], dest= 1)

if rank ==1:
    hueco = np.zeros(height*width, dtype = np.float32).reshape(height,width)
    sleep(1)
    print("Hueco preparado para el array")
    print(hueco)
    print()
    sleep(1)
    comm.Recv([hueco, 1, type])
    print("Array on receiver")
    print(hueco)
    



