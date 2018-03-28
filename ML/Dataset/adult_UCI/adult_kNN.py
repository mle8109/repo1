from sys import argv
import numpy as np


def loadData(filename):
    #script, filename = argv 
    with open(filename) as f:
        lines = f.readlines()

    print lines

def loadDataset(training_data, test_data):
    training = np.loadtxt(training_data, dtype = 'str', delimiter = ', ', skiprows = 0)
    print training.shape
    print training.item((0, 1))
    print training.item((2, 14))
    for (row in training)
        print row[0]

def main():
    print "Hello"
    loadDataset('adult.data', 'adult.test')
    
main()

