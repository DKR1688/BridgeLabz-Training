package OnlineAuctionBiddingSystem;

public class AuctionApp{
    public static void main(String[] args) {
        AuctionItem item = new AuctionItem("Vintage Watch");

        User u1 = new User(1, "Deepak");
        User u2 = new User(2, "Abhay");
        User u3 = new User(3, "Rajput");

        try {
            item.placeBid(u1, 100);
            item.placeBid(u2, 150);
            item.placeBid(u3, 120); // it is invalid,lower than highest
        } catch (InvalidBidException e) {
            System.out.println(e.getMessage());
        }

        item.showAllBids();

        Bid highest = item.getHighestBid();
        System.out.println("Highest bid: " + highest);
    }
}
