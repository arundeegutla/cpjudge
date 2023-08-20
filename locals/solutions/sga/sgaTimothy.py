n = int(input())
names = [None] * n;
for i in range(n):
	names[i] = input()
names.sort()
ans = 0
curLetter = names[0][0]
numCurLetter = 1
numCurName = 1
for i in range(1, n):
	if names[i][0] != curLetter:
		curLetter = names[i][0]
		numCurLetter = 1
		numCurName = 1
		continue
	if names[i] != names[i - 1]:
		numCurName = 0
	ans = ans + 2 * (numCurLetter - numCurName)
	numCurLetter = numCurLetter + 1
	numCurName = numCurName + 1
print(ans)
