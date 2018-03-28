import numpy as np
from sklearn import datasets, linear_model
import matplotlib.pyplot as plt

#Load iris data sets
iris = datasets.load_iris()

#Split training and test datasets
X_train, X_test = iris.data[:135,:1], iris.data[135:,:1]
Y_train, Y_test = iris.target[:135], iris.target[135:]

print "X_train set: ", X_train.shape
print "X_test set: ", X_test.shape
print "Y_train set: ", Y_train.shape
print "Y_test set: ", Y_test.shape

#Train model
lm = linear_model.LinearRegression()
lm.fit(X_train, Y_train)


print "Number of Coeffcient: ", len(lm.coef_)
print "Coeffcient: ", lm.coef_
print "Intercept: ", lm.intercept_

#Plot data
plt.scatter(X_train, Y_train, color='blue')
plt.scatter(X_test, Y_test, color='black')
plt.plot(X_test, lm.predict(X_test), color='green')
plt.show()
