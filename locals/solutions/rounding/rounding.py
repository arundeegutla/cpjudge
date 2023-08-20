# Arup Guha
# 9/1/2018
# Solution to 2018 UCF Locals problem: Rounding Many Ways

def main():

    # Get input and # of 2's and 5's in prime factorization.
    n = int(input(""))
    twos = numdiv(n,2)
    fives = numdiv(n,5)

    # Set up building the list.
    base = 1
    vals = []

    # Loop through all powers of 2.
    for i in range(twos+1):

        cur = base

        # And all powers of 5
        for j in range(fives+1):
            vals.append(cur)
            cur *= 5

        # Go to next power of two.
        base *= 2

    # Sort it!
    vals.sort()

    # Print output.
    print(len(vals))
    for i in range(len(vals)):
        print(vals[i])

# Returns the number of times div evenly divides into n.
def numdiv(n,div):
    res = 0
    while n%div == 0:
        res += 1
        n //= div
    return res

main()
