package ArtificialIntelligence;

import ArtificialIntelligence.ArtficialIntelligenceInterface.IAITile;
import Game.Tile;

public class AITile implements IAITile {

    Tile tile;
    WeightTile wTile;

    public AITile(WeightTile wTile, Tile tile) {
        this.tile = tile;
        this.wTile = wTile;

    }

    public double getWeight() {
        return wTile.getWeight();
    }

    public WeightTile getWeightTile() {
        return wTile;
    }

    public Tile getTile() {
        return tile;
    }

    @Override
    public int compareTo(AITile o) {
        if (getWeight() == o.getWeight()) {
            return 0;
        }
        if (getWeight() > o.getWeight()) {
            return 1;
        }
        if (getWeight() < o.getWeight()) {
            return -1;
        }
        return 0;
    }

    @Override
    public String toString() {

        return tile.toString() + ", " + wTile.getWeight();
    }

}
