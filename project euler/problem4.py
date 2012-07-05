""" A palindromic number reads the same both ways. The largest palindrome made from the product of two 2-digit numbers is 9009 = 91  99.

Find the largest palindrome made from the product of two 3-digit numbers. """ 

def isPalin(num):
	s = str(num)
	t = s[::-1]
	if s == t:
		return 1
	return -1

palin = []
	
for i in range(999,100, -10):
	for j in range(991,100, -10):
			num = i*j
			print i, j
			if isPalin(num) == 1:
				palin.append(num)	
				print "PALINDROME"

for i in range(993,100, -10):
	for j in range(993,100, -10):
			num = i*j
			print i, j
			if isPalin(num) == 1:
				palin.append(num)	
				print "PALINDROME"
				
for i in range(997,100,-10):
	for j in range(997,100,-10):
			num = i*j
			print i,j
			if isPalin(num) == 1:
				palin.append(num)
				print "PALINDROME"
				
palindrome = palin[0]
for i in range(len(palin)):
	if palin[i] > palindrome:
		palindrome = palin[i]

print palindrome
			