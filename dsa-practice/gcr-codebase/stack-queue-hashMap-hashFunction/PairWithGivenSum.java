import java.util.*;

public class PairWithGivenSum {

	public static void main(String[] args) {
		HashSet<Integer> set = new HashSet<>();
		int arr[] = { 8, 7, 2, 5, 3, 1 };
		int target = 10;
		int f = 0;
		for (int i = 0; i < arr.length; i++) {
			int compliment = target - arr[i];
			if (set.contains(compliment)) {
				System.out.println("Pair found: " + arr[i] + " " + compliment);
				f = 1;
				break;
			}
			set.add(arr[i]);
		}
		if (f == 0) {
			System.out.println("No Pair Found");
		}

	}

}
