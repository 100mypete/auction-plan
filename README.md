# ReadME

##From Rohan

The code all seems to work but functionality is used more as a prototype
Timer is not functional but there is a variable that represents timing, instead to simulate the ending of an auction the makeBid method has a variable that if set to true ends the auction (and doesn't execute that bid) 
Created an Auction class and a User class
Returning 50% works but I had to make some interpretation of the design that didn't specify certain things
- When a person has lost and has more than 1 bid, I only return 50% of their top bid not all of the bids they made
- The 2nd place winner also receives 50% of their bid