
public class AuctionTester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		User harold = new User(10.0, "harry");
		
		User richard = new User(5.0, "richy");
		
		User rohan = new User(20.0, "roro");
		
		User girl = new User(15.5, "girly");
		
		Auction bike = new Auction(richard, 3.0, "this is a bike");
				
		bike.makeBid(harold, 3.0, false);
		bike.makeBid(harold, 4.0, false);
		bike.makeBid(richard, 4.5, false);
		bike.makeBid(rohan, 2.0, false);
		
		
		Auction wheel = new Auction(girl, 2.0, "this is a wheel lol");
		
		wheel.makeBid(richard, 1.0, false);
		wheel.makeBid(richard, 2.0, false);
		
		wheel.makeBid(rohan, 3.0, false);
		wheel.makeBid(harold, 5.0, false);
		wheel.makeBid(richard, 0.0, true);
		
		System.out.println(richard.getBalance());
		System.out.println(harold.getBalance());
		System.out.println(rohan.getBalance());
		
		bike.makeBid(girl, 4.5, false);
		bike.makeBid(rohan, 5.0, false);
		
		bike.makeBid(girl, 0.0, true); // ends the auction
		
		System.out.println(harold.getBalance());
		System.out.println(richard.getsuccessfullAuctions());
		System.out.println(rohan.getBalance());
		System.out.println(girl.getBalance());
		
	
	}

}
