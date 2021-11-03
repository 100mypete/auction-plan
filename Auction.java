
import java.util.HashMap;
import java.util.UUID;

public class Auction{
	private String auctionID;
	private String description; //description of the item being sold
	private User seller; 
    private Double minBid; 
    private User highestBidder;
    private Double highestBidAmount;
    private User secondBidder;
    private int auctionTime; // time in hours
    private boolean isAlive; //is this auction still ongoing? 
    private HashMap<User, Double> bidList = new HashMap<User, Double>(); //this has the list of all bids in the auction, Which User bidded is the key and amount they bidded is the value

    public Auction(User seller, Double minBid, String description){
    	this.seller = seller; 
        this.minBid = minBid;
        this.description = description;
        this.auctionID = generateAuctionID(); //makes a random ID for the auction
        this.highestBidder = null;
        this.highestBidAmount = 0.0;
        this.secondBidder = null;
        this.auctionTime = 24; //represents 24 hours
        this.isAlive = true;
       
    }
    
    public String generateAuctionID() {
    	return UUID.randomUUID().toString();
    }
    
    public void makeBid(User bidder, Double amount, boolean shouldEnd) { //when a user attempts to make a bid
    	this.checkIfEnd(shouldEnd);
    	
    	if(isAlive) { //only execute if the auction is still going on
    		double balance = bidder.getBalance();
        	if(amount > balance) { //not enough money in user's balance
        		System.out.println("Not enough money in " + bidder.getUsername() +  "'s balance for a bid of " + amount + " amount!" );
        	}
        	else if(amount <= highestBidAmount) { //the bid isn't higher than the highest bid
        		System.out.println("Your bid must be higher than the current highest bid: " + highestBidAmount);
        	}
        	else if(amount < minBid) { //the bid isnt higher than the minimum bid amount
        		System.out.println("The first bid must be higher than the minimum bid amount: " + minBid);
        	}
        	else if(bidder.equals(seller)) {
        		System.out.println("The seller cannot bid on their own auction!");
        	}
        	else {
        		//do the bid
        		this.bidList.put(bidder, amount); //adds the sucessful bid to the list of bids
        		this.secondBidder = highestBidder; //makes the old highest = to the second highest
        		this.highestBidder = bidder; //makes the newest bidder = to the highest bidder
        		this.highestBidAmount = amount; //updates the highest bid amount
        		
        		HashMap<String, Double> investments = bidder.getInvestments();
        		investments.put(auctionID, amount); //updates their investment for this auction
        		this.auctionTime +=1; //increments the time by 1 hour for adding a bid
        			
        		}
        	}
    	}
   
    
    
    public void endAuction() { //runs when the auction is ended
    	
    	this.isAlive = false; 
    	
    	if(bidList.size() == 0) {
    		System.out.println("The auction has ended. Nobody has bid.");
    	}
    	if(bidList.size() == 1) {
    		System.out.println("The user " + highestBidder.getUsername() + " has won this auction!");
    		highestBidder.subtractBalance(highestBidAmount);
    		this.seller.addSuccessfullAuctions(); //increments their successful auctions by 1 as reward for completing auction
    	}
    	else {
    		for(User user: bidList.keySet()) {
        		HashMap<String, Double> investments = user.getInvestments();
        		Double highestInvestment = investments.get(auctionID);
        		
        		if(user.equals(highestBidder)) {
        			user.subtractBalance(highestInvestment); //subtracts the amount bidded by the highest bidder
        		} 
        		else {
        			user.addBalance(0.5 * highestInvestment); //adds 50% of their investment to their balance
        			if(user.equals(secondBidder)) {
            			//send them their item
        				System.out.println("The user " + secondBidder.getUsername() + " has won the auction!");
            		}
        		}
        		investments.remove(auctionID); //removes the auction from the investment list
        	}
    		
    		this.seller.addSuccessfullAuctions(); //increments their successful auctions by 1 as reward for completing auction
    	}
    	
    }
    
    public void checkIfEnd(boolean shouldEnd) { //checks to see if the auction should end
    	if(shouldEnd) { //if the auction should end then run the endAuction method
    		this.endAuction();
    	}
    	
    }

}
