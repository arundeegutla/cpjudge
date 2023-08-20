# Arup Guha
# 9/1/2018
# Solution to 2018 UCF Locals problem: Circle Meets Square

# Constants.
NOTOUCH = 0
TOUCH = 1
OVERLAP = 2

def main():

    # Get input.
    strtoks = input("").split()
    x = int(strtoks[0])
    y = int(strtoks[1])
    r = int(strtoks[2])
    strtoks = input("").split()
    sqX = int(strtoks[0])
    sqY = int(strtoks[1])
    sqS = int(strtoks[2])

    # Solve and output.
    print(solve(x,y,r,sqX,sqY,sqS))

def solve(x,y,r,sqX,sqY,sqS):
    
    # Get rid of the big bounding box case.
    if x < sqX-r or x > sqX+sqS+r or y < sqY-r or y > sqY+sqS+r:
        return NOTOUCH

    # Case where center is within y bounds of the square.
    # Only place to touch is at the left or right 
    if y >= sqY and y <= sqY+sqS:
        if x == sqX-r or x == sqX+sqS+r:
            return TOUCH
        return OVERLAP

    # Corresponding case within x bounds.
    if x >= sqX and x <= sqX+sqS:
        if y == sqY-r or y == sqY+sqS+r:
            return TOUCH
        return OVERLAP

    # Get the minimum distance square from center to any of the four corners.
    centerToCornerSq = distSq(x, y, sqX, sqY);
    centerToCornerSq = min(centerToCornerSq, distSq(x, y, sqX+sqS, sqY));
    centerToCornerSq = min(centerToCornerSq, distSq(x, y, sqX, sqY+sqS));
    centerToCornerSq = min(centerToCornerSq, distSq(x, y, sqX+sqS, sqY+sqS));

    # Here are out three cases based on this distance.
    if centerToCornerSq > r*r:
        return NOTOUCH
    if centerToCornerSq == r*r:
        return TOUCH
    return OVERLAP

# Returns the distance squared between (x1,y1) and (x2, y2)
def distSq(x1,y1,x2,y2):
    return (x2-x1)*(x2-x1) + (y2-y1)*(y2-y1)

# Run it!
main()
    


