package ArtificialIntelligence;

import ArtificialIntelligence.ArtficialIntelligenceInterface.IAIHand;

public class AIHand implements IAIHand {

    AITile[] hand;
    WeightBoard weight;
    int handSize;
    static int size;
    static int updateLocation;

    public AIHand(int handSize, WeightBoard weight) {
        hand = new AITile[handSize];
        this.weight = weight;
        this.handSize = handSize;
        this.size = 0;
        this.updateLocation = 0;
    }

    public void addTile(AITile aTile) {
        hand[size] = aTile;
        size++;
    }

    public void replaceTile(AITile aTile) {
        hand[updateLocation] = aTile;
        size++;
    }

    public AITile pickTile() {


        AITile picked = hand[0];
        System.out.println("hand[0]: " + hand[0].toString());
        for (int i = 1; i < handSize; i++) {
            System.out.println("hand[" + i + "]: " + hand[i].toString());
            if (picked.compareTo(hand[i]) < 1) {
                picked = hand[i];
                updateLocation = i;
                System.out.println("Picked: " + picked.toString());
                break;
            }
        }
        size--;
        return picked;
    }

    public AITile getTile(int loc) {
        return hand[loc];
    }

}
