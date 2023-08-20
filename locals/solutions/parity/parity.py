# Arup Guha
# 9/1/2018
# Solution to 2018 UCF Locals Problem: Parity of Strings

# Get the input.
line = input("")

# Our freq array
freq = [0]*26

# Add up frequencies of letters.
for i in range(len(line)):
    freq[ord(line[i])-ord('a')]+= 1

evenCnt = 0
oddCnt = 0

# Count up the parities of each letter.
for i in range(26):
    if freq[i]>0 and freq[i]%2 == 0:
        evenCnt += 1
    elif freq[i]%2 == 1:
        oddCnt += 1

# Here are our cases.
if evenCnt > 0 and oddCnt == 0:
    print(0)
elif oddCnt > 0 and evenCnt == 0:
    print(1)
else:
    print(2)
