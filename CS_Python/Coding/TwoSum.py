def twoSum(list_num, target):
    arr = {}
    for idx, item in enumerate(list_num):
        complement = target - item
        if (complement in arr):
            return 

