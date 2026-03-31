import java.util.*;

public class SubarraysWithZeroSum {

	public static void main(String[] args) {
		int arr[] = { 3, 4, -7, 1, 3, 3, 1, -4 };
		HashMap<Integer, Integer> map = new HashMap<>();
		map.put(0, -1);
		int sum = 0;
		for (int i = 0; i < arr.length; i++) {
			sum += arr[i];
			if (map.containsKey(sum)) {
				System.out.println("Zero sum Subarray is- " + (map.get(sum) + 1) + " " + i);
			} else {
				map.put(sum, i);
			}
		}
	}

}
