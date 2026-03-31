import java.util.*;
public class GcdAndLcmCalculator {
	public static void main(String[] args) {
        int[] numbers =userInput();
        int num1 = numbers[0];
        int num2 = numbers[1];

        int gcd =getGCD(num1, num2);
        int lcm =getLCM(num1, num2);

        printGcdLcm(gcd, lcm);
    }
	
	//this function is to take input from user
    static int[] userInput() {
        Scanner scanner= new Scanner(System.in);
        System.out.print("Enter first and second number: ");
        int num1 = scanner.nextInt();
        int num2 = scanner.nextInt();
        return new int[]{num1, num2};
    }

    //this function is to calculate GCD
    static int getGCD(int num1, int num2) {
        while (num2!=0) {
            int temp = num2;
            num2 = num1%num2;
            num1 = temp;
        }
        return num1;
    }

    //this function is to calculate LCM using GCD
    static int getLCM(int num1, int num2) {
        return (num1*num2)/getGCD(num1, num2);
    }

    static void printGcdLcm(int gcd, int lcm) {
        System.out.println("GCD of given two numbers - " +gcd);
        System.out.println("LCM of given two numbers - " +lcm);
    }
}
