package Game.GameInterface;

import Game.Tile;

/**
 * Created by scout on 6/24/16.
 */
public interface ICompany {

    int getMajorityPayout();
    int getMinorityPayout();
    int getCID();
    void incrementSize();
    void setSafe(boolean safe);
    boolean getSafe();
    boolean getOnboard();
    void setEndable();
    void setOnboard();
    void dissolve();
    void addTile(Tile tile);
    String getCompanyName();
    void setStockCount(int count);
    int getStockCount();
    int getSharePrice();
    Tile getTile(int loc);
    boolean containsTile(Tile tile);
    void addStockBack(int stock);
    boolean soldStock(int quantity);
    void logPrintTiles();
    int size();


}
