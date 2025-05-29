sudo apt install python3-venv python3-pip -y

python3 -m venv mpi-env
source mpi-env/bin/activate
pip install mpi4py
