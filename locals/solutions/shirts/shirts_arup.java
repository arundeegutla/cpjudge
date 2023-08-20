import java.util.*;

public class shirts_arup {
	
	public static String target;
	public static int n;
	public static String[] pieces;
	public static byte[] memo;
	
	public static void main(String[] args) {
		
		// Read input, and store as strings...
		Scanner stdin = new Scanner(System.in);
		target = stdin.next();
		n = stdin.nextInt();
		
		
		pieces = new String[n];
		for (int i=0; i<n; i++)
			pieces[i] = stdin.next();
		//Arrays.sort(pieces);
		
		// Set up memo and go!
		memo = new byte[1<<(n+2)];
		Arrays.fill(memo, (byte)-1);
		System.out.println(go(0, 0));
	}
	
	// k = index into the string to match, mask is what has been used.
	public static int go(int k, int mask) {
		
		if (k >= target.length()) return 1;
		if (memo[mask] != -1) return memo[mask];
		
		// If we haven't done Travis's number, try it as one or two digits.
		if (((mask >> n) == 0) && target.charAt(k) != '0') {
			int tmp = go(k+1, mask|(1<<n));
			if (tmp == 0) tmp = go(k+2, mask|(1<<(n+1)));
			if (tmp == 1) return memo[mask] = 1;
		}
		
		// Try each shirt.
		int res = 0;
		for (int i=0; i<n; i++) {
			if ((mask & (1<<i)) == 0) {
				if (match(pieces[i], target, k)) {
					res = go(k+pieces[i].length(), mask|(1<<i));
					if (res == 1) return memo[mask] = 1;
				}
			}
		}
		
		// Never made it work, we can't do it.
		return memo[mask] = 0;
	}
	
	// Returns true iff a matches b[k..].
	public static boolean match(String a, String b, int k) {
		
		// Avoid array out of bounds.
		if (k + a.length() > b.length()) return false;
		
		// Just cut out if a char doesn't match that is supposed to.
		for (int i=0,j=k; i<a.length(); i++,j++)
			if (a.charAt(i) != b.charAt(j))
				return false;
		
		// We're good if we get here.
		return true;
	}

}
