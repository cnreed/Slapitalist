package Game.GameInterface;

import Enums.TileStatus;
import Game.Company;
import Game.Player;

/**
 * Created by scout on 6/24/16.
 */
public interface ITile {

    int getRow();
    int getCol();
    void statusUpdate(TileStatus tileStatus);
    void subStatusUpdate(TileStatus tileStatus);

    TileStatus getStatus();
    Company getOwnerCompany();
    void setOwnerCompany(Company ownerCompany);
    Player getOwnerPlayer();
    void setOwnerPlayer(Player ownerPlayer);
    boolean getTop();
    boolean getRight();
    boolean getBottom();
    boolean getLeft();
    boolean getVisited();
    boolean setTop(boolean top);
    boolean setRight(boolean right);
    boolean setLeft(boolean left);
    boolean setBottom(boolean bottom);
    boolean setVisited(boolean visited);
    TileStatus getSubStatus();



}
