/* Company */

var CID, 				// Company ID	
	companyName,		// name of company
	companySize,		// size of company
	Tiles,			    // var to occupy companyTiles, not sure if this is needed or not
	companyTiles,// list of all tiles owned by company
	companyColor,		// color. will implement later
	playerNode,			// playerNode object for shareHolders
	shareHolders, // list of players who own stock
	companyTier,		// pay tier for company, 0 to 2
	isSafe,				// boolean is tile safe to play?
	gameEndable,		// boolean is game endable 
	onBoard;			// boolean is company in active play?

	function Company(companyName, companyTier, companySize, occurence){
		this.companyName = companyName;
		this.companyTier = companyTier;
		this.companySize = companySize;
		this.CID = occurence;
		isSafe = false;
		gameEndable = false;
		onBoard = false;
	}

	function increment_size(){
		companySize ++;
		if (companySize === Main.getSafeSize()) {
			isSafe = true;
		}
	}

	function insertShareHolder(name, shareCount) {
		shareHolders.add( playerNode[name] );
		playerNode.name.shareCount = shareCount;
	}

	//TODO: make sorting comparator like so:
	/*
		function(a, b){return a-b}
	*/
	function getMajority() {
		shareHolders.sort(comparator());
		return shareHolders[0].playerName;
	}

	function getMinority() {
        Collections.sort(comparator());
        return shareHolders[1].playerName;
    }
        function dissolve() {
            onboard = false;
            isSafe = false;
            gameEndable = false;
            companySize = 2;
        }

    function addTile(tile) {
            companyTiles.add(tile);
    }


