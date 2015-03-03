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

    var compare;    //The compare tile
    var results;
    var company;
    board.checkBoundaries(tile, x, y);

    if(tile.top) {
        compare = board.getTile(x, y-1);
        if(compare.status == "ONBOARD") {
            if(compare.company == null) {
                results = selectCompany(tile, compare);
                console(results);
            }
            else {
                company = compare.company;
                company.add(compare);
            }
        }
    }
    if(tile.right) {
        compare = board.getTile(x, y-1);
        if(compare.status == "ONBOARD") {
            if(compare.company == null) {
                results = selectCompany(tile, compare);
                console(results);
            }
            else {
                company = compare.company;
                company.add(compare);
            }
        }
    }
    if(tile.bottom) {
        compare = board.getTile(x, y-1);
        if(compare.status == "ONBOARD") {
            if(compare.company == null) {
                results = selectCompany(tile, compare);
                console(results);
            }
            else {
                company = compare.company;
                company.add(compare);
            }
        }
    }
    if(tile.left) {
        compare = board.getTile(x, y-1);
        if(compare.status == "ONBOARD") {
            if(compare.company == null) {
                results = selectCompany(tile, compare);
                console(results);
            }
            else {
                company = compare.company;
                company.add(compare);
            }
        }
    }
}

/* function checkAdjacency helper */
/* Make a helper that slims down the checkAdjacency function */

function playTile() {

}

function rearrangePlayerSequence() {

}

function drawStartingTiles() {

}

function whoIsFirst() {

}


/*

 */
function getPlayers() {

}


/**
 * List the companies and their tiers.
 */
function companyList() {
    print("Tier 1 Hotel Chains: \n\t Rahoi, Tower");
    print("Tier 2 Hotel Chains: \n\t American, Worldwide, Festival");
    print("Tier 3 Hotel Chains: \n\t Continental, Imperial");
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

}