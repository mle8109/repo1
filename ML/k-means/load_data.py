import numpy as np

data = np.loadtxt("data.txt", dtype = (int, int), delimiter='\t', usecols=(4,5))

print ("data = ", data)
