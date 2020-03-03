package tests;


import java.util.Set;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

/**
 * @author Sam Mills, Nadia Bixenman
 *
 */
public class BoardAdjTargetTests {
	// Static board
	private static Board board;
	@Before
	public void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueLegend.txt");
		board.initialize();

	}
	
	// Adjacency list tests for cells by doorways - GREEN on spreadsheet
	@Test
	public void testAdjDoorways() {
		// Testing walkway to the right of a room entrance
		System.out.println(board.getNumRows());
		Set<BoardCell> testList = board.getAdjList(11, 5);
		assertTrue(testList.contains(board.getCellAt(11, 4)));
		assertTrue(testList.contains(board.getCellAt(11, 6)));
		assertTrue(testList.contains(board.getCellAt(10, 5)));
		assertTrue(testList.contains(board.getCellAt(12, 5)));
		assertEquals(4, testList.size());
		
		// Testing walkway below room entrance, next to room wall
		testList = board.getAdjList(6, 9);
		assertTrue(testList.contains(board.getCellAt(7, 9)));
		assertTrue(testList.contains(board.getCellAt(5, 9)));
		assertTrue(testList.contains(board.getCellAt(6, 10)));
		assertEquals(3, testList.size());
		
		// Testing walkway above room entrance
		testList = board.getAdjList(14, 11);
		assertTrue(testList.contains(board.getCellAt(14, 12)));
		assertTrue(testList.contains(board.getCellAt(14, 10)));
		assertTrue(testList.contains(board.getCellAt(15, 11)));
		assertTrue(testList.contains(board.getCellAt(13, 11)));
		assertEquals(4, testList.size());
		
		// Testing walkway to the left of room entrance
		testList = board.getAdjList(15, 14);
		assertTrue(testList.contains(board.getCellAt(15, 15)));
		assertTrue(testList.contains(board.getCellAt(15, 13)));
		assertTrue(testList.contains(board.getCellAt(16, 14)));
		assertTrue(testList.contains(board.getCellAt(14, 14)));
		assertEquals(4, testList.size());
		
		// Testing walkways next to a door but from the wrong direction
		testList = board.getAdjList(8, 3);
		assertTrue(testList.contains(board.getCellAt(8, 4)));
		assertTrue(testList.contains(board.getCellAt(8, 2)));
		assertEquals(2, testList.size());

		testList = board.getAdjList(15, 9);
		assertTrue(testList.contains(board.getCellAt(14, 9)));
		assertTrue(testList.contains(board.getCellAt(15, 8)));
		assertEquals(2, testList.size());
				
	}

	// Adjacency list tests for walkway cells - LIGHT PURPLE on spreadsheet
	@Test
	public void testAdjWalkways() {
		// Testing walkway at left edge of board, between two room walls
		Set<BoardCell> testList = board.getAdjList(8, 0);
		assertTrue(testList.contains(board.getCellAt(8, 1)));
		assertEquals(1, testList.size());
		
		// Testing walkway in middle of walkway
		testList = board.getAdjList(13, 6);
		assertTrue(testList.contains(board.getCellAt(13, 5)));
		assertTrue(testList.contains(board.getCellAt(13, 7)));
		assertTrue(testList.contains(board.getCellAt(12, 6)));
		assertTrue(testList.contains(board.getCellAt(14, 6)));
		assertEquals(4, testList.size());
		
		// Testing walkway on bottom edge of board, next to one room wall
		testList = board.getAdjList(20, 7);
		assertTrue(testList.contains(board.getCellAt(19, 7)));
		assertTrue(testList.contains(board.getCellAt(20, 6)));
		assertEquals(2, testList.size());
		
		// Testing walkway between two rooms
		testList = board.getAdjList(3, 11);
		assertTrue(testList.contains(board.getCellAt(2, 11)));
		assertTrue(testList.contains(board.getCellAt(4, 11)));
		assertEquals(2, testList.size());
		
		// Testing walkway on right edge of board, next to two room walls
		testList = board.getAdjList(13, 20);
		assertTrue(testList.contains(board.getCellAt(14, 20)));
		assertEquals(1, testList.size());
		
		// Testing walkway on top edge of board, next to a room wall
		testList = board.getAdjList(0, 15);
		assertTrue(testList.contains(board.getCellAt(0, 16)));
		assertTrue(testList.contains(board.getCellAt(1, 15)));
		assertEquals(2, testList.size());
	}
	
	// Adjacency list tests for cells in rooms - ORANGE on spreadsheet
	@Test
	public void testAdjInRoom() {
		// Test cell in Room
		Set<BoardCell> testList = board.getAdjList(0, 7);
		assertEquals(0, testList.size());
		
		testList = board.getAdjList(17, 17);
		assertEquals(0, testList.size());
	}
	
	// Adjacency list tests for cells in doorways - DARK PURPLE on spreadsheet
	@Test
	public void testAdjInDoor() {
		// Test doorway cell UP
		Set<BoardCell> testList = board.getAdjList(16, 2);
		assertTrue(testList.contains(board.getCellAt(15, 2)));
		assertEquals(1, testList.size());
		
		// Test doorway cell RIGHT
		testList = board.getAdjList(18, 5);
		assertTrue(testList.contains(board.getCellAt(18, 6)));
		assertEquals(1, testList.size());

		// Test doorway cell DOWN
		testList = board.getAdjList(6, 13);
		assertTrue(testList.contains(board.getCellAt(7,13)));
		assertEquals(1, testList.size());

		// Test doorway cell LEFT
		testList = board.getAdjList(4, 17);
		assertTrue(testList.contains(board.getCellAt(4, 16)));
		assertEquals(1, testList.size());
	}
	
	// Test walkways with path length 1 - LIGHT BLUE on spreadsheet
	@Test
	public void testTargets_1() {
		board.calcTargets(8, 20, 1);
		Set<BoardCell> testList = board.getTargets();
		assertTrue(testList.contains(board.getCellAt(7, 20)));
		assertTrue(testList.contains(board.getCellAt(9, 20)));
		assertEquals(2, testList.size());
		
		board.calcTargets(1, 5, 1);
		testList = board.getTargets();
		assertTrue(testList.contains(board.getCellAt(0, 5)));
		assertTrue(testList.contains(board.getCellAt(1, 4)));
		assertTrue(testList.contains(board.getCellAt(2, 5)));
		assertEquals(3, testList.size());
	}
	
	// Test walkways with path length 2 - LIGHT BLUE on spreadsheet
	@Test
	public void testTargets_2() {
		board.calcTargets(8, 20, 2);
		Set<BoardCell> testList = board.getTargets();
		assertTrue(testList.contains(board.getCellAt(7, 19)));
		assertEquals(1, testList.size());
		
		board.calcTargets(1, 5, 2);
		testList = board.getTargets();
		assertTrue(testList.contains(board.getCellAt(0, 4)));
		assertTrue(testList.contains(board.getCellAt(2, 4)));
		assertTrue(testList.contains(board.getCellAt(3, 5)));
		assertEquals(3, testList.size());
	}
	
	// Test walkways with path length 4 - LIGHT BLUE on spreadsheet
	@Test
	public void testTargets_4() {
		board.calcTargets(8, 20, 4);
		Set<BoardCell> testList = board.getTargets();
		assertTrue(testList.contains(board.getCellAt(7, 17)));
		assertEquals(1, testList.size());
		
		board.calcTargets(1, 5, 4);
		testList = board.getTargets();
		assertTrue(testList.contains(board.getCellAt(2, 4)));
		assertTrue(testList.contains(board.getCellAt(3, 5)));
		assertTrue(testList.contains(board.getCellAt(4, 4)));
		assertTrue(testList.contains(board.getCellAt(5, 5)));
		assertTrue(testList.contains(board.getCellAt(4, 4)));
		assertEquals(5, testList.size());
	}
	
	// Test walkways with path length 5 - LIGHT BLUE on spreadsheet
	@Test
	public void testTargets_5() {
		board.calcTargets(8, 20, 5);
		Set<BoardCell> testList = board.getTargets();
		assertTrue(testList.contains(board.getCellAt(7, 16)));
		assertEquals(1, testList.size());
		
		board.calcTargets(1, 5, 5);
		testList = board.getTargets();
		assertTrue(testList.contains(board.getCellAt(2, 5)));
		assertTrue(testList.contains(board.getCellAt(3, 4)));
		assertTrue(testList.contains(board.getCellAt(4, 5)));
		assertTrue(testList.contains(board.getCellAt(6, 5)));
		assertTrue(testList.contains(board.getCellAt(5, 4)));
		assertTrue(testList.contains(board.getCellAt(1, 4)));
		assertTrue(testList.contains(board.getCellAt(0, 5)));
		assertEquals(7, testList.size());
	}
	
	// Test targets from cells into rooms - LIGHT BLUE on spreadsheet
	@Test
	public void testTargetsIntoRoom() {
		// Door 1 space and 2 spaces away
		board.calcTargets(6, 4, 2);
		Set<BoardCell> testList = board.getTargets();
		assertEquals(6, testList.size());
		// Into room
		assertTrue(testList.contains(board.getCellAt(6, 3)));
		assertTrue(testList.contains(board.getCellAt(7, 3)));
		// Other
		assertTrue(testList.contains(board.getCellAt(4, 4)));
		assertTrue(testList.contains(board.getCellAt(5, 5)));
		assertTrue(testList.contains(board.getCellAt(7, 5)));
		assertTrue(testList.contains(board.getCellAt(8, 4)));
		
		// Door 4 spaces away
		board.calcTargets(8, 8, 4);
		testList = board.getTargets();
		assertEquals(14, testList.size());
		// Into room
		assertTrue(testList.contains(board.getCellAt(5, 9)));
		// Other
		assertTrue(testList.contains(board.getCellAt(8, 12)));
		assertTrue(testList.contains(board.getCellAt(8, 10)));
		assertTrue(testList.contains(board.getCellAt(8, 4)));
		assertTrue(testList.contains(board.getCellAt(7, 5)));
		assertTrue(testList.contains(board.getCellAt(9, 5)));
		assertTrue(testList.contains(board.getCellAt(11, 7)));
		assertTrue(testList.contains(board.getCellAt(7, 7)));
		assertTrue(testList.contains(board.getCellAt(8, 6)));
		assertTrue(testList.contains(board.getCellAt(10, 6)));
		assertTrue(testList.contains(board.getCellAt(7, 9)));
		assertTrue(testList.contains(board.getCellAt(7, 11)));
		assertTrue(testList.contains(board.getCellAt(6, 10)));
		assertTrue(testList.contains(board.getCellAt(9, 7)));
	}
	
	// Test targets from room exits - LIGHT BLUE on spreadsheet
	@Test
	public void testTargetsLeavingRoom() {
		// One Step from room
		board.calcTargets(17, 5, 1);
		Set<BoardCell> testList = board.getTargets();
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(17, 6)));
		
		// Two steps from room
		board.calcTargets(16, 15, 2);
		testList = board.getTargets();
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCellAt(15, 14)));
		assertTrue(testList.contains(board.getCellAt(16, 13)));
	}

}
