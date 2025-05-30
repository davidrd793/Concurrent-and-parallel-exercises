#!/usr/bin/env python3

from mpi4py import MPI
import numpy as np

num_data = 4

# IMPORTANTE: estas declaraciones son comunes a todos los programas que veremos
comm = MPI.COMM_WORLD
my_rank = comm.rank
num_processes = comm.size

# IMPORTANTE: el flujo de la ejecución de cada proceso es guiado por condicionales aprovechando el rango (identificador único) 
if my_rank != 0:
   data = np.arange(my_rank*100, my_rank*100+num_data, dtype=np.float64) #IMPORTANTE: Declaración y consistencia con los tipos
   comm.Send(data, dest=0, tag=500)
else:
   print("Hola, soy el proceso %d (hay %d procesos) y recibo:" % (my_rank, num_processes))
   for source_rank in range(1,num_processes):
      data = np.empty(num_data)
      comm.Recv(data, source=source_rank, tag=500)
      print("Del proceso %d: " % source_rank, data)