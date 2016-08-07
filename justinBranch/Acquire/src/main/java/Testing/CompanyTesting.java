//package Testing;
//
//import Game.Board;
//import Game.Company;
//import Game.Tile;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.ArrayList;
//
//import static org.junit.Assert.assertTrue;
//
//
//
//public class CompanyTesting {
//    Board board;
//    Board lBoard;
//    Board unplayBoard;
//    Game.GameTest game;
//    Company comp;
//    Company test;
//    Company loop;
//    Company Ltest;
//    Company sameSize1;
//    Company sameSize2;
//    Company diffSame1;
//    Company different;
//    Company diffSame2;
//    Company largeComp1;
//    Company largeComp2;
//
//    @Before
//    public void setUp() {
//        board = new Board(9, 12);
//        lBoard = new Board(9, 12);
//        unplayBoard = new Board(9, 12);
//        game = new Game.GameTest(9, 12, null, board);
//        comp = new Company("First", 0, null, 0, 0);
//        test = new Company("Test", 0, null, 3, 0);
//        loop = new Company("Loop", 0, null, 0, 0);
//        Ltest = new Company("Ltest", 0, null, 0, 0);
//        sameSize1 = new Company("SameSize1", 0, null, 2, 0);
//        sameSize2 = new Company("SameSize2", 0, null, 2, 0);
//        diffSame1 = new Company("diffSame1", 0, null, 4, 0);
//        different = new Company("different", 0, null, 3, 0);
//        diffSame2 = new Company("diffSame2", 0, null, 4, 0);
//        largeComp1 = new Company("largeComp1", 0, null, 0, 0);
//        largeComp2 = new Company("largeComp2", 0, null, 0, 0);
//    }
//
//    @Test
//    public void testSetCompany() {
//        // board.print();
//        assertTrue("Company names is actually " + comp.getCompanyName(), comp
//                .getCompanyName().equals("First"));
//
//    }
//
//    // @Test
//    // public void addCompany() {
//    // board.print();
//    // Tile tile = board.getTile(0, 0);
//    // comp.addTile(tile);
//    //
//    // assertTrue("Company size is actually: " + comp.size(), comp.size(), 1);
//    // }
//
//    @Test
//    public void setSafe() {
//        comp.setSafe(true);
//        assertTrue("The company is safe", comp.getSafe());
//    }
//
//    @Test
//    public void onBoard() {
//        System.out.println();
//        board.print();
//        System.out.println();
//        Tile tile = board.getTile(0, 0);
//        comp.addTile(tile);
//        tile.statusUpdate(2);
//        board.print();
//        assertTrue("The tile status is: " + tile.getStatus(), tile.getStatus()
//                .equals("ONBOARD"));
//    }
//
//    @Test
//    public void testSetUnplayable() {
//
//        Tile tile = null;
//        System.out.println("Testing setUnplayable: \n");
//        for (int i = 0; i < 11; i++) {
//            tile = board.getTile(0, i);
//            test.addTile(tile);
//            tile.statusUpdate(2);
//            tile.subStatusUpdate(5);
//            tile.ownerCompany = test;
//        }
//        board.print();
//        game.setUnplayable(board);
//        // assertTrue(test.getSafe());
//        tile = board.getTile(1, 0);
//        String status = tile.getSubStatus();
//        System.out.println("Next testing: \n");
//        assertTrue(status.equals("QUARANTINED"));
//        for (int i = 0; i < 11; i++) {
//            tile = board.getTile(2, i);
//            loop.addTile(tile);
//            tile.statusUpdate(2);
//            tile.subStatusUpdate(5);
//            tile.ownerCompany = loop;
//        }
//        game.setUnplayable(board, test);
//        board.print();
//    }
//
//    @Test
//    public void testLShapeSetUnplayable() {
//        System.out.println("L Shaped test Comany: \n");
//
//        Tile tile;
//        System.out.println("Long ways: \n");
//        for (int i = 0; i < 11; i++) {
//            tile = lBoard.getTile(0, i);
//            Ltest.addTile(tile);
//            tile.statusUpdate(2);
//            tile.subStatusUpdate(5);
//            tile.ownerCompany = Ltest;
//        }
//        lBoard.print();
//        System.out.println("Short ways: \n");
//        for (int i = 0; i < 4; i++) {
//            tile = lBoard.getTile(i, 0);
//            tile.statusUpdate(2);
//            tile.subStatusUpdate(5);
//            tile.ownerCompany = Ltest;
//        }
//        lBoard.print();
//        System.out.println("L Shaped Company set: ");
//        game.setUnplayable(lBoard);
//        tile = lBoard.getTile(1, 1);
//        System.out.println("Tile substatus is: " + tile.getSubStatus());
//        game.logPrintTileStatus(lBoard);
//        assertTrue(tile.getSubStatus().equals("QUARANTINED"));
//    }
//
//    @Test
//    public void testMergeCompsOfSameSize() {
//        ArrayList<Company> companyList = new ArrayList<Company>();
//        companyList.add(sameSize1);
//        companyList.add(sameSize2);
//        Company largest = game.findLargest(companyList);
//        assertTrue(largest.equals(sameSize2));
//    }
//
//    @Test
//    public void testMergeCompsofDuplicateSizes() {
//        ArrayList<Company> companyList = new ArrayList<Company>();
//        companyList.add(sameSize1);
//        companyList.add(sameSize2);
//        companyList.add(diffSame1);
//        companyList.add(different);
//        companyList.add(diffSame2);
//        Company largest = game.findLargest(companyList);
//        assertTrue(largest.equals(diffSame1));
//
//    }
//
//    @Test
//    public void testAllTilesUnplayable() {
//
//        for (int i = 0; i < 1; i++) {
//            for (int j = 0; j < 12; j++) {
//                Tile tile = unplayBoard.getTile(i, j);
//                largeComp1.addTile(tile);
//                tile.statusUpdate(2);
//                tile.subStatusUpdate(5);
//                tile.ownerCompany = largeComp1;
//                TilesLeftOnBoard--;
//
//            }
//        }
//        System.out.println("First Company Done!");
//        unplayBoard.print();
//        System.out.println();
//        for (int x = 2; x < 9; x++) {
//            for (int y = 0; y < 12; y++) {
//                Tile tile = unplayBoard.getTile(x, y);
//                largeComp2.addTile(tile);
//                tile.statusUpdate(2);
//                tile.subStatusUpdate(5);
//                tile.ownerCompany = largeComp2;
//                TilesLeftOnBoard--;
//            }
//        }
//
//        int count = TilesLeftOnBoard--;
//        unplayBoard.print();
//        game.setUnplayable(unplayBoard, largeComp1);
//        // game.logPrintTileStatus(]un);
//        System.out.println("Tile: " + board.getTile(1, 0).toString());
//        System.out.println("Tile: " + board.getTile(3, 1).toString());
//        assertTrue("Tile's substatus is" + board.getTile(1, 0).getStatus(),
//                unplayBoard.getTile(1, 0).getSubStatus().equals("UNPLAYABLE"));
//        boolean bool = game.allTilesUnplayable(unplayBoard, count);
//        assertTrue(bool);
//
//    }
//}
