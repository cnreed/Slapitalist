package ArtificialIntelligence.ArtficialIntelligenceInterface;

import Game.Board;
import Game.Tile;

/**
 * Created by scout on 6/10/16.
 */
public interface IWeightBoard {

    void printBoard();
    void printTileWithCoordinates();
    void printSurroundCountBoard();
    void centerTileStatus(Tile tile, Board board);
    void tilePlaced(Tile tile);
    void boardWipe();
}
