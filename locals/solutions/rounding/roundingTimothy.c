#include <stdio.h>

// Counts the number of times the prime d shows up in the factorization of x.
int count(long long x, long long d) {
	long long ans = 0;
	while (x > 0 && x % d == 0) {
		ans++;
		x /= d;
	}
	return ans;
}

// Returns b ^ e
long long power(long long b, long long e) {
	long long ans = 1;
	for (int i = 0; i < e; i++)
		ans *= b;
	return ans;
}

int main(void) {
	long long n;
	scanf("%lld", &n);
	long long num2 = count(n, 2);
	long long num5 = count(n, 5);
	
	// Because the numbers are small, we can just loop through them all every time to "sort".
	long long num = (num2 + 1) * (num5 + 1);
	printf("%lld\n", num);
	long long prev = 0;
	for (int i = 0; i < num; i++) {
		// Find the next smallest number larger than prev.
		long long next = 2000000000000000000LL;
		for (int p2 = 0; p2 <= num2; p2++) {
			for (int p5 = 0; p5 <= num5; p5++) {
				long long val = power(2, p2) * power(5, p5);
				if (val > prev && val < next)
					next = val;
			}
		}
		printf("%lld\n", next);
		prev = next;
	}
	return 0;
}