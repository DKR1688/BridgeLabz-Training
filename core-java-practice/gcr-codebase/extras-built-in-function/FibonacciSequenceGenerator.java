import java.util.*;
public class FibonacciSequenceGenerator {
	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of terms - ");
        int terms =scanner.nextInt();

        generateFibonacci(terms);
        scanner.close();
    }

    // this function is to generate Fibonacci sequence
    public static void generateFibonacci(int terms) {
        int first=0;
        int second=1;
        
        if (terms<=0) {
            System.out.println("Enter a positive integer greater than 0.");
        }

        System.out.print("Fibonacci Sequence is - ");
        for (int i=1; i<=terms; i++) {
            System.out.print(first + " ");
            int next = first+second;
            first = second;
            second = next;
        }
    }
}