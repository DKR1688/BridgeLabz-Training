package methods;

import java.util.*;
public class RandomNumbers {
	public static void main(String[] args) {
        RandomNumbers rNumber = new RandomNumbers();
        
        int[] randomNumbers = rNumber.generate4DigitRandomArray(5);
        System.out.println("Generated numbers are " + Arrays.toString(randomNumbers));

        double[] ans= rNumber.findAverageMinMax(randomNumbers);
        System.out.println("Average of numbers are " +ans[0]);
        System.out.println("Minimum of numbers are " +ans[1]);
        System.out.println("Maximum of numbers are " +ans[2]);
    }

    //a method that generates array of 4 digit random numbers given the size as a parameter as shown in the method signature 
	//public int[] generate4DigitRandomArray(int size)
    public int[] generate4DigitRandomArray(int size) {
        int[] numbers = new int[size];
        for (int i=0; i<size; i++) {
            numbers[i] = (int)(Math.random()*9000)+1000;
        }
        return numbers;
    }

    // a method to find average, min and max value of an array 
    //public double[] findAverageMinMax(int[] numbers) 
    public double[] findAverageMinMax(int[] numbers) {
        int min =numbers[0];
        int max =numbers[0];
        int sum =0;

        for (int num:numbers) {
            sum += num;
            min = Math.min(min, num);
            max = Math.max(max, num);
        }
        double average = (double) sum/numbers.length;
        return new double[]{average, min, max};
    }
}