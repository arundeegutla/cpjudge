#include <stdlib.h>
#include <stdio.h>
#include <string.h>

static int stringCompare(const void *a, const void *b) {
	return strcmp(*(const char **) a, *(const char **) b);
}

void sort(const char *arr[], int n) {
	qsort(arr, n, sizeof(const char *), stringCompare);
}

int main(void) {
	int n;
	scanf("%d", &n);
	const char **names = (const char **) malloc(n * sizeof(const char*));
	for (int i = 0; i < n; i++) {
		names[i] = (const char *) malloc(21 * sizeof(const char));
		scanf("%s", names[i]);
	}
	sort(names, n);
	long long ans = 0;
	char curLetter = names[0][0];
	long long numCurLetter = 1;
	long long numCurName = 1;
	for (int i = 1; i < n; i++) {
		if (names[i][0] != curLetter) {
			curLetter = names[i][0];
			numCurLetter = 1;
			numCurName = 1;
			continue;
		}
		if (strcmp(names[i], names[i - 1]) != 0) {
			numCurName = 0;
		}
		ans += 2 * (numCurLetter++ - numCurName++);
	}
	printf("%lld\n", ans);
	return 0;
}