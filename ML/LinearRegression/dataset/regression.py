import numpy as np
from numpy import loadtxt, zeros, ones, array, linspace, logspace
from pylab import scatter, show, title, xlabel, ylabel, plot, contour


#Load the dataset
data = loadtxt('housing.data',dtype=np.float)

#Plot the data
scatter(data[:, 5], data[:, 13], marker='o', c='b')
title('Relationship between RM and price')
xlabel('Avg number of rooms')
ylabel('Housing Price')
show()

X = data[:, 5]
y = data[:, 13]

#number of training samples
m = y.size

#A = X.transpose()

print X.shape
#print A.shape
print "m = ", m

#add a colum of one to X
#it = np.ones(shape=(m,2))
#it[:,1] = X
#assign it to X
#X = it



#Initialize theta parameters
theta = zeros(shape=(2, 1))

#Some gradient descent settings
iterations = 1500
alpha = 0.01


print "X shape =", X.shape
#print X

#data = np.loadtxt('housing.data',dtype=np.str)

#print data[0]
#file = open('housing.data', 'r')
#print file.read()

def basicLR(x, y):
        length = len(x)
        sum_x = sum(x)
        sum_y = sum(y)

        sum_x_squared = sum(map(lambda a: a*a, x))
        sum_of_products = sum([x[i]*y[i] for i in range(length)])

        a = (sum_of_products - (sum_x * sum_y)/length) / (sum_x_squared - ((sum_x ** 2)/length))
        b = (sum_y - a*sum_x)/length
        return a, b

a, b = basicLR(X, y)
print "a= ", a
print "b= ", b
