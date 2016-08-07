package Game;

import Enums.TileStatus;
import Game.GameInterface.IPlayer;
import org.apache.log4j.Logger;

public class Player implements IPlayer {

    public static Logger log = Logger.getLogger(Player.class);
    private int totalCash;
    private String name;

    private int pid; // game session unique id
    private int handSize = 6;
    private Tile[] hand = new Tile[handSize];
    private int companiesStarted;
    private int companiesMerged;
    private int numberOfTurns;
    private int numHand = 0; // The number of Tiles in the hand.


    public Player(String name, Board board) {
        this.name = name;
        this.totalCash = 6000;
        this.hand = new Tile[handSize];
        numHand = 0;

    }

    public void addHand(Tile tile) {
        if (numHand >= 6) {
            System.out.println("Cheating is prohibited");
            return;
        }
        tile.statusUpdate(TileStatus.InBag);
        hand[numHand] = tile;
        numHand++;
    }

    public void showHand() {
        for (int i = 0; i < hand.length; i++) {
            System.out.println((i + 1) + ". " + (char) (hand[i].getRow() + 65) + ""
                    + (hand[i].getCol() + 1));
        }
    }

    /**
     * Places a new tile in the location of an old tile.
     *
     * @param loc  - Location in the hand
     * @param tile - Tile to add to the hand
     */
    public void updateHand(int loc, Tile tile) {
        hand[loc] = tile;
        numHand++;
    }

    public int getCash() {

        return totalCash;
    }

    public void updateCash(int money) {
        totalCash += money;
    }

    public String getName() {
        return name;
    }


    public int getNumHand() {
        return numHand;
    }


    public void setNumHand(int numHand) {
        this.numHand = numHand;
    }


    public void setHandSize(int size) {
        handSize = size;
    }


    public int getHandSize() {
        return handSize;
    }


    public String printTile(Tile tile) {
        return tile.getRow() + "" + tile.getCol();
    }

    public Tile getTile(int loc) {
        return hand[loc];
    }


    public void setTile(int loc, Tile tile) {
        hand[loc] = tile;
    }

    /**
     * Do something with the board.
     *
     * @param loc - the location in the hand.
     */
    public int placeTile(int loc) {
        Tile tile = hand[loc];
        tile.statusUpdate(TileStatus.OnBoard);
        hand[loc] = null;
        numHand--;
        return loc;
        // TODO: Might not have to return the location.
    }

    public void printHand() {
        System.out.print("Your hand: ");
        for (Tile aHand : hand) {
            if (!aHand.getSubStatus().equals("UNPLAYABLE")
                    || !(aHand.getStatus().equals("UNPLAYABLE"))) {
                aHand.toString();
            }
        }
        System.out.println();
        System.out.print("Unplayable tiles in your hand: ");
        for (Tile aHand : hand) {
            if (aHand.getSubStatus().equals("UNPLAYABLE")) {
                aHand.toString();
            }
        }
    }





}
