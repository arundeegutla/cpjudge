#include <stdio.h>

int digs[10];
int d10s[25];
int d1s[25];
int len;
int n;

int go(int i, int used[], int usedMe) {
	if (i == len)
		return 1;
	if (i > len || digs[i] == 0)
		return 0;
	if (!usedMe && (go(i + 1, used, 1) || go(i + 2, used, 1)))
		return 1;
	for (int j = 0; j < n; j++) {
		if (!used[j]) {
			int usedBefore = 0;
			for (int k = 0; k < j; k++) {
				if (!used[k] && d1s[j] == d1s[k] && d10s[j] == d10s[k]) {
					usedBefore = 1;
					break;
				}
			}
			if (usedBefore)
				continue;
			if (d10s[j] == 0) {
				if (d1s[j] == digs[i]) {
					used[j] = 1;
					if (go(i + 1, used, usedMe))
						return 1;
					used[j] = 0;
				}
			} else if (i + 1 < len) {
				if (d10s[j] == digs[i] && d1s[j] == digs[i + 1]) {
					used[j] = 1;
					if (go(i + 2, used, usedMe))
						return 1;
					used[j] = 0;
				}
			}
		}
	}
	return 0;
}

int main(void) {
	long num;
	scanf("%d %ld", &num, &n);
	len = 0;
	long tmp = num;
	while (tmp > 0) {
		tmp /= 10;
		len++;
	}
	int idx = len - 1;
	while (num > 0) {
		digs[idx--] = num % 10;
		num /= 10;
	}
	for (int i = 0; i < n; i++) {
		int x;
		scanf("%d", &x);
		d10s[i] = x / 10;
		d1s[i] = x % 10;
	}
	int used[25];
	for (int i = 0; i < n; i++)
		used[i] = 0;
	printf("%d\n", go(0, used, 0));
	return 0;
}