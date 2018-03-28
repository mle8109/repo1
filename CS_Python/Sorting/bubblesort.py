def bubble_sort(array):
    for i in range(len(array) - 1, 0, -1):
        for j in range(0, i):
            if (array[j + 1] < array[j]):
               swap(array, j, j+1)
               print("swapped: i = %s j = %s" %(i,j),array)
    return array

def bubble_sort2(array):
    for i in range(0, len(array) - 1):
        for j in range(0, len(array) -1 - i):
            if (array[j + 1] < array[j]):
               swap(array, j, j+1)
               print("swapped: i = %s j = %s" %(i,j),array)
    return array

def swap(array, i, j):
    tmp = array[i]
    array[i] = array[j]
    array[j] = tmp

unsorted = [2,4,1,5,9,7,3]
print("Before: ", unsorted)
sorted = bubble_sort2(unsorted)
print("After: ", sorted)
