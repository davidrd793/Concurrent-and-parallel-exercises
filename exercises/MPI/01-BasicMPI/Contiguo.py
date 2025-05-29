#!/usr/bin/env python3

from mpi4py import MPI
import numpy as np
from time import sleep

comm = MPI.COMM_WORLD
rank = comm.rank
size = comm.size

height = 6
width = 6

num_datos = 2
pos_datos = 2

row_type = MPI.INT64_T.Create_contiguous(count=6)
row_type.Commit()

if rank == 0:
    matriz = (+np.arange(width*height,dtype=np.int64)).reshape(height,width)
    print("Array on sender")
    print(matriz)
    print()
    comm.Send([matriz, (num_datos,pos_datos), row_type], dest = 1)
if rank ==1:
    sleep(1)
    hueco= np.zeros(width*height, dtype=np.int64).reshape(height,width)
    print("Preparando hueco para recibir el array")
    print(hueco)
    print()
    comm.Recv([hueco, (num_datos, pos_datos), row_type])
    sleep(1)
    print("Array on receiver")
    print(hueco)
