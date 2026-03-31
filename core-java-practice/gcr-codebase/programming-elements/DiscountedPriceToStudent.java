// a program to find the discounted amount and discounted price 
// the student will pay for the course.

public class DiscountedPriceToStudent {
    public static void main(String[] args) {
        int fee  = 125000;
        int discountPercent  = 10;
        int dicount = (fee * discountPercent) / 100;

        // calculating remaining fee after discount
        int remainingFee = fee - dicount;
        System.out.println("The discount amount is INR "+dicount+" and final discounted fee is: " + remainingFee);
    }
}
