// Arup Guha
// 5/4/2018
// Solution to Potential 2018 UCF Locals Problem: First-Last Sorting

import java.util.*;

public class firstlast {

	public static void main(String[] args) {

		// Note: I am using a Scanner because I want our run-times to allow for newcomers to solve this problem
		//       without using a FastScanner/BufferedReader.
		Scanner stdin = new Scanner(System.in);
		int n = stdin.nextInt();
		int[] list = new int[n];
		int[] inv = new int[n];

		// Just read the input and map inverse function.
		for (int i=0; i<n; i++) {
			list[i] = stdin.nextInt()-1;
			inv[list[i]] = i;
		}

		// dp[i] will store max length subsequence of consecutive ints ending at list[i].
		int[] dp = new int[n];
		int consec = 0;

		// Run our algorithm to efficiently find the longest subsequence of consecutive values.
		for (int i=0; i<n; i++) {

			// To avoid array out of bounds.
			if (list[i] == 0) dp[i] = 1;

			// We can build off the previous term.
			else if (inv[list[i]-1] < i) dp[i] = dp[inv[list[i]-1]] + 1;

			// This must start a new streak.
			else dp[i] = 1;

			// Update longest consecutive streak.
			consec = Math.max(consec, dp[i]);
		}

		// Fewest number of ops is list length minus LIS.
		System.out.println(n-consec);
	}
}