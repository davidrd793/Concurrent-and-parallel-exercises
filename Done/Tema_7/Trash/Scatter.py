#!/usr/bin/env python3

from mpi4py import MPI
import numpy as np

comm = MPI.COMM_WORLD
rank = comm.rank
num_processes = comm.size

# Scatter: el proceso raíz contiene un dato y lo dispersa entre todos los procesos objetivo, incluyendose a el mismo
if (rank == 0):
    data = np.arange(12, dtype=np.int32)
else:
    data = None

#Buffer de cada proceso para recibir su parte
buffer = np.zeros(int(12/num_processes), dtype=np.int32)

comm.Scatter(data, buffer, root=0)

print("Soy el proceso %d y he recibido: %s" % (rank, buffer))

