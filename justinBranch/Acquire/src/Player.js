/* Player */

var totalCash,			// cash owned by player
	name, 				// player name
	pid,				// game session unique id
	handsize = 6, 		// hand size for this game
	hand_array, 		// array of tiles for a players hand 
	companies_started,	// amount of companies started for stats 
	companies_merged, 	// amount of merges for stats
	number_of_turns,	// amount of turns taken
	money,				// ??? maybe a duplicate of totalCash?
	shares_list,		// shares owned by player 
	numHand = 0,		// number of Tiles in the hand
	my_turn, 			// boolean this player's turn
	my_merge_turn;		// boolean this player's merge turn

	function Player(name , board){
		this.name = name;
		this.totalCash = 6000;
		this.hand = hand;
		this.numHand = 0; 	// is this ambiguous to the above delcaration?
	}

	function addHand(tile){
		if(numHand >= handSize){
			console.log("Handsize exceeded");
			return;
		}
		tile.statusUpdate(1);
		hand[numHand] = tile; 
		this.numHand++;
	}

	function showHand() {
		for (var i = 0; i < hand.length; i++) {
			Console.log((i + 1) + ". " + String.fromCharCode(hand[i].row + 65) + ""
					+ (hand[i].col + 1));
		}
	}

	function updateHand(loc, tile) {
		hand[loc] = tile;
		numHand++;
	}

	function printTile(tile) {
		return (String.fromCharCode(tile.row + 65)+ "" + tile.col);
	}

	/**
	 * Do something with the board.
	 * 
	 * @param loc
	 *            - the location in the hand.
	 */
	function placeTile(loc) {
		var tile = hand[loc];
		tile.statusUpdate(2);
		hand[loc] = null;
		numHand--;
		return loc;
		// TODO: Might not have to return the location.
	}

		function stockAquisition() {
		var numStock = 0;
		while (numStock != 3) {
			buyStock();
		}
	}

	/**
	 * TODO: have a switch statement of stocks.
	 * 
	 * @return
	 */
	function buyStock() {
		return 0;
	}

	function printHand() {
		Console.log("Your hand: ");
		for( var i = 0; i < hand.length; i++){
			if (
                (!hand[i].getSubStatus() === "UNPLAYABLE")
				|| (!hand[i].getStatus() === "UNPLAYABLE")
                )
                {
					hand[i].toString();
				}
		}
		console.log("\n");
		console.log("Unplayable tiles in your hand: ");
		for (var i = 0; i < hand.length; i++){
			if (hand[i].getSubStatus() == "UPLAYABLE"){
				hand[i].toString();
			}
		}
	}








