import java.util.*;

public class sgaTimothy {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		long[] letterCounts = new long[26];
		HashMap<String, Long>[] nameCounts = new HashMap[26];
		for (int i = 0; i < 26; i++)
			nameCounts[i] = new HashMap<>();
		long ans = 0;
		for (int i = 0; i < n; i++) {
			String name = in.next();
			int letter = name.charAt(0) - 'A';

			long letterCount = letterCounts[letter];
			long nameCount = nameCounts[letter].containsKey(name) ? nameCounts[letter].get(name) : 0;
			
			ans += 2 * (letterCount - nameCount);
			
			letterCounts[letter]++;
			nameCounts[letter].put(name, nameCount + 1);
		}
		System.out.println(ans);
	}
}