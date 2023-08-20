#include<stdio.h>
#include<string.h>

int main(void) {
	char str[71];
	scanf("%s", str);
	long long parity = 0, targetEven = 0, targetOdd = 0;
	int len = strlen(str);
	for (int i = 0; i < len; i++) {
		long long bit = 1LL << (str[i] - 'a');
		parity ^= bit;
		targetOdd |= bit;
	}
	if (parity == targetEven) {
		printf("0\n");
	} else if (parity == targetOdd) {
		printf("1\n");
	} else {
		printf("2\n");
	}
	return 0;
}