package ArtificialIntelligence;

import ArtificialIntelligence.ArtficialIntelligenceInterface.IAIHand;
import ArtificialIntelligence.ArtficialIntelligenceInterface.IArtificialIntelligence;
import Game.Tile;

public class AIHand implements IAIHand {

    AITile[] hand;
    WeightBoard weight;
    int handSize;
    static int size;
    static int updateLocation;
    IArtificialIntelligence intelligence;
    boolean createPriority;
    boolean growPriority;

    public AIHand(int handSize, WeightBoard weight, boolean createPriority, boolean growPriority, ArtificialIntelligence ai) {
        hand = new AITile[handSize];
        this.weight = weight;
        this.handSize = handSize;
        this.size = 0;
        this.updateLocation = 0;
        this.intelligence = ai;
        this.createPriority = createPriority;
        this.growPriority = growPriority;
    }

    public void addTile(AITile aTile) {
        hand[size] = aTile;
        size++;
    }

    @Override
    public AITile prioritySelection() {

        if(intelligence.getCompaniesOnboard() == 7 || this.growPriority) {
            //Ai can only grow companies or place tiles that will have no neighbors.
            //select a tile and return;
            for(int i = 0; i < handSize; i++) {
                AITile aiTile = hand[i];
                Tile tile = aiTile.getTile();
//                if(tile)
            }
            return null;
        }
        //It is createPriorty.

        //If createPriority cannot find a tile, then it must default to growPriority.


        //If Ai cannot grow or create a company. Ai must throw out a tile and pick new tile to be played. Return Tile with
        //status of unplayable and then replace that location with
        return null;
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
