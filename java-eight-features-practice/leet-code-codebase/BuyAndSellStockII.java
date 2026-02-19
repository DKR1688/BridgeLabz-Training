
public class BuyAndSellStockII {
	public int maxProfit(int[] prices) {
        int buy=prices[0];
        int profit=0;
        for(int i=0; i<prices.length; i++){
            if(buy>prices[i]){
                buy=prices[i];
            }else if(buy<prices[i]){
                profit=profit+(prices[i]-buy);
                buy=prices[i];
            }
        }
        return profit;
    }
	
	public static void main(String[] args) {
        BuyAndSellStockII obj=new BuyAndSellStockII();

        int[] prices = {7, 1, 5, 3, 6, 4};
        int result = obj.maxProfit(prices);

        System.out.println("Maximum profit- "+result);
    }
}