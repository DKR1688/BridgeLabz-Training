package methods;

import java.util.*;
public class ShortestTallestAndMean {
	public static void main(String[] args) {
		
		//an int array named heights of size 11 and 
		//get 3 digits random height in cms for each player in the range 150 cms to 250 cms
        Random random =new Random();
        int[] heightsOfPlayer = new int[11];
        for (int i=0; i<heightsOfPlayer.length; i++) {
            heightsOfPlayer[i] = random.nextInt(101)+150;
        }

        System.out.println("Player heights are ");
        for (int h : heightsOfPlayer) {
            System.out.println(h+ " ");
        }

        int sum = findSum(heightsOfPlayer);
        double mean = findMean(heightsOfPlayer);
        int shortest = findShortest(heightsOfPlayer);
        int tallest = findTallest(heightsOfPlayer);

        System.out.println("Sum of heights is " +sum);
        System.out.println("Mean height is " + mean);
        System.out.println("Shortest height is " + shortest);
        System.out.println("Tallest height is " + tallest);
    }
	
	//method to Find the sum of all the elements present in the array.
    static int findSum(int[] heights) {
        int sum=0;
        for (int h : heights) {
            sum += h;
        }
        return sum;
    }

    //method to find the mean height of the players on the football team
    static double findMean(int[] heights) {
        int sum=findSum(heights);
        return (double) sum / heights.length;
    }

    //method to find the shortest height of the players on the football team
    public static int findShortest(int[] heights) {
        int min=heights[0];
        for (int h : heights) {
            if (h<min) {
                min = h;
            }
        }
        return min;
    }

    //method to find the tallest height of the players on the football team
    public static int findTallest(int[] heights) {
        int max=heights[0];
        for (int h : heights) {
            if (h>max) {
                max = h;
            }
        }
        return max;
    }
}
