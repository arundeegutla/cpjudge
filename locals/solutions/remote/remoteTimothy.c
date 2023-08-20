#include <stdio.h>

int containsDig(int channel, int dig) {
	if (channel == 0 && dig == 0) return 1;
	while (channel > 0) {
		if ((channel % 10) == dig)
			return 1;
		channel /= 10;
	}
	return 0;
}

int isValidChannel(int channel, int broken[]) {
	for (int dig = 0; dig < 10; dig++) {
		if (broken[dig] && containsDig(channel, dig))
			return 0;
	}
	return 1;
}

int abs(int x) {
	return x < 0 ? -x : x;
}

int main(void) {
	int n, target, dig;
	scanf("%d", &n);
	int broken[10];
	for (int i = 0; i < 10; i++) {
		broken[i] = 0;
	}
	for (int i = 0; i < n; i++) {
		scanf("%d", &dig);
		broken[dig] = 1;
	}
	scanf("%d", &target);
	int ans = 2000;
	for (int channel = 0; channel <= 999; channel++) {
		int dif = abs(channel - target);
		if (dif < ans && isValidChannel(channel, broken))
			ans = dif;
	}
	printf("%d\n", ans);
	return 0;
}