// Arup Guha
// 8/9/2018
// Alternate Solution to 2018 UCF Locals Problem: SGA President

import java.util.*;

public class sga_alt {
	
	public static void main(String[] args) {

		// Set up 26 ArrayLists of names, by first letter.		
		Scanner stdin = new Scanner(System.in);
		int n = stdin.nextInt();
		ArrayList[] lists = new ArrayList[26];
		for (int i=0; i<26; i++) lists[i] = new ArrayList<String>();
		
		// Read name into appropriate list.
		for (int i=0; i<n; i++) {
			String name = stdin.next();
			lists[name.charAt(0)-'A'].add(name);
		}
		
		// Sort each list.
		for (int i=0; i<26; i++) Collections.sort(lists[i]);
		
		long res = 0;
		
		// Go through each list.
		for (int i=0; i<26; i++) {
			
			int j = 0;
			
			// Sweep through this list.
			while (j < lists[i].size()) {
				
				// Find all names equal to name j on this list.
				int k = j+1;
				while (k < lists[i].size() && lists[i].get(k).equals(lists[i].get(j))) k++;
				
				// Basically, we can pair this name with all others in the list that aren't it.
				res = res + (k-j)*(lists[i].size()-(k-j));
				
				// Moves to next unique name.
				j = k;
			}
		}
		
		// Ta da!!!
		System.out.println(res);
	}
}