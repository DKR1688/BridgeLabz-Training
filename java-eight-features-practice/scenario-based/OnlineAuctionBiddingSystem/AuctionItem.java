package OnlineAuctionBiddingSystem;

import java.util.*;
public class AuctionItem {
	private String itemName;
	private TreeMap<Double, User> bids; // Key=BidAmount, Value=User

	public AuctionItem(String itemName) {
		this.itemName = itemName;
		//treeMap sorts by key (bid amount)
		bids = new TreeMap<>();
	}

	public void placeBid(User user, double amount) throws InvalidBidException {
		if (!bids.isEmpty() && amount <= bids.lastKey()) {
			throw new InvalidBidException("Bid must be higher than current highest bid!");
		}
		bids.put(amount, user);
		System.out.println(user.getName() + " placed a bid of $" + amount);
	}

	public Bid getHighestBid() {
		if (bids.isEmpty())
			return null;
		double highestAmount = bids.lastKey();
		User highestUser = bids.get(highestAmount);
		return new Bid(highestUser, highestAmount);
	}

	public void showAllBids() {
		System.out.println("Bids for " + itemName + ":");
		for (Map.Entry<Double, User> entry : bids.entrySet()) {
			System.out.println(entry.getValue().getName() + " -> $" + entry.getKey());
		}
	}
}
