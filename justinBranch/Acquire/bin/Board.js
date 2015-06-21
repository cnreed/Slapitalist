/* Grid */

var x_size,
	y_size,
	board,
	inBag,
	bag,
	randArray,
	turns,
	merges,
	companies_started;

function Board(x_size, y_size){
        this.x_size = x_size;
        this.y_size = y_size;
        initialize();
        randomizeBoard();
        initBag();
    }

/**
 * Initializes the board on start up.
 */
function initialize() {

    for (var i = 0; i < x_size; i++) {
        for (var j = 0; j < y_size; j++) {
            var tile = new TileFull(i, j, null, null, 0);

            board[i][j] = tile;
            randArray[i][j] = tile;
            inBag[i][j] = true;
        }
    }

    Console.log("Board initialized")
}

/**
 * Prints the board.
 */
function print(){

    var statusIndicator = "";
    for (var i = 0; i < x_size; i++) {
        for (var j = 0; j < y_size; j++) {
            Tile; thisTile = grid[i][j];
            if (thisTile.getStatus().equals("ONBOARD")) {
                Console.log("[" + String.fromCharCode(grid[i][j].row + 65)
                + (grid[i][j].col + 1) + "]\t");
            } else {
                Console.log("" + (String.fromCharCode(grid[i][j].row + 65))
                + (grid[i][j].col + 1) + "\t");
            }
        }
        statusIndicator = "";
        Console.log("\n");
    }
}

/**
 * Checks to see if the tile is on the edge of the board. If it is, then set
 * corresponding possible moves to false.
 *
 * @param tile
 * @param x
 * @param y
 */
function checkBoundaries( tile,  x,  y) {
    if (x <= 0) {
        tile.top = false;
    }
    if (y <= 0) {
        tile.left = false;
    }
    if (x >= 11) {
        tile.bottom = false;
    }
    if (y >= 11) {
        tile.right = false;
    }
}

function getTile(x, y){
    return grid[x][y];
}

/**
 * Prints if the corresponding move is possible according to the limits of
 * the board.
 *
 * @param tile
 */
function printBoundaries(tile) {
    Console.log("Top: " + tile.top + " ");
    Console.log("Right: " + tile.right + " ");
    Console.log("Bottom: " + tile.bottom + " ");
    Console.log("Left: " + tile.left);
}

/**
 * Randomizes the values in the array. I think this can be combined with
 * with init bag, later on.
 *
 *  TODO: need to port this over to javascript.
 */
function randomizeGrid() {


    for (var i = 0; i < board.length - 1; i++) {
        for (var j = 0; j < board[i].length - 1; j++) {
            var x = rand.nextInt(i + 1);
            var y = rand.nextInt(j + 1);

             temp = randArray[i][j]; //temp is a Tile
            randArray[i][j] = randArray[x][y];
            randArray[x][y] = temp;
        }
    }
}

function fisherYates ( myArray ) {
    var i = myArray.length;
    if ( i === 0 ){return false;}
    while ( --i ) {
        var j = Math.floor( Math.random() * ( i + 1 ) );
        var tempi = myArray[i];
        var tempj = myArray[j];
        myArray[i] = tempj;
        myArray[j] = tempi;
    }
}

function initBag() {

    for (var i = 0; i < randArray.length; i++) {
        for (var j = 0; j < randArray[i].length; j++) {
            tile = randArray[i][j];
            bag.add(tile);
        }
    }
}

/**
 * Pops a Tile off the top of the bag.
 * @return
 */
function bagPop() {
    var tile = bag.pop();
    tile.status = 1;
    return tile;
}

Array.prototype.contains = function(obj) {
    var i = this.length;
    while (i--) {
        if (this[i] == obj) {
            return true;
        }
    }
    return false;
}

/**
 * TODO: Test this function.
 *
 * @param company
 */
function setUnplayable( company) {
    var change; //Tile
    for (var i = 0; i < company.companyTiles.length; i++) {
        var tile = company.companyTiles[i];
        var x = tile.row;
        var y = tile.col;
        if (tile.top) {
            change = board[x][y + 1];
            if (!company.companyTiles.contains(change)) {
                change.subStatus = 4;
            }
        }
        if (tile.right) {
            change = board[x + 1][y];
            if (!company.companyTiles.contains(change)) {
                change.subStatus = 4;
            }
        }
        if (tile.bottom) {
            change = board[x][y - 1];
            if (!company.companyTiles.contains(change)) {
                change.subStatus = 4;
            }
        }
        if (tile.left) {
            change = grid[x - 1][y];
            if (!company.companyTiles.contains(change)) {
                change.subStatus = 4;
            }
        }
    }
}

function playTile(board, tile) {

}