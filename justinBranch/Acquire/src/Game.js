/**
 * Created by Carolyn on 3/2/15.
 */

var Game,
    board,
    Rahoi,
    Imperial,
    Worldwide,
    Continental,
    American,
    Festival,
    Tower,
    whosTurn,
    gameInPlay = true,
    players


/* Constructor */
function Game(x ,y) {

    board = new Board(x, y);
    players = getPlayers();
    var firstPlayer = whoIsFirst(board);
    whosTurn = firstPlayer;

    console(players[firstPlayer].name + " goes first!");

    players = rearrangePlayerSequence(firstPlayer);

    drawStartingTiles(board);


    initCompanies();

    while(gameInPlay) {
        getMove(whosTurn, board);
        whosTurn++;
        whosTurn = whosTurn % numPlayers;
    }

}

/*
Game.prototype.start = function ();
this.status = "playing";
//create board
//hand out tiles
shuffle tiles
ccreate Companies
determine order

module.exports = {
 return new Game :function(params)

}
*/

/* Look underscore */
function getMove(playerIndex, board) {

    var player = players[playerIndex];
    var playable, choice = 0;
    board.print();

    console("Player: " + player.name);

    player.showHand();
    choice = prompt("Which tile would you like to place?");
    choice--;

    playable = playTile(player.hand[choice]);
    console("Playing: " + player.hand[choice].toString());

    if(playable) {
        console(player.name + " successfully placed down tile "
        + player.hand[choice].toString());
        checkAdjaceny(player.hand[choice], player.hand[choice].row,
            player.hand[choice].col);
        var newTile = board.bagPop();
        player.hand[choice] = newTile;
    }




}

function checkAdjacency(tile, x, y) {

    board.checkBoundaries(tile, x, y);

    if(tile.top)    checkHelper(x-1, y);
    if(tile.left)   checkHelper(tile, x, y-1);;
    if(tile.bottom) checkHelper(tile, x+1, y);
    if(tile.right)  checkHelper(tile, x, y+1);
}

function checkHelper(tile, x, y) {

    var company;
    var results;
    var tile2 = board.getTile(x, y);
    if(tile.status == "ONBOARD)") {
        if(tile.ownerCompany == null) {
            results = selectCompany(tile, tile2);
        }
        else { /* Fix this */
            company = tile2.ownerCompany;
            company.addTile(tile2);
        }
    }

}

/* function checkAdjacency helper */
/* Make a helper that slims down the checkAdjacency function */

function playTile(tileInPlay) {

    if(tile.status == "INHAND") {
        //tileInPlay.ownerPlayer(players[whosTurn]);
        tileInPlay.statusUpdate(2);
    }

}

function rearrangePlayerSequence(first) {
    var tempPlayers = new Players(players.length);

    for(var i = 0; i < tempPlayers.length; i++) {
        tempPlayers[i] = players[(first + i) % players.length]
    }
    return tempPlayers;

}

function drawStartingTiles() {

   for(var i = 0; i < players.length; i++) {
       player[i].numHand = 0;
       for(var j = 0; j < 6; j++) {

           var tempTile = board.bagPop();
           player[0].addHand(tempTile);
       }
   }

}

function whoIsFirst() {

    var plength = players.length;
    var tempTile;
    //arrayList??
    for(var i = 0; i < plength; i++) {
        tempTile = board.bagPop();
        playTile(tempTile);

        var pythag = Math.sqrt(tempTile.col * temTile.col
        + tempTile.row * tempTile * row) + tempTile.col;
        //arrayList?
    }

}



/**
 * Chooses which player will go first.
 * @param board
 * @return

private int whoIsFirst(Grid board) {
    int pLength = players.length;
    Tile tempTile;
    ArrayList<Double> distances = new ArrayList<Double>();

    for (int i = 0; i < pLength; i++) {
        tempTile = board.bagPop();
        playTile(tempTile);

        double pythag = Math.sqrt(tempTile.col * tempTile.col
        + tempTile.row * tempTile.row)
        + tempTile.col;
        distances.add(pythag);

        // System.out.print(players[i].name);
        // System.out.printf(" %.2f \n", distances.get(i));
    }
    return distances.indexOf(Collections.min(distances));
} */



/*

 */
function getPlayers() {

    var gate = false;
    var numPlayers;
    var name;
    while(!gate) {
        numPlayers = prompt("Enter the number of Platers (Max 6): ");
        if(numPlayers > 1 && numPlayers < 7) {
            gate = true;
        }

    }

    players = new Player[numPlayers];
    for(var i = 0; i < numPlayers; i++) {
        name = prompt("Name of Player: " + (i+1) + ": ");
        players[i] = new Player(name, board);
    }

}

/*/**
 * Initializes the list of players.
 * @return

private static Player[] getPlayers() {

    boolean gate = false;
    while (!gate) {
        System.out.print("Enter the number of Players (Max 6): ");
        numPlayers = scan.nextInt();
        if (numPlayers > 1 && numPlayers < 7) {
            gate = true;
        }
    }

    players = new Player[numPlayers];
    for (int i = 0; i < numPlayers; i++) {
        System.out.print("Name of player " + (i + 1) + ": ");
        String name = scan.next();
        players[i] = new Player(name, board);
    }

    return players;
}*/


/**
 * List the companies and their tiers.
 */
function companyList() {

    var string;
    console.log("Tier 1 Hotel Chains: \n\t")
    if(Rahoi.ownerPlayer == null) {
        string += "Rahoi, ";
    }
    if(Tower.ownerPlayer == null) {
        string += "Tower";
    }
    console.log(string + "\n\n");
    string = "";
    console.log("Tier 2 Hotel Chains: \n\t");
    if(American.ownerPlayer == null) {
        string += "American, ";
    }
    if(Worldwide.ownerPlayer == null) {
        string += "Wordwide, ";
    }
    if(Festival.ownerPlayer == null) {
        string += "Festival";
    }
    console.log(string + "\n\n");
    string = "";
    console.log("Tier 3 Hotel Chains: \n\t");
    if(Continental.ownerPlayer == null) {
        string += "Continental, "
    }
    if(Imperial.ownerPlayer == null) {
        string += "Imperial";
    }
    console.log(string + "\n\n");

}



function selectCompany(tile1, tile2) {
    var answer;
    var choice;
    answer = prompt("Would you like to list the companies and their tiers? yes or no");
    if(answer = "yes") {
        companyList();
    }
    choice = prompt(" 1: Rahoi, 2: Tower, 3: American, 4: Worldwide, 5: Festival, 6: Continental, 7: Imperial");
    switch (choice -1) {
        case 0:
            //setCompany(Rahoi, tile1, tile2);
            break;
        case 1:
            //setCompany(Tower, tile1, tile2);
            break;
        case 2:
            //setCompany(American, tile1, tile2);
            break;
        case 3:
            //setCompany(Worldwide, tile1, tile2);
            break;
        case 4:
            //setCompany(Festival, tile1, tile2);
            break;
        case 5:
            //setCompany(Continental, tile1, tile2);
            break;
        case 6:
            //setCompany(Imperial, tile1, tile2);
            break;
    }
}

function setCompany(Company, tile1, tile2) {

}

function initCompanies () {

    Rahoi = new Company("Rahoi", 0, 0, 0);
    Tower = new Company("Tower", 0, 0, 0);
    American = new Company("American", 1, 0, 0);
    Worldwide = new Company("WorldWide", 1, 0, 0);
    Festival = new Company("Festival", 1, 0, 0);
    Continental = new Company("Continental", 2, 0, 0);
    Imperial = new Company("Imperial", 2, 0, 0);

}