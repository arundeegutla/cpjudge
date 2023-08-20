import java.util.*;

public class windowTimothy {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int w = in.nextInt();
		int h = in.nextInt();
		int d = in.nextInt();
		if (w <= d * 2 || h <= d * 2) {
			System.out.println(0);
		} else {
			System.out.println((w - 2 * d) * (h - 2 * d));
		}
	}

}