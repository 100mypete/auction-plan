// SPDX-License-Identifier: GPL-3.0


pragma solidity >=0.8.9;
import "WinAuction.sol";

contract Auction{
    uint256 highestBidAmount;
    address secondHighest;
    address highest;
    uint256 minBidAmount;
    address seller;
    bool isAlive; //false if the auction is over
    uint256 auctionTime; 
    IERC721 public nft;
    uint256 public  nftID;
    
     struct User {
        uint256 balance;
        address add;
        string username;
        uint256 successfullAuctions;
    }
    
    mapping (address => uint256) bidList; //list of all bids Key: address, Value: bid amount (only list each users highest bid)
    address [] biddersList; //list of all bidders
    


    constructor(){
        seller = msg.sender;
        isAlive = true; 
        
    }
    
    function makeNFT(address _nft, uint256 _nftID) public{
        require(msg.sender == seller, "Only the seller can set the NFT");
        nft = IERC721(_nft);
        nftID = _nftID;
    }
    
    function setMinBidAmount(uint256 _minBidAmount) public{
        require(msg.sender == seller, "Only the seller can set the minimum bid brice");
        minBidAmount = _minBidAmount;
    }
    
    function setAuctionTime(uint256 _auctionTime) public{
        require(msg.sender == seller, "Only the seller can set the auction time");
        auctionTime = _auctionTime;
    }
    
    function makeBid(uint256 amount) public checkIfPossibleBid (amount){
       
        //
        
        bidList[msg.sender] = amount; 
        if(!containsBidder(msg.sender)){
            biddersList.push(msg.sender);
        }
        
        
        
        auctionTime+=1;
        
        updateHighests(amount);
        //take money from them
        
    }
    
    //function  transferEther (uint256 _amount) public{
        //ddress(this).transfer(_amount);
    //
    
    function containsBidder(address bidder) public view returns (bool){
        for(uint i = 0; i<biddersList.length; i++){
            if(bidder == biddersList[i]){
                return true;
            }
        }
        return false; 
    }
    
    function updateHighests(uint256 amount) internal{
         
         secondHighest = highest;
         highest = msg.sender;
         highestBidAmount = amount; 
         
    }
    
    modifier checkIfPossibleBid(uint256 amount) {
        address bidder = msg.sender;
        uint256 balance = bidder.balance;
        uint256 weiAmount = amount * 1000000000000000000;
        require(isAlive == true, "You cannot bid on this auction because it has finished.");
        //require(weiAmount < balance, "Not enough money to make this bid");
        require(amount > highestBidAmount, "Your bid must be higher than the current highest bid");
        require(amount > minBidAmount, "The first bid must be higher than the minimum bid amount");
        require(bidder != seller, "The seller cannot bid on their own auction");
        _;
        
        
    }
    
    function endAuction() public payable{
        require(msg.sender == seller, "Cannot end the auction. Only the seller can end the auction.");
        isAlive = false; 
        if(biddersList.length == 0){
            //do nothing
        }
        else{
            //address payable sellerPayable = payable(seller);
           //WinAuction win = new WinAuction(highest);
           //win.win();
           
           //winAuction(highest);
           address winner = highest;
           
            nft.safeTransferFrom(address(this), winner, nftID);
    		

           
        }//address(this) = contract adress, i can transfer 
       //i would like to implement people receiving 50% of their bids back and my code is set up it is possible,
       //I am just not sure if its logistically possible because theres no way for people to just gain money without receiving from someone
    }
    
    function winAuction(address winner) internal{
        
        nft.safeTransferFrom(address(this), winner, nftID);
		nft.approve(winner, nftID);
		address payable sellerPayable = payable(seller);
	    sellerPayable.transfer(msg.value);
        //WinAuction win = new WinAuction(winner);
      
        //win.win();
       
    }
    
}