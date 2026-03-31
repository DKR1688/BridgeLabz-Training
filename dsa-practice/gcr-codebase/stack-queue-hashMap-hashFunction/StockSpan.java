import java.util.*;

public class StockSpan {

	public static int[] calculateSpan(int prices[]) {
		int span[] = new int[prices.length];
		Stack<Integer> stack = new Stack<>();
		for (int i = 0; i < prices.length; i++) {
			while (!stack.isEmpty() && prices[stack.peek()] <= prices[i]) {
				stack.pop();
			}
			if (stack.isEmpty()) {
				span[i] = i + 1;
			} else {
				span[i] = i - stack.peek();
			}
			stack.push(i);
		}
		return span;
	}

	public static void main(String[] args) {

		int prices[] = { 50, 60, 10, 70, 80, 85 };
		int span[] = calculateSpan(prices);
		for (int i = 0; i < span.length; i++) {
			System.out.print(span[i] + " ");
		}
	}

}
