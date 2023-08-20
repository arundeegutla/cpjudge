#include <stdio.h>
#include <stdlib.h>

typedef struct Pair {
	long long area;
	long long count;
} Pair;

long long getMin(long long a, long long b) {
	return a < b ? a : b;
}

// Returns the sum of all integers in the range [a, b]
long long sumRange(long long a, long long b) {
	if (b < a) {
		return 0;
	}
	long long len = b - a + 1;
	return (a - 1) * len + len * (len + 1) / 2;
}

// Returns the sum (as a double) of all integers in the range [a, b]
double sumRangeDouble(double a, double b) {
	if (b < a) {
		return 0;
	}
	double len = b - a + 1;
	return (a - 1) * len + len * (len + 1) / 2;
}

// Returns a pair of total area and number of unique bars that can be made
// with each bar having area at most maxSize and fitting into a box of size w x h
Pair calc(long long w, long long h, long long maxSize) {
	Pair p;
	double dArea = 0.0;
	double dCount = 0.0;
	long long MAX_X = 1000000;
	// Do everything with doubles first to skip cases that overflow
	for (long long x = 1; x <= w && x <= MAX_X; x++) {
		if (maxSize / x == 0) break;
		if (x * x > maxSize) break;
		// x < y
		{
			long long minY = x + 1;
			long long maxY = getMin(h, maxSize / x);
			if (minY <= maxY) {
				dCount += maxY - minY + 1;
				dArea += x * sumRangeDouble(minY, maxY);
			}
		}
		// y <= x
		{
			long long minY = x;
			long long maxY = getMin(w, maxSize / x);
			if (minY <= maxY) {
				dCount += maxY - minY + 1;
				dArea += x * sumRangeDouble(minY, maxY);
			}
		}
	}
	if (dArea >= 1.1e18) {
		p.area = 1100000000000000000LL;
		p.count = 0;
		return p;
	}
	
	p.area = 0;
	p.count = 0;
	for (long long x = 1; x <= w && x <= MAX_X; x++) {
		if (maxSize / x == 0) break;
		if (x * x > maxSize) break;
		// x < y
		{
			long minY = x + 1;
			long maxY = getMin(h, maxSize / x);
			if (minY <= maxY) {
				p.count += maxY - minY + 1;
				p.area += x * sumRange(minY, maxY);
			}
		}
		// y <= x
		{
			long minY = x;
			long maxY = getMin(w, maxSize / x);
			if (minY <= maxY) {
				p.count += maxY - minY + 1;
				p.area += x * sumRange(minY, maxY);
			}
		}
	}
	return p;
}


int main(void) {
	long long w, h, x;
	scanf("%lld %lld %lld", &w, &h, &x);
	if (w > h) {
		long long tmp = w;
		w = h;
		h = tmp;
	}
	long long min = 1;
	long long max = 2000000000LL;
	while (min < max) {
		long long mid = (min + max + 1) / 2;
		Pair p = calc(w, h, mid);
		if (p.area > x) {
			max = mid - 1;
		} else {
			min = mid;
		}
	}
	Pair p = calc(w, h, min);
	Pair p2 = calc(w, h, min + 1);
	long long possibleExtra = p2.count - p.count;
	long long ans = p.count + getMin(possibleExtra, (x - p.area) / (min + 1));
	printf("%lld\n", ans);
	
	return 0;
}