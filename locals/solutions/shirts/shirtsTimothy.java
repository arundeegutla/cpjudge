import java.util.*;

public class shirtsTimothy {

	static int[] digs, d10s, d1s;
	static int len, n;

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String str = in.next();
		n = in.nextInt();
		digs = new int[len = str.length()];
		for (int i = 0; i < len; i++)
			digs[i] = str.charAt(i) - '0';
		d10s = new int[n];
		d1s = new int[n];
		for (int i = 0; i < n; i++) {
			int x = in.nextInt();
			d10s[i] = x / 10;
			d1s[i] = x % 10;
		}
		boolean[] used = new boolean[n];
		System.out.printf("%d\n", go(0, used, false) ? 1 : 0);
	}

	static boolean go(int i, boolean used[], boolean usedMe) {
		if (i == len)
			return true;
		if (i > len || digs[i] == 0)
			return false;
		if (!usedMe && (go(i + 1, used, true) || go(i + 2, used, true)))
			return true;
		outer: for (int j = 0; j < n; j++) {
			if (!used[j]) {
				for (int k = 0; k < j; k++) {
					if (!used[k] && d1s[j] == d1s[k] && d10s[j] == d10s[k]) {
						continue outer;
					}
				}
				if (d10s[j] == 0) {
					if (d1s[j] == digs[i]) {
						used[j] = true;
						if (go(i + 1, used, usedMe))
							return true;
						used[j] = false;
					}
				} else if (i + 1 < len) {
					if (d10s[j] == digs[i] && d1s[j] == digs[i + 1]) {
						used[j] = true;
						if (go(i + 2, used, usedMe))
							return true;
						used[j] = false;
					}
				}
			}
		}
		return false;
	}

}