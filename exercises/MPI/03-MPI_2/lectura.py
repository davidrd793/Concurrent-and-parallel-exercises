#!/usr/bin/env python3

import numpy as np
from mpi4py import MPI
TAMBUFFER = 4

com = MPI.COMM_WORLD
size = com.Get_size()
rank = com.Get_rank()

if size != 3:
    if rank == 0:
        print("ERROR: Lanzar el programa con 3 procesos")
    quit()

data = np.zeros(TAMBUFFER, dtype= np.int16)

mpi_file = MPI.File.Open(com, "fichero1.data", MPI.MODE_RDONLY)
mpi_file.Set_view((2-rank)*data.nbytes, MPI.SHORT)
mpi_file.Read(data)
mpi_file.Close()

print(("Proceso %d lee:" %(rank)), data)
