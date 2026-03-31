// a program to find the youngest friends among 3 Amar, Akbar, and Anthony 
// based on their ages and the tallest among the friends based on their heights

import java.util.*;
public class YoungestTallestFriend {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // we are taking user input for ages
        System.out.println("Enter age of Amar, Akbar and Anthony: ");
        int ageOfAmar = scanner.nextInt();
        int ageOfAkbar = scanner.nextInt();
        int ageOfAnthony = scanner.nextInt();

        // we are taking user input for heights
        System.out.println("Enter height of Amar, Akbar and Anthony: ");
        int heightOfAmar = scanner.nextInt();
        int heightOfAkbar = scanner.nextInt();
        int heightOfAnthony = scanner.nextInt();

        // here we are finding the youngest friend
        if (ageOfAmar<ageOfAkbar && ageOfAmar<ageOfAnthony) {
            System.out.println("Amar is the youngest.");
        } else if (ageOfAkbar < ageOfAmar && ageOfAkbar < ageOfAnthony) {
            System.out.println("Akbar is the youngest.");
        } else if (ageOfAnthony < ageOfAmar && ageOfAnthony < ageOfAkbar) {
            System.out.println("Anthony is the youngest.");
        } else {
            System.out.println("Two friends are with same age.");
        }

        // here we are finding the tallest friend
        if (heightOfAmar>heightOfAkbar && heightOfAmar>heightOfAnthony) {
            System.out.println("Amer is the tallest friend");
        } else if (heightOfAkbar>heightOfAmar && heightOfAkbar>heightOfAnthony) {
            System.out.println("Akbar is the tallest friend");
        } else if (heightOfAnthony>heightOfAmar && heightOfAnthony>heightOfAkbar) {
            System.out.println("Anthony is the tallest frient");
        } else {
            System.out.println("Two friends are with same height");
        }

        scanner.close();
    }
}