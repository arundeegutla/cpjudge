import java.util.*;

public class firstlastTimothy {
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int ans = 0;
		int[] dp = new int[n + 1];
		for (int i = 0; i < n; i++) {
			int x = in.nextInt();
			ans = Math.max(ans, dp[x] = dp[x - 1] + 1);
		}
		System.out.printf("%d\n", n - ans);
	}

}