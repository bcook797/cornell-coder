"""
The sum of the squares of the first ten natural numbers is,

12 + 22 + ... + 102 = 385
The square of the sum of the first ten natural numbers is,

(1 + 2 + ... + 10)2 = 552 = 3025
Hence the difference between the sum of the squares of the first ten natural numbers and the square of the sum is 3025  385 = 2640.

Find the difference between the sum of the squares of the first one hundred natural numbers and the square of the sum.  """

def sumOfSquares(n):
	sum = 0
	i = 0
	print "Sum of Squares"
	while i <= n:
		print i
		sum = sum + (i*i)
		i = i + 1
	
	return sum
	
def squareOfSums(n):
	sum = 0
	i = 0
	print "Square of Sums"
	while i <= n:
		print i
		sum = sum + i
		i = i + 1
	
	square = sum * sum
	
	return square

diff = squareOfSums(100) - sumOfSquares(100)

print diff
		