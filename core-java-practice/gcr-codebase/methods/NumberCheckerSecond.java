package methods;

import java.util.*;
public class NumberCheckerSecond{
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

        System.out.println("Sum of digits is " +sumOfDigits(digits));

        System.out.println("Sum of squares of digits is " +sumOfSquares(digits));
        
        System.out.println("It is a harshad number or not - " +isHarshad(number, digits));

        int[][] freq = digitFrequency(digits);
        System.out.println("Digit frequencies are ");
        for (int i=0; i<freq.length; i++) {
            if (freq[i][1]>0) {
                System.out.println("Digit " +freq[i][0]+ " => " +freq[i][1]+ " times");
            }
        }
        scanner.close();
    }

    //Method to Find the count of digits in the number
    static int countDigits(int num) {
        return String.valueOf(num).length();
    }

    static int[] getDigits(int num) {
        String string =String.valueOf(num);
        
        int[] digits =new int[string.length()];
        for (int i=0; i<string.length(); i++) {
            digits[i] = string.charAt(i)-'0';
        }
        return digits;
    }

    //Method to find the sum of the digits of a number using the digits array
    static int sumOfDigits(int[] digits) {
    	int sum = 0;
    	for (int digit : digits) {
    		sum +=digit;
    	}
    	return sum;
    }
    
  //Method to find the sum of the squares of the digits of a number using the digits array. Use Math.pow() method
    public static int sumOfSquares(int[] digits) {
        int sum = 0;
        for (int digit : digits) {
            sum += Math.pow(digit, 2);
        }
        return sum;
    }

    	
    //Method to Check if a number is a harshad number using a digits array. A number is called a Harshad number 
    //if it is divisible by the sum of its digits. For e.g. 21
    static boolean isHarshad(int num, int[] digits) {
        int sum =sumOfDigits(digits);
        return num % sum == 0;
    }

   //Method to find the frequency of each digit in the number. Create a 2D array to store the frequency 
    //with digit in the first column and frequency in the second column.
    static int[][] digitFrequency(int[] digits) {
        int[][] freq = new int[10][2];
        for (int i=0; i<10; i++) {
            freq[i][0] = i;
            freq[i][1] = 0;
        }
        for (int d : digits) {
            freq[d][1]++;
        }
        return freq;
     }
}