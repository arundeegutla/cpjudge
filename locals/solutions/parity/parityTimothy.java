import java.util.*;

public class parityTimothy {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String str = in.next();
		int parity = 0, targetEven = 0, targetOdd = 0;
		for (char c : str.toCharArray()) {
			int bit = 1 << (c - 'a');
			parity ^= bit;
			targetOdd |= bit;
		}
		if (parity == targetEven) {
			System.out.println(0);
		} else if (parity == targetOdd) {
			System.out.println(1);
		} else {
			System.out.println(2);
		}
	}
	
}