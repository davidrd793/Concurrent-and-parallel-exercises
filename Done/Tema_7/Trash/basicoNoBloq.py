#!/usr/bin/env python3

from mpi4py import MPI
import numpy as np 

comm = MPI.COMM_WORLD
rank = comm.rank
num_processes = comm.size

#Que el envío sea no bloqueante supondrá que el emisor se despegará del envío tan rápido como le sea posible

if (rank != 0):
    data = np.arange(100, 200, 2, dtype=np.int32)
    req = comm.Isend(data, dest=0, tag=500)
    req.wait()
    print("Soy el proceso %d y he salido del envío", rank)
else:
    for i in range (1, num_processes):
        buffer = np.empty(50, dtype=np.int32)
        req = comm.Irecv(buffer, source=i, tag=500)
        req.wait()
        print("He recibido: ", buffer)
    
