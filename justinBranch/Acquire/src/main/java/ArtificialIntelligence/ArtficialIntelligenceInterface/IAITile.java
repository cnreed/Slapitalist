package ArtificialIntelligence.ArtficialIntelligenceInterface;

import ArtificialIntelligence.AITile;
import ArtificialIntelligence.WeightTile;
import Game.Tile;

/**
 * Created by scout on 6/10/16.
 */
public interface IAITile extends Comparable<AITile> {

    double getWeight();
    WeightTile getWeightTile();
    Tile getTile();
    int compareTo(AITile o);

}
