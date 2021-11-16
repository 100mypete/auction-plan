
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

    constructor(address _winner) {
        seller = payable(msg.sender);
        
        winner = _winner;
    }
    
    function setNFT(address _nft, uint _nftId) public{
        nft = IERC721(_nft);
        nftId = _nftId;
    }

    // Winds the auction for the specified amount
    function win() external payable {
        
        nft.safeTransferFrom(seller, winner, nftId);
        seller.transfer(msg.value);

        emit Win(winner, amount);
    }
}