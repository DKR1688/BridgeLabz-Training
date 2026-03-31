/*Festival Lucky Draw 🎉
At Diwali mela, each visitor draws a number.
● If the number is divisible by 3 and 5, they win a gift.
● Use if, modulus, and loop for multiple visitors.
● continue if input is invalid.
*/

import java.util.*;
public class FestivalLuckyDraw {
	public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        System.out.print("Enter the number of visitor ");
        int visitorsInFestival =scanner.nextInt();

        for (int i=1; i<=visitorsInFestival; i++) {
            System.out.print("Visitor "+i+", enter your lucky draw number ");
            int number =scanner.nextInt();

            if (number<=0) {
                System.out.println("Invalid nuber to lucky draw.");
                // skip for next visitors
                continue;
            }

            // Checking divisibility by 3 and 5 to win gift
            if (number%3 == 0 && number%5 == 0) {
                System.out.println("Congratulations visitor "+i+"! You have won a gift!");
            } else {
                System.out.println("Sorry, Better luck next time, Visitor "+i);
            }
        }
        scanner.close();
    }
}
