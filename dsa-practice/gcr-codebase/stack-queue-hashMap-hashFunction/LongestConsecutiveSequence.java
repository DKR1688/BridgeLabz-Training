import java.util.*;

public class LongestConsecutiveSequence {

	public static void main(String[] args) {
		int[] nums = { 100, 4, 200, 1, 3, 2, 5 };
		HashSet<Integer> set = new HashSet<>();
		int longest = 0;

		for (int num : nums) {
			set.add(num);
		}
		for (int num : nums) {
			if (!set.contains(num - 1)) {
				int count = 1;
				int currNum = num;
				while (set.contains(currNum + 1)) {
					currNum++;
					count++;
				}
				longest = Math.max(count, longest);

			}

		}
		System.out.println("Longest swquence is- " + longest);

	}

}
