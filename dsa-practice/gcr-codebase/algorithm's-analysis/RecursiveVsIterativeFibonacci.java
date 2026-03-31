import java.util.Scanner;

public class RecursiveVsIterativeFibonacci {
	//recursive fibonacci -O(2^N)
	static long fibonacciRecursive(int n) {
		if (n <= 1)
			return n;
		return fibonacciRecursive(n - 1) + fibonacciRecursive(n - 2);
	}

	//iterative fibonacci -O(N)
	static long fibonacciIterative(int n) {
		if (n <= 1)
			return n;

		long a = 0, b = 1, sum = 0;
		for (int i = 2; i <= n; i++) {
			sum = a + b;
			a = b;
			b = sum;
		}
		return b;
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();

		//Recursive approach
		long startRecursive = System.nanoTime();
		long recursiveResult = fibonacciRecursive(n);
		long endRecursive = System.nanoTime();

		//Iterative approach
		long startIterative = System.nanoTime();
		long iterativeResult = fibonacciIterative(n);
		long endIterative = System.nanoTime();

		System.out.println("\n--- Fibonacci Comparison ---");
		System.out.println("Recursive result is- " + recursiveResult);
		System.out.println("Recursive time is- " + (endRecursive - startRecursive) + " ns");

		System.out.println("Iterative result is- " + iterativeResult);
		System.out.println("Iterative time is- " + (endIterative - startIterative) + " ns");

		sc.close();
	}
}
