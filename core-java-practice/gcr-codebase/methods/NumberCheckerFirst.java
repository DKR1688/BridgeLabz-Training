package methods;

import java.util.*;
public class NumberCheckerFirst{
	public static void main(String[] args) {
		Scanner scanner=new Scanner(System.in);
		System.out.println("Enter the number ");
        int number =scanner.nextInt();

        int count = countDigits(number);
        System.out.println("Count of digits are "+count);

        int[] digits = getDigits(number);
        System.out.print("Digits of the array are ");
        for (int digit:digits) {
        	System.out.print(digit+ " ");
        }
        System.out.println();

        System.out.println("It is a duck number or not - " +isDuckNumber(digits));

        System.out.println("It is a armstrong number or not - " +isArmstrong(digits, number));

        int[] largestNumber = findLargestTwo(digits);
        System.out.println("Largest number is " + largestNumber[0]);
        System.out.println("Second largest number is " + largestNumber[1]);

        int[] smallestNumber = findSmallestTwo(digits);
        System.out.println("Smallest number is " +smallestNumber[0]);
        System.out.println("Second smallest number is " +smallestNumber[1]);
        scanner.close();
    }

    //Method to Find the count of digits in the number
    static int countDigits(int num) {
        return String.valueOf(num).length();
    }

    //Method to Store the digits of the number in a digits array
    static int[] getDigits(int num) {
        String string =String.valueOf(num);
        
        int[] digits =new int[string.length()];
        for (int i=0; i<string.length(); i++) {
            digits[i] = string.charAt(i)-'0';
        }
        return digits;
    }

    //Method to Check if a number is a duck number using the digits array. 
    //A duck number is a number that has a non-zero digit present in it
    public static boolean isDuckNumber(int[] digits) {
        if (digits[0] == 0) return false; // cannot start with 0
        for (int d : digits) {
            if (d == 0) return true;
        }
        return false;
    }

    //Method to check if the number is a armstrong number using the digits array. ​​Armstrong number is a number 
    //that is equal to the sum of its own digits raised to the power of the number of digits. Eg: 153 = 1^3 + 5^3 + 3^3
    static boolean isArmstrong(int[] digits, int num) {
        int n=digits.length;
        int sum=0;
        for (int digit :digits) {
            sum += Math.pow(digit, n);
        }
        return sum==num;
    }

    //Method to find the largest and second largest elements in the digits array. 
    //Use Integer.MIN_VALUE to initialize the variable.
    static int[] findLargestTwo(int[] digits) {
        int largest = Integer.MIN_VALUE;
        int secondLargest = Integer.MIN_VALUE;

        for (int digit : digits) {
            if (digit>largest) {
                secondLargest = largest;
                largest =digit;
            } else if (digit>secondLargest && digit!=largest) {
                secondLargest=digit;
            }
        }
        return new int[]{largest, secondLargest};
    }

    //Method to find the the smallest and second smallest elements in the digits array. 
    //Use Integer.MAX_VALUE to initialize the variable
    public static int[] findSmallestTwo(int[] digits) {
        int smallest = Integer.MAX_VALUE;
        int secondSmallest = Integer.MAX_VALUE;

        for (int digit : digits) {
            if (digit<smallest) {
                secondSmallest = smallest;
                smallest = digit;
            } else if (digit<secondSmallest && digit!=smallest) {
                secondSmallest = digit;
            }
        }
        return new int[]{smallest, secondSmallest};
    }
}