def selection_sort(array):
    for i in range(len(array) - 1, 0, -1):
        largest = array[i]
        for j in range(0, i):
            if (array[j] > largest):
               largest = array[j]
               swap(array, i, j)
               print("swapped: i = %s j = %s" %(i,j),array)
    return array

def selection_sort2(array):
    for i in range(0, len(array) - 1):
        smallest = array[i]
        for j in range(i+1, len(array)):
            if (array[j] < smallest):
               smallest = array[j]
               swap(array, i, j)
               print("swapped: i = %s j = %s" %(i,j),array)
    return array

def swap(array, i, j):
    tmp = array[i]
    array[i] = array[j]
    array[j] = tmp

unsorted = [2,4,1,5,9,7,3]
print("Before: ", unsorted)
sorted = selection_sort(unsorted)
print("After: ", sorted)
