import java.util.HashMap;
import java.util.UUID;

public class User {
	private Double balance; 
	private String walletID;
	private String username;
	private HashMap<String, Double> investments; //has the highest bids that they contain in every auction they're in <Auction ID they bidded in, Highest Amount they have bidded in that>
	private int successfullAuctions; 
	
	
    public User(Double initialBalance, String username){
        this.balance = initialBalance;
        this.username = username;
        this.walletID = this.generateWalletID();
        this.investments = new HashMap<String, Double>();
        this.successfullAuctions = 0;
    }
    
    public String generateWalletID() {
    	return UUID.randomUUID().toString();
    }
    
    public double getBalance() {
    	return balance;
    }
    
    public String getWalletID() {
    	return walletID;
    }
    
    public String getUsername() {
    	return username;
    }
    
    public void subtractBalance(Double amount) {
    	this.balance -= amount; 
    }
    
    public void addBalance(Double amount) {
    	this.balance += amount; 
    }
    
    public HashMap<String, Double> getInvestments(){
    	return investments;
    }
    
    public int getsuccessfullAuctions() {
    	return successfullAuctions;
    }
    
    public void addSuccessfullAuctions() {
    	successfullAuctions+=1;
    }
    
}
