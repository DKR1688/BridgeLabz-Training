import java.util.Scanner;
public class UniversityFee {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int fee = scanner.nextInt();

        int discountedPercentage = scanner.nextInt();
        int discount = (fee * discountedPercentage) / 100;

        int remainingFee = fee - discount;
        System.out.println("The discount amount is "+discount+" and the remaining fee is "+remainingFee);
        scanner.close();
    }
}
