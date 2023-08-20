n = int(input())
ans = 0
dp = [0] * (n + 1)
for i in range(n):
	x = int(input())
	dp[x] = dp[x - 1] + 1
	ans = max(ans, dp[x])
print(n - ans)
