# Arup Guha
# 9/1/2018
# Solution to 2018 UCF Locals Problem: Historical TV Remote Control

def main():
    
    # Store bad digits.
    line = input("").split()
    badlist = []
    for i in range(1,len(line)):
        badlist.append(int(line[i]))

    # This is our target.
    target = int(input(""))

    # Just try each value we could type in the remote
    res = 1000
    for i in range(0, 1000):
        if canDo(i, badlist):
            res = min(res, abs(i-target))

    # Ta da!
    print(res)

# Returns true if we can type val in the remote with bad numbers badlist.
def canDo(val,badlist):

    # Special case.
    if val == 0:
        return not (val in badlist)

    # Peel off each digit. If any is bad, it doesn't work.
    while val > 0:
        digit = val%10
        if digit in badlist:
            return False
        val //= 10

    # If we get here, we are good.
    return True

# Get it started
main()    

