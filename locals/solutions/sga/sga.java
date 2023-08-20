// Arup Guha
// 5/4/2018
// Solution to 2018 UCF Locals Problem: SGA President

import java.util.*;

public class sga {

	public static int n;
	public static HashMap[] maps;
	public static long[] sizes;

	public static void main(String[] args) {

		// Note: I am using a Scanner because I want our run-times to allow for newcomers to solve this problem
		//       without using a FastScanner/BufferedReader.
		Scanner stdin = new Scanner(System.in);
		n = stdin.nextInt();
		
		// Set up my HashMaps.
		maps = new HashMap[26];
		sizes = new long[26];
		for (int i=0; i<maps.length; i++)
			maps[i] = new HashMap<String,Integer>();
			
			
		// Read in all the names in to the appropriate HashMaps.	
		for (int i=0; i<n; i++) {
			
			// Get the name and first letter, marking how many names start with that letter.
			String name = stdin.next();
			int idx = name.charAt(0)-'A';
			sizes[idx]++;
			
			// Place in the appropriate hash map how many of this name we've seen.
			if (maps[idx].containsKey(name))
				maps[idx].put(name, ((Integer)maps[idx].get(name))+1);
			else
				maps[idx].put(name, 1);
		}
		
		long res = 0;
		
		// Count up the result.
		for (int i=0; i<26; i++) {
			
			// Go through each name as a possible president, mapping to all other distinct names starting
			// with the same letter as VP.
			for (String s: ((HashMap<String,Integer>)maps[i]).keySet()) {
				int freq = (Integer)maps[i].get(s);
				res = res + freq*(sizes[i]-freq);
			}
		}
		
		// Ta da!
		System.out.println(res);
	}
}