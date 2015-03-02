/* Player Node */

var playerName, 	// name for player who owns shares
	shareCount;		// amount of shares owned by player

function playerNode(playerName, shareCount){
		this.playerName = playerName;
		this.shareCount = shareCount;
	}

/* compareTo should return < 0 if this is supposed to be
	less than other, > 0 if this is supposed to be greater than
	other and 0 if they are supposed to be equal 
	*/
function compareTo(other){
	var last = other.shareCount.compareTo(this.shareCount);
	return last === 0 ? 
		this.playerName.compareTo(other.playerName) : last;
}