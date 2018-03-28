import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
import matplotlib.pyplot as plt
#matplotlib inline

data_train = pd.read_csv('train.csv')
data_test = pd.read_csv('test.csv')

#print (data_train.describe())
#print (data_train[['Pclass', 'Survived']].groupby(['Pclass'], as_index=False).mean())
#print(pd.crosstab(data_train['Pclass'], data_train['Survived']))

#print(data_test.describe())
#sns.barplot(x="Embarked", y="Survived", hue="Sex", data=data_train);
#plt.show();

print(data_train.head(5))
