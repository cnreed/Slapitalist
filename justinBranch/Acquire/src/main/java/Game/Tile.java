package Game; /**
 * Individual Tiles for the game board.
 *
 * @author Carolyn
 */

import Enums.TileStatus;
import Game.GameInterface.ITile;
//import org.apache.log4j.Logger;

public class Tile implements ITile {

//    public static Logger log = Logger.getLogger(Tile.class);
    private int row;
    private int col;
    private boolean top;
    private boolean bottom;
    private boolean left;
    private boolean right;
    private boolean visited;
    private TileStatus subStatus;

    private Company ownerCompany; // what company owns it
    private Player ownerPlayer; // what player owns it
    private TileStatus status; // Current status of the tile




    public Tile(int row, int col) {
        this.row = row;
        this.col = col;
        this.visited = false;
        top = bottom = left = right = true;
    }

    public Tile(int row, int col, Company ownerCompany, Player ownerPlayer,
                TileStatus tileStatus) {
        this.row = row;
        this.col = col;
        top = bottom = left = right = true;
        this.ownerCompany = ownerCompany;
        this.ownerPlayer = ownerPlayer;
        this.status = tileStatus;
        this.subStatus = tileStatus;
        this.visited = false;
    }

    /**
     * Return the row of a particular tile.
     *
     * @return Return the Row value for the tile
     */
    public int getRow() {
        return row;
    }

    /**
     * Return the col of a particular tile.
     *
     * @return
     */
    public int getCol() {
        return col;
    }

    /**
     * Updates the status of the tile.
     *
     * @param tileStatus
     */
    public void statusUpdate(TileStatus tileStatus) {
        status = tileStatus;
    }

    /**
     * This status is for tiles that are in someone's hand, that become
     * unplayable.
     *
     * @param tileStatus
     */
    public void subStatusUpdate(TileStatus tileStatus) {
        subStatus = tileStatus;
    }

    /**
     * Returns the status of the tile.
     *
     * @return
     */
    public TileStatus getStatus() {
        return status;
    }

    /**
     * @return the ownerCompany
     */
    public Company getOwnerCompany() {
        return ownerCompany;
    }

    /**
     * @param ownerCompany
     *            the ownerCompany to set
     */
    public void setOwnerCompany(Company ownerCompany) {
        this.ownerCompany = ownerCompany;
    }

    /**
     * @return the ownerPlayer
     */
    public Player getOwnerPlayer() {
        return ownerPlayer;
    }

    /**
     * @param ownerPlayer
     *            the ownerPlayer to set
     */
    public void setOwnerPlayer(Player ownerPlayer) {
        this.ownerPlayer = ownerPlayer;
    }

    /**
     * A boolean that checks if we are above above any possible boolean values
     *
     * @return
     */
    public boolean getTop() {
        return top;
    }

    /**
     * A boolean that checks if we are past the right side of the grid.
     *
     * @return
     */
    public boolean getRight() {
        return right;
    }

    /**
     * A boolean check that makes sure we have not surpassed the bottom of the
     * grid.
     *
     * @return
     */
    public boolean getBottom() {
        return bottom;
    }

    /**
     * A boolean check that makes sure we have not surpassed the left side of
     * the grid.
     *
     * @return
     */

    public boolean getLeft() {
        return left;
    }

    @Override
    public boolean getVisited() {
        return visited;
    }

    @Override
    public boolean setTop(boolean top) {
        return this.top;
    }

    @Override
    public boolean setRight(boolean right) {
        return this.right;
    }

    @Override
    public boolean setLeft(boolean left) {
        return this.left;
    }

    @Override
    public boolean setBottom(boolean bottom) {
        return this.bottom;
    }

    @Override
    public boolean setVisited(boolean visited) {
        return this.visited;
    }


    public TileStatus getSubStatus() {
        return subStatus;
    }


    @Override
    public String toString() {
        return String.valueOf((char) (this.row + 65))
                + String.valueOf(this.col + 1);
    }

}
