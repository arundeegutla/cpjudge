import java.util.*;

public class circlesquareTimothy {
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		long circX = in.nextLong();
		long circY = in.nextLong();
		long rad = in.nextLong();
		long squareX = in.nextLong();
		long squareY = in.nextLong();
		long side = in.nextLong();

		if (overlap(circX, circY, rad, squareX, squareY, side)) {
			System.out.print("2\n");
		} else if (touch(circX, circY, rad, squareX, squareY, side)) {
			System.out.print("1\n");
		} else {
			System.out.print("0\n");
		}
	}

	static boolean pointInCircle(long circX, long circY, long rad, long x, long y) {
		long dx = x - circX;
		long dy = y - circY;
		return dx * dx + dy * dy < rad * rad;
	}

	static boolean pointOnCircle(long circX, long circY, long rad, long x, long y) {
		long dx = x - circX;
		long dy = y - circY;
		return dx * dx + dy * dy == rad * rad;
	}

	static boolean pointInSquare(long squareX, long squareY, long side, long x, long y) {
		return x > squareX && x < squareX + side && y > squareY && y < squareY + side;
	}

	static boolean pointOnSquare(long squareX, long squareY, long side, long x, long y) {
		return (x == squareX && y >= squareY && y <= squareY + side)
			|| (x == squareX + side && y >= squareY && y <= squareY + side)
			|| (x >= squareX && x <= squareX + side && y == squareY)
			|| (x >= squareX && x <= squareX + side && y == squareY + side);
	}

	static boolean overlap(long circX, long circY, long rad, long squareX, long squareY, long side) {
		return pointInCircle(circX, circY, rad, squareX, squareY)
			|| pointInCircle(circX, circY, rad, squareX + side, squareY)
			|| pointInCircle(circX, circY, rad, squareX, squareY + side)
			|| pointInCircle(circX, circY, rad, squareX + side, squareY + side)
			|| pointInSquare(squareX, squareY, side, circX - rad, circY)
			|| pointInSquare(squareX, squareY, side, circX + rad, circY)
			|| pointInSquare(squareX, squareY, side, circX, circY - rad)
			|| pointInSquare(squareX, squareY, side, circX, circY + rad)
			|| pointInSquare(squareX, squareY, side, circX, circY);
	}

	static boolean touch(long circX, long circY, long rad, long squareX, long squareY, long side) {
		return pointOnCircle(circX, circY, rad, squareX, squareY)
			|| pointOnCircle(circX, circY, rad, squareX + side, squareY)
			|| pointOnCircle(circX, circY, rad, squareX, squareY + side)
			|| pointOnCircle(circX, circY, rad, squareX + side, squareY + side)
			|| pointOnSquare(squareX, squareY, side, circX - rad, circY)
			|| pointOnSquare(squareX, squareY, side, circX + rad, circY)
			|| pointOnSquare(squareX, squareY, side, circX, circY - rad)
			|| pointOnSquare(squareX, squareY, side, circX, circY + rad);
	}

}