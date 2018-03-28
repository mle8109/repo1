import csv
with open('iris.data', 'rt') as csvfile:
	lines = csv.reader(csvfile)
	print (lines)
	for row in lines:
		print (row)
