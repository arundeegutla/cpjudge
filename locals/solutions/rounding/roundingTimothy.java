import java.util.*;

public class roundingTimothy {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		long n = in.nextLong();
		int num2 = count(n, 2);
		int num5 = count(n, 5);
		long[] ans = new long[(num2 + 1) * (num5 + 1)];
		int idx = 0;
		long p2 = 1;
		for (int i = 0; i <= num2; i++) {
			long p5 = 1;
			for (int j = 0; j <= num5; j++) {
				ans[idx++] = p2 * p5;
				p5 *= 5;
			}
			p2 *= 2;
		}
		Arrays.sort(ans);
		System.out.printf("%d\n", ans.length);
		for (long a : ans)
			System.out.printf("%d\n", a);
	}
	
	// Counts the number of times the prime d shows up in the factorization of x.
	static int count(long x, long d) {
		int ans = 0;
		while (x > 0 && x % d == 0) {
			ans++;
			x /= d;
		}
		return ans;
	}

}