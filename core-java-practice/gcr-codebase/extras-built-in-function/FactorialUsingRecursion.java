import java.util.*;
public class FactorialUsingRecursion {
	public static void main(String[] args) {
        int num = userInput();
        long ans = factorial(num);
        printFactorial(num, ans);
    }
	//this function is to take input
    static int userInput() {
        Scanner scanner= new Scanner(System.in);
        System.out.print("Enter the number - ");
        int number = scanner.nextInt();
        return number;
    }

    // this function is to calculate factorial
    static long factorial(int n) {
        if (n==0 || n==1) {
            return 1;
        } else {
            return n * factorial(n-1);
        }
    }

    static void printFactorial(int num, long ans) {
        System.out.println("Factorial of "+num+" is - "+ans);
    }
}
