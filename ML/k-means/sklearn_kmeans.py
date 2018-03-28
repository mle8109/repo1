import numpy as np
import matplotlib.pyplot as plt
from scipy.spatial.distance import cdist
from sklearn.cluster import KMeans
from sklearn.decomposition import PCA


#N = 500
N = 1000

X = np.loadtxt("data1.txt", dtype = (float, float), delimiter='\t', usecols=(4,5))
print (X.shape)
#print(X[:,0])
plt.hist(X[:,1])
#plt.show()

kmeans_model = KMeans(n_clusters=3, random_state=1)
kmeans_model.fit(X)

labels = kmeans_model.labels_
#print(labels)

pca_2 = PCA(2)
plot_columns = pca_2.fit_transform(X)
plt.scatter(x=plot_columns[:,0], y=plot_columns[:,1], c=labels)
plt.show()
