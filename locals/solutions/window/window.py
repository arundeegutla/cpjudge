# Arup Guha
# 9/1/2018
# Solution to 2018 UCF Locals Problem: Window on the Wall

# Get the input.
data = input("").split()
width = int(data[0])
height = int(data[1])
gap = int(data[2])

# If either dimension is too small, area is 0.
if 2*gap >= width or 2*gap >= height:
    print(0)

# Otherwise, make room for the gap on both sides!
else:
    print((width-2*gap)*(height-2*gap))
