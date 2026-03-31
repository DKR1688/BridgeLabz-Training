// a program to find the mean height of players present in a football team.

import java.util.Scanner;
public class MeanHeightOfPlayer {

    public static void main(String[] args) {
        Scanner scanner =new Scanner(System.in);
        double[] heights =new double[11];
        double sum = 0.0;

        // here we are taking input for the heights of 11 players
        System.out.println("Enter the heights of players in the football team ");
        for (int i=0; i<heights.length; i++) {
            heights[i] = scanner.nextDouble();
            sum+=heights[i];
        }

        // here we are calculating mean height
        double meanHeight = sum/heights.length;

        // printing the mean height of the football team
        System.out.println("Mean height of players is "+meanHeight);

        scanner.close();
    }
}