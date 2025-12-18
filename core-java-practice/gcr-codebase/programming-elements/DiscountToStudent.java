public class DiscountToStudent {
    public static void main(String[] args) {
        int fee  = 125000;
        int discountPercent  = 10;
        int dicount = (fee * discountPercent) / 100;

        // calculating remaining fee after discount
        int remainingFee = fee - dicount;
        System.out.println("The fee you have to pay by subtracting the discount from the fee: " + remainingFee);
    }
}
