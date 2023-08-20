// Arup Guha
// 8/9/2018
// Solution to 2018 UCF Locals problem: Circle Meets Square

import java.util.*;

public class circlesquare {
	
	final public static int OVERLAP = 2;
	final public static int TOUCH = 1;
	final public static int NOTOUCH = 0;
	
	public static int x;
	public static int y;
	public static int r;
	public static int sqX;
	public static int sqY;
	public static int sqS;
	
	public static void main(String[] args) {
		
		// Get the descriptions of both shapes.
		Scanner stdin = new Scanner(System.in);
		x = stdin.nextInt();
		y = stdin.nextInt();
		r = stdin.nextInt();
		sqX = stdin.nextInt();
		sqY = stdin.nextInt();
		sqS = stdin.nextInt();
		
		// Ta da!
		System.out.println(solve());
	}
	
	public static int solve() {
		
		// Get rid of the big bounding box case.
		if (x < sqX-r || x > sqX+sqS+r || y < sqY-r || y > sqY+sqS+r) return NOTOUCH;
		
		// Case where center is within y bounds of the square.
		// Only place to touch is at the left or right ends of the rectangle, otherwise it's an overlap.
		if (y >= sqY && y <= sqY+sqS) {
			if (x == sqX-r || x == sqX+sqS+r) return TOUCH;
			return OVERLAP;
		}
		
		// Corresponding case within x bounds.
		if (x >= sqX && x <= sqX+sqS) {
			if (y == sqY-r || y == sqY+sqS+r) return TOUCH;
			return OVERLAP;
		}
		
		// Get the minimum distance square from center to any of the four corners.
		int centerToCornerSq = distSq(x, y, sqX, sqY);
		centerToCornerSq = Math.min(centerToCornerSq, distSq(x, y, sqX+sqS, sqY));
		centerToCornerSq = Math.min(centerToCornerSq, distSq(x, y, sqX, sqY+sqS));
		centerToCornerSq = Math.min(centerToCornerSq, distSq(x, y, sqX+sqS, sqY+sqS));
		
		// Here are our three cases based on this distance.
		if (centerToCornerSq > r*r) return NOTOUCH;
		if (centerToCornerSq == r*r) return TOUCH;
		return OVERLAP;
	}
	
	// Returns the distance squared between pts (x1, y1) and (x2, y2).
	public static int distSq(int x1, int y1, int x2, int y2) {
		return (x2-x1)*(x2-x1) + (y2-y1)*(y2-y1);
	}
}