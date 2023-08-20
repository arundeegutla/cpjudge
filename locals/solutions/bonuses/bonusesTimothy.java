import java.util.*;
import java.io.*;

public class bonusesTimothy {

	static final int NEW_HIRE = 1;
	static final int CHANGE_MULTIPLIER = 2;
	static final int PAY_BONUS = 3;
	static final int QUERY_AMOUNT = 4;
	
	static int[] preOrder, postOrder;
	static long[] multipliers;
	static long[] bonusAmounts;
	static ArrayList<Integer>[] adjs;
	static int curVal;
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int numQueries = in.nextInt();
		long startingMultiplier = in.nextLong();
		int[] types = new int[numQueries];
		int[] ids = new int[numQueries];
		long[] vals = new long[numQueries];
		int numNodes = 1;
		for (int i = 0; i < numQueries; i++) {
			types[i] = in.nextInt();
			ids[i] = in.nextInt() - 1;
			if (types[i] == CHANGE_MULTIPLIER || types[i] == PAY_BONUS) {
				vals[i] = in.nextLong();
			}
			if (types[i] == NEW_HIRE) {
				numNodes++;
			}
		}
		adjs = new ArrayList[numNodes];
		for (int i = 0; i < numNodes; i++) {
			adjs[i] = new ArrayList<>();
		}
		int curId = 1;
		for (int i = 0; i < numQueries; i++) {
			if (types[i] == NEW_HIRE) {
				adjs[ids[i]].add(curId++);
			}
		}
		curVal = 1;
		preOrder = new int[numNodes];
		postOrder = new int[numNodes];
		dfs(0);
		BIT bit = new BIT(curVal + 1);
		
		multipliers = new long[numNodes];
		Arrays.fill(multipliers, startingMultiplier);
		bonusAmounts = new long[numNodes];
		curId = 1;

		for (int i = 0; i < numQueries; i++) {
			if (types[i] == NEW_HIRE) {
				int id = curId++;
				bit.clear(preOrder[id]);
			} else if (types[i] == CHANGE_MULTIPLIER) {
				int id = ids[i];
				bonusAmounts[id] += bit.query(preOrder[id]) * multipliers[id];
				bit.clear(preOrder[id]);
				multipliers[id] = vals[i];
			} else if (types[i] == PAY_BONUS) {
				int id = ids[i];
				bit.update(preOrder[id], postOrder[id], vals[i]);
			} else if (types[i] == QUERY_AMOUNT) {
				int id = ids[i];
				out.println(bit.query(preOrder[id]) * multipliers[id] + bonusAmounts[id]);
			}
		}
		out.close();
	}

	static void dfs(int cur) {
		preOrder[cur] = curVal++;
		for (int next : adjs[cur]) {
			dfs(next);
		}
		postOrder[cur] = curVal - 1;
	}
	
	static class BIT {
		int n;
		long[] tree;

		public BIT(int n) {
			this.n = n;
			tree = new long[n + 1];
		}
	
		long query(int i) {
			long sum = 0;
			while (i > 0) {
				sum += tree[i];
				i -= i & -i;
			}
			return sum;
		}
		
		void clear(int i) {
			update(i, i, -query(i));
		}
		
		void update(int i, int j, long val) {
			update(i, val);
			update(j + 1, -val);
		}
		
		void update(int i, long val) {
			while (i <= n) {
				tree[i] += val;
				i += i & -i;
			}
		}
	}
}
