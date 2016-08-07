package Testing;

import ArtificialIntelligence.AIHand;
import ArtificialIntelligence.AITile;
import ArtificialIntelligence.WeightTile;
import Game.*;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class ArtificialIntelligenceTesting {

    @Test
    public void HandTest() {
        Board board = new Board(3, 6);
        int handSize = 3;
        float weightAdded = 0.2f;

        // First Tile
        Tile tile = board.getTile(1, 2);
        WeightTile weight = new WeightTile(tile, 1, 2);
        weight.addWeight(weightAdded * 2);
        AITile aiTile = new AITile(weight, tile);
        System.out.println("Tile: " + tile.toString());

        // Second Tile
        Tile tile1 = board.getTile(0, 0);
        WeightTile weight1 = new WeightTile(tile1, 0, 0);
        weight1.addWeight(weightAdded);
        AITile aiTile1 = new AITile(weight1, tile1);
        System.out.println("Tile1:" + tile1.toString());

        // Third Tile
        Tile tile2 = board.getTile(2, 4);
        WeightTile weight2 = new WeightTile(tile2, 2, 4);
        weight2.addWeight(weightAdded * 4);
        AITile aiTile2 = new AITile(weight2, tile2);
        System.out.println("Tile2: " + tile2.toString());

        AIHand hand = new AIHand(handSize, null, false, false,null);
        hand.addTile(aiTile);
        hand.addTile(aiTile1);
        hand.addTile(aiTile2);

        AITile compare = hand.getTile(0);
        assertTrue("The tiles match ", compare.equals(aiTile));

    }

    @Test
    public void bestTileTest() {

        Board board = new Board(3, 6);
        int handSize = 3;
        float weightAdded = 0.2f;

        // First Tile
        Tile tile = board.getTile(1, 2);
        WeightTile weight = new WeightTile(tile, 1, 2);
        weight.addWeight(weightAdded * 2);
        AITile aiTile = new AITile(weight, tile);
        System.out.println("Tile: " + tile.toString());

        // Second Tile
        Tile tile1 = board.getTile(0, 0);
        WeightTile weight1 = new WeightTile(tile1, 0, 0);
        weight1.addWeight(weightAdded);
        AITile aiTile1 = new AITile(weight1, tile1);
        System.out.println("Tile1:" + tile1.toString());

        // Third Tile
        Tile tile2 = board.getTile(2, 4);
        WeightTile weight2 = new WeightTile(tile2, 2, 4);
        weight2.addWeight(weightAdded * 6);
        AITile aiTile2 = new AITile(weight2, tile2);
        System.out.println("Tile2: " + tile2.toString());

        AIHand hand = new AIHand(handSize, null, false, false, null);
        hand.addTile(aiTile);
        hand.addTile(aiTile1);
        hand.addTile(aiTile2);
        AITile bestTile = hand.pickTile();
        assertTrue("C5 Tile is the largest: ",
                bestTile.getTile().equals(board.getTile(2, 4)));

    }

    @Test
    public void tilesOfSameWeight() {

        Board board = new Board(3, 6);
        int handSize = 3;
        float weightAdded = 0.2f;

        // First Tile
        Tile tile = board.getTile(1, 2);
        WeightTile weight = new WeightTile(tile, 1, 2);
        weight.addWeight(weightAdded * 2);
        AITile aiTile = new AITile(weight, tile);
        System.out.println("Tile: " + tile.toString());

        // Second Tile
        Tile tile1 = board.getTile(0, 0);
        WeightTile weight1 = new WeightTile(tile1, 0, 0);
        weight1.addWeight(weightAdded * 6);
        AITile aiTile1 = new AITile(weight1, tile1);
        System.out.println("Tile1:" + tile1.toString());

        // Third Tile
        Tile tile2 = board.getTile(2, 4);
        WeightTile weight2 = new WeightTile(tile2, 2, 4);
        weight2.addWeight(weightAdded * 6);
        AITile aiTile2 = new AITile(weight2, tile2);
        System.out.println("Tile2: " + tile2.toString());

        AIHand hand = new AIHand(handSize, null, false, false, null);
        hand.addTile(aiTile);
        hand.addTile(aiTile1);
        hand.addTile(aiTile2);
        AITile bestTile = hand.pickTile();
        assertTrue("C5 Tile is the largest: ",
                bestTile.getTile().equals(board.getTile(0, 0)));

    }


}
