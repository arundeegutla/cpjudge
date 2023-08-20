import java.util.*;

public class remoteTimothy {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		ArrayList<Integer> brokenDigs = new ArrayList<>();
		for (int i = 0; i < n; i++)
			brokenDigs.add(in.nextInt());
		int target = in.nextInt();
		int ans = 1000000;
		for (int channel = 0; channel <= 999; channel++) {
			int dif = Math.abs(target - channel);
			if (dif < ans && isValidChannel(channel, brokenDigs))
				ans = dif;
		}
		System.out.println(ans);
	}
	
	static boolean isValidChannel(int channel, ArrayList<Integer> broken) {
		String str = "" + channel;
		for (int dig : broken)
			if (str.contains("" + dig))
				return false;
		return true;
	}
}