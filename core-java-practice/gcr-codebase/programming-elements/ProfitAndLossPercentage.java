public class ProfitAndLossPercentage {
    public static void main(String[] args) {
        int costPrice = 129;
        int sellingPrice = 191;

        int profit = sellingPrice - costPrice;
        int profitPercentage = (profit*100 / costPrice);
        
        //a single print statement to display multiline and varible
        System.out.println("The cost price is " + costPrice+" and selling price is: " + sellingPrice+"\n"+ "Profit Percentage is: " + profitPercentage + "%");
    }
}