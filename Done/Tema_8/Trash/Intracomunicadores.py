#!/usr/bin/env python3

from mpi4py import MPI
import numpy as np

num_data = 4

comm = MPI.COMM_WORLD
rank = comm.rank
num_proc = comm.size

if (num_proc != 9):
    print("Deben lanzarse 9 procesos")
    exit(0)

# Vamos a crear un intracomunicador dentro del comunicador global que comunique los procesos 0, 1, 2, 3 y realiza un reeduce sobre 0

data = np.array([rank, rank+10], dtype=np.int32)

# Subcomunicador
group = comm.Get_group()
new_comm_ranks = [0, 1, 2, 3]
newGroup = group.Incl(new_comm_ranks)
sub_comm = comm.Create(newGroup)

#Hacemos el reduce a 0
if (sub_comm is not None):
    data = 0 * data

print(f"Soy el proceso {rank} y tengo el dato: {data}")