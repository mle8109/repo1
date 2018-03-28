import numpy as np
import pandas as pd

data = pd.read_csv("train.csv")
#print(data.head())

test = pd.read_csv("test.csv")
#print(test.head().T)
null_columns=data.columns[data.isnull().any()]

import matplotlib.pyplot as plt
import seaborn as sns
sns.set(font_scale=1)

pd.options.display.mpl_style = 'default'
labels = []
values = []
for col in null_columns:
    labels.append(col)
    values.append(data[col].isnull().sum())
    ind = np.arange(len(labels))
    width=0.6
    fig, ax = plt.subplots(figsize=(6,5))
    rects = ax.barh(ind, np.array(values), color='purple')
    ax.set_yticks(ind+((width)/2.))
    ax.set_yticklabels(labels, rotation='horizontal')
    ax.set_xlabel("Count of missing values")
    ax.set_ylabel("Column Names")
    ax.set_title("Variables with missing values");
data.hist(bins=10,figsize=(9,7),grid=False);            
