// a program to find the youngest friends among 3 Amar, Akbar, and Anthony 
// based on their ages and the tallest among the friends based on their heights

import java.util.Scanner;

public class YoungestTallestFriend{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] names = {"Amar", "Akbar", "Anthony"};

        int[] agesOfFriends = new int[3];
        int[] heightsOfFriends = new int[3];

        for (int i=0; i<3; i++) {
            System.out.print("Enter age of "+names[i]+" ");
            agesOfFriends[i] = scanner.nextInt();

            System.out.print("Enter height of "+names[i]+ " ");
            heightsOfFriends[i] = scanner.nextInt();
        }

        // here we are finding youngest friend
        int minAge = 0;
        for (int i=1; i<3; i++) {
            if (agesOfFriends[i]<agesOfFriends[minAge]) {
                minAge = i;
            }
        }

        // ihere we are findig tallest friend
        int maxHeight= 0;
        for (int i=1; i<3; i++) {
            if (heightsOfFriends[i]>heightsOfFriends[maxHeight]) {
                maxHeight= i;
            }
        }

        System.out.println("Youngest friend is " + names[minAge]);
        System.out.println("Tallest friend is " + names[maxHeight]);
        
        scanner.close();
    }
}