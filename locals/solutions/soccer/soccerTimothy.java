import java.util.*;

public class soccerTimothy {
	
	static int n;
	static Vec[] ps;
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		n = in.nextInt();
		ps = new Vec[n * 2];
		for (int i = 0; i < n * 2; i++) {
			ps[i] = new Vec(in.nextInt(), in.nextInt());
		}
		int[][] dist = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i == j)
					dist[i][j] = 0;
				else
					dist[i][j] = canPass(i, j) ? 1 : 1000;
			}
		}
		for (int k = 0; k < n; k++)
			for (int i = 0; i < n; i++)
				for (int j = 0; j < n; j++)
					if (dist[i][k] + dist[k][j] < dist[i][j])
						dist[i][j] = dist[i][k] + dist[k][j];
		System.out.printf("%d\n", dist[0][n - 1] < 1000 ? dist[0][n - 1] : -1);
	}
	
	static boolean canPass(int ai, int bi) {
		for (int i = 0; i < n * 2; i++)
			if (i != ai && i != bi && Vec.isBetween(ps[ai], ps[i], ps[bi]))
				return false;
		return true;
	}
	
	static class Vec {
		long x, y;
		public Vec(long xx, long yy) {
			x = xx; y = yy;
		}
		public Vec sub(Vec v) {
			return new Vec(x - v.x, y - v.y);
		}
		public long cross(Vec v) {
			return x * v.y - y * v.x;
		}
		public static boolean isBetween(Vec a, Vec b, Vec c) {
			if (b.x < Math.min(a.x, c.x) || b.x > Math.max(a.x, c.x))
				return false;
			if (b.y < Math.min(a.y, c.y) || b.y > Math.max(a.y, c.y))
				return false;
			return b.sub(a).cross(c.sub(a)) == 0;
		}
		public String toString() {
			return "(" + x + ", " + y + ")";
		}
	}

}