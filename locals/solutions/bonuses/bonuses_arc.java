import java.util.*;

public class bonuses_arc {
	static int[] employeeMultipliers;
	static long[] bonuses;
	static BIT pendingBonuses;
	static int gId = 0;
	static ArrayList<Integer>[] adj;
	static int[] preIds, postIds;

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int S = in.nextInt();

		// First, we'll read in all of the input, so that we can
		// build the tree before we answer queries
		Query[] input = new Query[n];
		for (int i = 0; i < n; i++) {
			Query query = new Query();
			query.type = in.nextInt();
			query.i = in.nextInt() - 1;
			if (query.type == 2) {
				query.M = in.nextInt();
			} else if (query.type == 3) {
				query.B = in.nextInt();
			}
			input[i] = query;
		}

		// Build the tree of employees
		adj = new ArrayList[n];
		for (int i = 0; i < n; i++)
			adj[i] = new ArrayList<>();
		int employeeCount = 1;
		for (Query q : input) {
			if (q.type == 1) {
				int curId = employeeCount++;
				int supervisor = q.i;
				adj[supervisor].add(curId);
			}
		}

		// Find dfs ordering (pre/post order) of tree
		preIds = new int[employeeCount];
		postIds = new int[employeeCount];
		dfs(0);

		// Now go through the queries, and solve them

		// Start everyone but the root with multiplier of 1 (to handle
		// that they aren't techinically hired yet)
		employeeMultipliers = new int[employeeCount];
		employeeMultipliers[0] = S;

		bonuses = new long[employeeCount];
		pendingBonuses = new BIT(employeeCount);
		int newNodeID = 1;

		for (Query q : input) {
			if (q.type == 1) {
				// When someone is hired, push any pending bonuses (should have
				// 0 multiplier) and update their multiplier
				pushBonus(newNodeID);
				employeeMultipliers[newNodeID++] = S;
			} else if (q.type == 2) {
				// When someone gets multiplier changed, push pending bonuses
				// and update multiplier
				pushBonus(q.i);
				employeeMultipliers[q.i] = q.M;
			} else if (q.type == 3) {
				// When a bonus is given to a division, update our BIT from
				// pre[i] to post[i], representing the subtree of i
				pendingBonuses.update(preIds[q.i], postIds[q.i], q.B);
			} else if (q.type == 4) {
				// If we need to print an answer, push pending bonuses and
				// print!
				pushBonus(q.i);
				System.out.println(bonuses[q.i]);
			}
		}
	}

	// Moves bonuses from the pendingBonuses BIT into an array slot
	static void pushBonus(int i) {
		// Query the pending bonus
		long pendingBonus = pendingBonuses.query(preIds[i]);
		// Add it in with the multiplier
		bonuses[i] += employeeMultipliers[i] * pendingBonus;
		// Remove it from the BIT
		pendingBonuses.update(preIds[i], preIds[i], -pendingBonus);
	}

	// Retrieves pre/post order numbers for the nodes, such that
	// the children of node i have pre orders ranging from pre[i] to post[i]
	// (a contiguous block, to make our updates easier)
	static void dfs(int node) {
		preIds[node] = gId++;
		for (int e : adj[node])
			dfs(e);
		postIds[node] = gId - 1;
	}

	static class Query {
		int type, i, M, B;
	}

	// Standard Fenwick Tree supporting range update, point query
	static class BIT {
		int n;
		long[] tree;

		public BIT(int nn) {
			n = nn;
			tree = new long[n + 1];
		}

		long query(int i) {
			i++;
			long sum = 0;
			while (i <= n) {
				sum += tree[i];
				i += i & -i;
			}
			return sum;
		}

		void update(int i, int j, long val) {
			update(j, val);
			update(i - 1, -val);
		}

		void update(int i, long val) {
			i++;
			while (i > 0) {
				tree[i] += val;
				i -= i & -i;
			}
		}
	}

}
