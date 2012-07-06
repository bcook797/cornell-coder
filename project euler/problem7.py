"""
By listing the first six prime numbers: 2, 3, 5, 7, 11, and 13, we can see that the 6th prime is 13.

What is the 10 001st prime number?
"""

def isPrime(num):
	if (num%2 == 0):
		return -1
	
	i = 3
	while(i*i <= num):
		if(num%i == 0):
			return -1
		i = i + 2
	
	return 1

n = 0
m = 1
while n < 10001:
	if isPrime(m) == 1:
		n = n + 1
		print m
	m = m + 1 