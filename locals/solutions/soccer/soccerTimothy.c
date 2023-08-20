#include <stdio.h>

int xs[22];
int ys[22];
int n;

int min(int a, int b) {
	return a < b ? a : b;
}

int max(int a, int b) {
	return a > b ? a : b;
}

int isBetween(int ai, int bi, int ci) {
	int ax = xs[ai], ay = ys[ai];
	int bx = xs[bi], by = ys[bi];
	int cx = xs[ci], cy = ys[ci];
	if (bx < min(ax, cx) || bx > max(ax, cx))
		return 0;
	if (by < min(ay, cy) || by > max(ay, cy))
		return 0;
	int abx = bx - ax, aby = by - ay;
	int acx = cx - ax, acy = cy - ay;
	return abx * acy - aby * acx == 0;
}

int canPass(int ai, int bi) {
	for (int i = 0; i < n * 2; i++)
		if (i != ai && i != bi && isBetween(ai, i, bi))
		return 0;
	return 1;
}

int main(void) {
	scanf("%d", &n);
	for (int i = 0; i < n * 2; i++) {
		scanf("%d", &xs[i]);
		scanf("%d", &ys[i]);
	}
	int dist[11][11];
	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
			if (i == j)
				dist[i][j] = 0;
			else
				dist[i][j] = canPass(i, j) ? 1 : 1000;
		}
	}
	for (int k = 0; k < n; k++)
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				if (dist[i][k] + dist[k][j] < dist[i][j])
					dist[i][j] = dist[i][k] + dist[k][j];
	printf("%d\n", dist[0][n - 1] < 1000 ? dist[0][n - 1] : -1);
	return 0;
}