""" The prime factors of 13195 are 5, 7, 13 and 29.

What is the largest prime factor of the number 600851475143 ? """

def isPrime(num):
	if (num%2 == 0):
		return -1
	
	i = 3
	while(i*i <= num):
		if(num%i == 0):
			return -1
		i = i + 2
	
	return 1


comp = 600851475143

j = 2
while(j*j <= comp):
	if(isPrime(j) == 1 and comp%j == 0):
		print j
	j = j + 1

