package ArtificialIntelligence.ArtficialIntelligenceInterface;

import ArtificialIntelligence.AITile;

/**
 * Created by scout on 6/10/16.
 */
public interface IAIHand {

    AITile pickTile();
    AITile getTile(int loc);
    void replaceTile(AITile tile);
    void addTile(AITile tile);
}
