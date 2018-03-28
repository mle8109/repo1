def fibonacci(n):
    if n == 0: return 0
    if n == 1: return 1
    return fibonacci(n-1) + fibonacci(n-2)

ret = []
def fibonacci2(n):
	if n == 0: 
		ret.append(0)
		return ret
	if n == 1: 
		ret.append(1)
		return ret
	tmp = fibonacci2(n-1) + fibonacci2(n-2)
	ret.append(tmp)
	return ret
	
cache = []
def fibonacci_ds(n):
	if n == 0:	cache.insert(0,0)
	if n == 1:	cache.insert(1,1)
	if n > 1:	cache.insert(n,fibonacci_ds(n-1) + fibonacci_ds(n-2))
	return cache[n]
	
print (fibonacci(9))
#print (fibonacci_ds(9))
print(fibonacci2(3))