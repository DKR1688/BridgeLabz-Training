import java.util.*;

public class SlidindWindowMaximum {
	public static int[] maxSlidingWindow(int[] nums, int k) {
		int n = nums.length;
		int[] ans = new int[n - k + 1];
		Deque<Integer> deque = new ArrayDeque<>();

		for (int i = 0; i < n; i++) {

			while (!deque.isEmpty() && deque.peekFirst() <= i - k) {
				deque.pollFirst();
			}

			while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[i]) {
				deque.pollLast();
			}

			deque.offerLast(i);

			if (i >= k-1) {
				ans[i - k+1] = nums[deque.peekFirst()];
			}
		}
		return ans;
	}

	public static void main(String[] args) {

		int numsArr[] = { 2, 1, -2, -4, 4, 7, 8, 5 };
		int k = 4;
		int[] ans = maxSlidingWindow(numsArr, k);
		System.out.println(Arrays.toString(ans));
	}

}
