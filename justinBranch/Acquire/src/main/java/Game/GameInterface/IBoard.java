package Game.GameInterface;

import Game.Company;
import Game.Tile;

/**
 * Created by scout on 6/24/16.
 */
public interface IBoard {

    int getX();
    int getY();
    void initialize();
    void checkBoundaries(Tile tile, int x, int y);
    Tile getTile(int x, int y);
    void initBag();
    Tile bagPop();
    void setUnplayable(Company company);


}
