//import Game.Game;
//import org.junit.After;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
///**
// * Created by scout on 3/29/16.
// */
//public class PlayerTest {
//
//    Game.Player player;
//    Game.Board board;
//
//    @Before
//    public void setUp() throws Exception {
//
//        board = new Game.Board(9 ,12);
//        player = new Game.Player("Carolyn", null);
//
//
//    }
//
//    @After
//    public void tearDown() throws Exception {
//
//    }
//
//    //TODO Figure out what in the world is Hamcrest
//    @Test
//    public void testAddHand() {
//
//        Game.Tile tile = board.getTile(0,0);
//        player.addHand(tile);
//        Assert.assertTrue("Players do not match: Player is: " + tile.getOwnerPlayer(), tile.getOwnerPlayer() == player);
//
//    }
//
//    @Test
//    public void testShowHand() throws Exception {
//
//    }
//
//    @Test
//    public void testUpdateHand() throws Exception {
//
//    }
//
//    @Test
//    public void testGetCash() throws Exception {
//
//    }
//
//    @Test
//    public void testUpdateCash() throws Exception {
//
//    }
//
//    @Test
//    public void testGetName() throws Exception {
//
//    }
//
//    @Test
//    public void testPrintTile() throws Exception {
//
//    }
//
//    @Test
//    public void testPlaceTile() throws Exception {
//
//    }
//
//    @Test
//    public void testPrintHand() throws Exception {
//
//    }
//}