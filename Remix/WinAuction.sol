
// SPDX-License-Identifier: GPL-3.0
// OpenZeppelin Contracts v4.3.2 (token/ERC721/ERC721.sol)

pragma solidity >=0.8.9;


import "@openzeppelin/contracts/token/ERC721/IERC721.sol";




contract WinAuction {
    
    event Win(address winner, uint256 amount);

    IERC721 public nft;
    uint public nftId;
    uint public amount; 

    address payable public seller;
    address public winner;

    constructor(
        address _nft,
        uint _nftId,
        uint256 _amount,
        address payable _seller,
        address _winner
    ) {
        seller = payable(msg.sender);
        nft = IERC721(_nft);
        nftId = _nftId;
        amount = _amount;
        seller = _seller;
        winner = _winner;
    }

    // Winds the auction for the specified amount
    function win() external payable {
        
        nft.safeTransferFrom(seller, winner, nftId);
        seller.transfer(amount);

        emit Win(winner, amount);
    }
}