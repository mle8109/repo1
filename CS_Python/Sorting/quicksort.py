def quicksort(array):
    less = []
    equal = []
    greater = []
    if (len(array) > 1):
        pivot = array[0]
        for x in array:
            if (x < pivot):
                less.append(x)
            elif (x == pivot):
                equal.append(x)
            else:
                greater.append(x)
        return quicksort(less) + equal + quicksort(greater)
    else:
        return array

def main():
    input = [12,5,3,3,6,7,2,45,2]
    output = quicksort(input)
    print("Result: ", output)

if __name__ == "__main__":main()    
