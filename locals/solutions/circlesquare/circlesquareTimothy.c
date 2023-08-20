#include <stdio.h>

int pointInCircle(long circX, long circY, long rad, long x, long y) {
	long dx = x - circX;
	long dy = y - circY;
	return dx * dx + dy * dy < rad * rad;
}

int pointOnCircle(long circX, long circY, long rad, long x, long y) {
	long dx = x - circX;
	long dy = y - circY;
	return dx * dx + dy * dy == rad * rad;
}

int pointInSquare(long squareX, long squareY, long side, long x, long y) {
	return x > squareX && x < squareX + side && y > squareY && y < squareY + side;
}

int pointOnSquare(long squareX, long squareY, long side, long x, long y) {
	return (x == squareX && y >= squareY && y <= squareY + side)
		|| (x == squareX + side && y >= squareY && y <= squareY + side)
		|| (x >= squareX && x <= squareX + side && y == squareY)
		|| (x >= squareX && x <= squareX + side && y == squareY + side);
}

int overlap(long circX, long circY, long rad, long squareX, long squareY, long side) {
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

int touch(long circX, long circY, long rad, long squareX, long squareY, long side) {
	return pointOnCircle(circX, circY, rad, squareX, squareY)
		|| pointOnCircle(circX, circY, rad, squareX + side, squareY)
		|| pointOnCircle(circX, circY, rad, squareX, squareY + side)
		|| pointOnCircle(circX, circY, rad, squareX + side, squareY + side)
		|| pointOnSquare(squareX, squareY, side, circX - rad, circY)
		|| pointOnSquare(squareX, squareY, side, circX + rad, circY)
		|| pointOnSquare(squareX, squareY, side, circX, circY - rad)
		|| pointOnSquare(squareX, squareY, side, circX, circY + rad);
}

int main(void) {
	long circX, circY, rad, squareX, squareY, side;
	scanf("%ld %ld %ld", &circX, &circY, &rad);
	scanf("%ld %ld %ld", &squareX, &squareY, &side);

	if (overlap(circX, circY, rad, squareX, squareY, side)) {
		printf("2\n");
	} else if (touch(circX, circY, rad, squareX, squareY, side)) {
		printf("1\n");
	} else {
		printf("0\n");
	}

	return 0;
}
