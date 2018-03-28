import numpy as np # linear algebra
import pandas as pd # data processing, CSV file I/O (e.g. pd.read_csv)
import matplotlib.pyplot as plt
from matplotlib import pyplot
from mpl_toolkits.mplot3d import Axes3D
from sklearn.cluster import KMeans
import matplotlib.axes as ax



data = pd.read_csv("new_data3.csv")
data.shape


print (data.head())

#replace negative values
data['sum'][data['sum']<0] =  data['sum'][data['sum']<0]*(-1)
 
#print (data.head())

#for 2D, needs 2 data columns
X = data.iloc[:,2:4].values

#for 3D, needs 3 data columns
#X = data.iloc[:,1:4].values


print(X.shape)
X_1 = data[data.columns[2]]
X_2 = data[data.columns[3]]

print (X_1.min(), X_1.max())
print (X_2.min(), X_2.max())
k = 3
kmeans = KMeans(n_clusters=k)
kmeans.fit(X)

labels = kmeans.labels_
print("labels:", labels)
centroids = kmeans.cluster_centers_
print("centroids:", centroids)

for i in range(k):
    # select only data observations with cluster label == i
    ds = X[np.where(labels==i)]
	#
    # plot the data observations
    pyplot.plot(ds[:,0],ds[:,1],'o')
    # plot the centroids
    lines = pyplot.plot(centroids[i,0],centroids[i,1],'x')
    # make the centroid x's bigger
    # pyplot.setp(lines,ms=15.0)
    # pyplot.setp(lines,mew=2.0)
pyplot.xlim([X_1.min(),X_1.max()])
pyplot.ylim([X_2.min(),X_2.max()])

# fig = pyplot.figure()
# fig.suptitle('bold figure suptitle', fontsize=14, fontweight='bold')

# ax = fig.add_subplot(111)
# fig.subplots_adjust(top=0.85)
# ax.set_title('axes title')

# ax.set_xlabel('xlabel')
# ax.set_ylabel('ylabel')
pyplot.xlabel('num_session')
pyplot.ylabel('total_time')

#pyplot.xrange.ticklabel_format(style='plain')
#ax.Axes.get_yaxis().get_major_formatter().set_useOffset(False)

pyplot.show()


# fig = plt.figure(1, figsize=(4, 3))
# ax = Axes3D(fig, rect=[0, 0, .95, 1], elev=48, azim=134)
# kmeans.fit(X)
# labels = kmeans.labels_

# ax.scatter(X[:, 0], X[:, 1], X[:, 2],
		   # c=labels.astype(np.float), edgecolor='k')

		   		   
# ax.w_xaxis.set_ticklabels([])
# ax.w_yaxis.set_ticklabels([])
# ax.w_zaxis.set_ticklabels([])


# ax.set_xlabel('num_apps')
# ax.set_ylabel('num_session')
# ax.set_zlabel('total_time')
# ax.dist = 12
	
# plt.show()