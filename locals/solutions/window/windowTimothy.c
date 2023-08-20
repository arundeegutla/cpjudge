#include <stdio.h>

int main(void) {
	long w, h, d;
	scanf("%ld %ld %ld", &w, &h, &d);
	long ans = 0;
	if (w > 2 * d && h > 2 * d) {
		ans = (w - 2 * d) * (h - 2 * d);
	}
	printf("%ld\n", ans);
	return 0;
}