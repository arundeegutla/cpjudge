#include <stdio.h>

long max(long a, long b) {
	return a > b ? a : b;
}

int main(void) {
	long dp[100001];
	long n, x, ans = 0;
	scanf("%ld", &n);
	for (long i = 0; i <= n; i++)
		dp[i] = 0;
	for (long i = 0; i < n; i++) {
		scanf("%ld", &x);
		ans = max(ans, dp[x] = dp[x - 1] + 1);
	}
	printf("%ld\n", n - ans);
	return 0;
}