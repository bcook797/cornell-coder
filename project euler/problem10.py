""""
The sum of the primes below 10 is 2 + 3 + 5 + 7 = 17.

Find the sum of all the primes below two million.

"""

def isPrime(num):
	if (num%2 == 0):
		return 1
	
	i = 3
	while(i*i <= num):
		if(num%i == 0):
			return -1
		i = i + 2
	
	return 1

sum = 0
r = 2
while r < 2000000:
	if isPrime(r) == 1:
		sum = sum + r
		print r
	r = r + 1

print sum 