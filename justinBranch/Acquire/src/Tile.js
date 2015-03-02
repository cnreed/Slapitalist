/**
 * Individual Tiles for the game board.
 * 
 * @author Carolyn
 *
 */

 var row,
 	row_x, 				// numerical value (to be converted to char)
 	col,				// numerical value
 	top,				// boolean
 	bottom,				// boolean
 	left,				// boolean 
 	right,				// boolean
 	ownerCompany,		// which company owns tile
 	ownerPlayer,		// which player owns tile
 	status,				// status of tile from list below
 	statuses = ["INBAG", "INHAND", "ONBOARD", "QUARANTINED", "UNPLAYABLE"],
 	safe,				// boolean is tile safe?
 	subStatus;  		// 


 function TileBasic(row, col){
 	this.row = row;
 	this.col = col;
 	top = bottom = left = right = false;
 }

 function TileFull(row, col, ownerCompany, ownerPlayer, statusIndex){
 	this.row = row;
 	this.col = col;
 	this.row_x = row_x;
 	top = bottom  = left = right = true;
 	this.ownerCompany = ownerCompany;
 	this.ownerPlayer = ownerPlayer;
 	this.status = statuses[statusIndex];
 	this.subStatus = statuses[statusIndex];
 }

function toString(){
	return String.fromCharCode(this.row + 65) 
		+ String.fromCharCode(this.col + 1);
}

