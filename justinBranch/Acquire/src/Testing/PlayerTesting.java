//package Testing;
//
//import Game.Game;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.ArrayList;
//
//import static org.junit.Assert.assertTrue;
//
//public class PlayerTesting {
//
//    Game.GameTest game;
//    Game.Board board;
//    Game.Player player1;
//    Game.Player player2;
//    Game.Player player3;
//    Game.Player player4;
//    Game.Player player5;
//    Game.Player player6;
//    Game.Player[] players;
//    static int [][] playerStockList;
//
//    @Before
//    public void setUp() {
//
//        board = new Game.Board(9, 12);
//        players = new Game.Player[6];
//        player1 = new Game.Player("Carolyn", board);
//        player2 = new Game.Player("Nicole", board);
//        player3 = new Game.Player("Reed", board);
//        player4 = new Game.Player("Scout", board);
//        player5 = new Game.Player("Phillip", board);
//        player6 = new Game.Player("Marilyn", board);
//
//        players[0] = player1;
//        players[1] = player2;
//        players[2] = player3;
//        players[3] = player4;
//        players[4] = player5;
//        players[5] = player6;
//
//        game = new Game.GameTest(9, 12, players, board);
//        playerStockList = game.getPlayerStockList();
//        playerStockList[0][0] = 3;
//        Game.GameTest.Rahoi.soldStock(3);
//
//    }
//
//    @After
//    public void teatDown() {
//        board = null;
//        players = null;
//        player1 = null;
//        player2 = null;
//        player3 = null;
//        player4 = null;
//        player5 = null;
//        player6 = null;
//
//        game = null;
//    }
//
//    @Test
//    public void determinePlayer1Winner() {
//
//        player1.updateCash(5600);
//        ArrayList<Game.Player> winner = game.determineWinner();
//
//        assertTrue("The size of the ArrayList should be 1", winner.size() == 1);
//        assertTrue("The winner should be Carolyn",
//                winner.get(0).getName() == player1.getName());
//
//    }
//
//    @Test
//    public void determineTie() {
//
//        player1.updateCash(5600);
//        player3.updateCash(5600);
//
//        ArrayList<Game.Player> winner = game.determineWinner();
//        assertTrue(
//                "The winners should be Carolyn and Reed",
//                ((winner.get(0).getName().equals(player1.getName()))
//                        && ((winner.get(1).getName().equals(player3.getName()))) || ((winner
//                        .get(0).getName().equals(player3.getName())) && ((winner
//                        .get(1).getName().equals(player1.getName()))))));
//
//    }
//
//    @Test
//    public void payPlayers() {
//
//        game.payPlayers();
//
//
//    }
//}
