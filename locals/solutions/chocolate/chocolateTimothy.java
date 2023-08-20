import java.util.*;

public class chocolateTimothy {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		long w = in.nextLong();
		long h = in.nextLong();
		long x = in.nextLong();

		if (w > h) {
			long tmp = w;
			w = h;
			h = tmp;
		}
		long min = 1;
		long  max = 2000000000;
		while (min < max) {
			long mid = (min + max + 1) / 2;
			Pair p = calc(w, h, mid);
			if (p.area > x) {
				max = mid - 1;
			} else {
				min = mid;
			}
		}
		Pair p = calc(w, h, min);
		Pair p2 = calc(w, h, min + 1);
		long possibleExtra = p2.count - p.count;
		long ans = p.count + Math.min(possibleExtra, (x - p.area) / (min + 1));
		System.out.printf("%d\n", ans);
	}

	// Returns a pair of total area and number of unique bars that can be made
	// with each bar having area at most maxSize and fitting into a box of size w x h
	static Pair calc(long w, long h, long maxSize) {
		Pair p = new Pair(0, 0);
		double dArea = 0.0;
		double dCount = 0.0;
		long MAX_X = 1000000;
		// Do everything with doubles first to skip cases that overflow
		for (long x = 1; x <= w && x <= MAX_X; x++) {
			if (maxSize / x == 0) break;
			if (x * x > maxSize) break;
			// x < y
			{
				long minY = x + 1;
				long maxY = Math.min(h, maxSize / x);
				if (minY <= maxY) {
					dCount += maxY - minY + 1;
					dArea += x * sumRangeDouble(minY, maxY);
				}
			}
			// y <= x
			{
				long minY = x;
				long maxY = Math.min(w, maxSize / x);
				if (minY <= maxY) {
					dCount += maxY - minY + 1;
					dArea += x * sumRangeDouble(minY, maxY);
				}
			}
		}
		if (dArea >= 1.1e18) {
			p.area = 1100000000000000000L;
			p.count = 0;
			return p;
		}
		
		p.area = 0;
		p.count = 0;
		for (long x = 1; x <= w && x <= MAX_X; x++) {
			if (maxSize / x == 0) break;
			if (x * x > maxSize) break;
			// x < y
			{
				long minY = x + 1;
				long maxY = Math.min(h, maxSize / x);
				if (minY <= maxY) {
					p.count += maxY - minY + 1;
					p.area += x * sumRange(minY, maxY);
				}
			}
			// y <= x
			{
				long minY = x;
				long maxY = Math.min(w, maxSize / x);
				if (minY <= maxY) {
					p.count += maxY - minY + 1;
					p.area += x * sumRange(minY, maxY);
				}
			}
		}
		return p;
	}

	// Returns the sum of all integers in the range [a, b]
	static long sumRange(long a, long b) {
		if (b < a) {
			return 0;
		}
	 long len = b - a + 1;
		return (a - 1) * len + len * (len + 1) / 2;
	}

	// Returns the sum (as a double) of all integers in the range [a, b]
	static double sumRangeDouble(double a, double b) {
		if (b < a) {
			return 0;
		}
		double len = b - a + 1;
		return (a - 1) * len + len * (len + 1) / 2;
	}
	
	static class Pair {
		long area, count;
		public Pair(long area, long count) {
			this.area = area;
			this.count = count;
		}
	}

}