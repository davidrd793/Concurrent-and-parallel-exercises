#!/usr/bin/env python3

from mpi4py import MPI
import numpy as np
TAMBUFFER = 4

com = MPI.COMM_WORLD
size = com.Get_size()
rank = com.Get_rank()

if size !=3:
    if rank == 0:
        print("ERROR: Lanzar programa con 3 procesos")
    quit()

data = np.array([rank*TAMBUFFER+i for i in range(TAMBUFFER)], dtype = np.int16)

mpi_file = MPI.File.Open(com, "fichero1.data", MPI.MODE_WRONLY + MPI.MODE_CREATE)
mpi_file.Set_view(rank*data.nbytes, MPI.SHORT)
mpi_file.Write(data)
mpi_file.Close()

print("Proceso %d escribe: " % (rank), data)