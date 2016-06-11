package ArtificialIntelligence.ArtficialIntelligenceInterface;

/**
 * Created by scout on 6/10/16.
 */
public interface IWeightTile {

    double getWeight();
    void addWeight(float addend);
    void removeWeight();
    void updateSurroundCount();
    int getSurroundCount();
    void removeSurroundCount();


}
