package Game.GameInterface;

import Game.Tile;

/**
 * Created by scout on 6/17/16.
 */
public interface IPlayer {

    void addHand(Tile tile);
    void showHand();
    void updateHand(int loc, Tile tile);
    int getCash();
    String printTile(Tile tile);
    Tile getTile(int loc);
    void setTile(int loc, Tile tile);
    String getName();
    int getNumHand();
    void setNumHand(int numHand);
    void setHandSize(int size);
    int getHandSize();
}
