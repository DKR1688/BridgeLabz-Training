package methods;

import java.util.*;
public class YoungestAndTallestFriends {
	public static void main(String[] args) {
        Scanner scanner= new Scanner(System.in);

        String[] nameOfFriends = {"Amar", "Akbar", "Anthony"};

        int[] ageOfFriends = new int[3];
        int[] heightOfFriends= new int[3];

        for (int i=0; i<3; i++) {
            System.out.print("Enter age of "+nameOfFriends[i]+ " ");
            ageOfFriends[i] = scanner.nextInt();

            System.out.print("Enter height of " +nameOfFriends[i] + ": ");
            heightOfFriends[i] = scanner.nextInt();
        }
        System.out.println(findYoungest(nameOfFriends, ageOfFriends));
        System.out.println(findTallest(nameOfFriends, heightOfFriends));
        scanner.close();
    }

    //a Method to find the youngest of the 3 friends
    static String findYoungest(String[] names, int[] ages) {
        int minAge = ages[0];
        int index=0;
        for (int i=1; i<ages.length; i++) {
            if (ages[i]<minAge) {
                minAge=ages[i];
                index=i;
            }
        }
        return names[index] + " is the youngest";
    }

    //a Method to find the tallest of the 3 friends
    public static String findTallest(String[] names, int[] heights) {
        int maxHeight = heights[0];
        int index = 0;
        for (int i=1; i<heights.length; i++) {
            if (heights[i]>maxHeight) {
                maxHeight=heights[i];
                index = i;
            }
        }
        return names[index] + " is the tallest";
    }
}