/* Company */

var CID, 				// Company ID	
	companyName,
	companySize,
	Tiles,
	companyTiles[Tiles],
	companyColor,
	playerNode,
	shareHolders[playerNode],
	companyTier,
	isSafe,
	gameEndable,
	onBoard;

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
	
	function dissolve(){
		onboard = false;
		isSafe = false;
		gameEndable = false;
		companySize = 2;
	}

	function addTile(tile){
		companyTiles.add(tile);
	}

