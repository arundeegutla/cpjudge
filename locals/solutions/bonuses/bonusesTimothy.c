#include <stdio.h>
#include <stdlib.h>

#define NEW_HIRE 1
#define CHANGE_MULTIPLIER 2
#define PAY_BONUS 3
#define QUERY_AMOUNT 4

#define MAX_N 100000

long preOrder[MAX_N + 1];
long postOrder[MAX_N + 1];
long numChildren[MAX_N + 1];
long long multipliers[MAX_N + 1];
long long bonusAmounts[MAX_N + 1];
long long bit[MAX_N + 3];
long curVal = 1;
long bitN = MAX_N + 2;
long *adjs[MAX_N + 1];

void dfs(long cur) {
	preOrder[cur] = curVal++;
	for (long i = 0; i < numChildren[cur]; i++)
		dfs(adjs[cur][i]);
	postOrder[cur] = curVal - 1;
}

long long bitQuery(long i) {
	long long sum = 0;
	while (i > 0) {
		sum += bit[i];
		i -= i & -i;
	}
	return sum;
}

void bitUpdate(long i, long long val) {
	while (i <= bitN) {
		bit[i] += val;
		i += i & -i;
	}
}
		
void bitUpdateRange(long i, long j, long long val) {
	bitUpdate(i, val);
	bitUpdate(j + 1, -val);
}
		
void bitClear(long i) {
	bitUpdateRange(i, i, -bitQuery(i));
}
		


int main(void) {
	long numQueries;
	scanf("%ld", &numQueries);
	long long startingMultiplier;
	scanf("%lld", &startingMultiplier);
	long types[MAX_N];
	long ids[MAX_N];
	long long vals[MAX_N];
	long numNodes = 1;
	long parents[MAX_N + 1];
	long childrenIds[MAX_N + 1];
	for (long i = 0; i < numQueries; i++) {
		scanf("%ld", &types[i]);
		scanf("%ld", &ids[i]);
		ids[i]--;
		if (types[i] == CHANGE_MULTIPLIER || types[i] == PAY_BONUS) {
			scanf("%lld", &vals[i]);
		}
		if (types[i] == NEW_HIRE) {
			parents[numNodes] = ids[i];
			numChildren[ids[i]]++;
			numNodes++;
		}
	}
	
	for (long i = 0; i < numNodes; i++) {
		adjs[i] = malloc(sizeof(long) * numChildren[i]);
		childrenIds[i] = 0;
	}
	for (long i = 1; i < numNodes; i++) {
		adjs[parents[i]][childrenIds[parents[i]]++] = i;
	}
	dfs(0);
	for (long i = 0; i <= curVal + 1; i++)
		bit[i] = 0;
	for (long i = 0; i < numNodes; i++) {
		multipliers[i] = startingMultiplier;
		bonusAmounts[i] = 0;
	}
	long curId = 1;
	for (long i = 0; i < numQueries; i++) {
		if (types[i] == NEW_HIRE) {
			long id = curId++;
			bitClear(preOrder[id]);
		} else if (types[i] == CHANGE_MULTIPLIER) {
			long id = ids[i];
			bonusAmounts[id] += bitQuery(preOrder[id]) * multipliers[id];
			bitClear(preOrder[id]);
			multipliers[id] = vals[i];
		} else if (types[i] == PAY_BONUS) {
			long id = ids[i];
			bitUpdateRange(preOrder[id], postOrder[id], vals[i]);
		} else if (types[i] == QUERY_AMOUNT) {
			long id = ids[i];
			printf("%lld\n", bitQuery(preOrder[id]) * multipliers[id] + bonusAmounts[id]);
		}
	}
	return 0;
}

