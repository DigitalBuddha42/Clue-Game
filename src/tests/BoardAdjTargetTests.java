package tests;

/*
 * This program tests that adjacencies and targets are calculated correctly.
 */

import java.util.Set;

//Doing a static import allows me to write assertEquals rather than
//assertEquals
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTests {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("ClueLayout.csv", "ClueLegend.txt");		
		// Initialize will load BOTH config files 
		board.initialize();
	}

	@Test
	public void testWalkways() {
		// Testing walkway at left edge of board, between two room walls
		Set<BoardCell> testList = board.getAdjList(8, 0);
		assertTrue(testList.contains(board.getCellAt(8, 1)));
		assertEquals(1, testList.size());
		
		// Testing walkway to the right of a room entrance
		testList = board.getAdjList(11, 5);
		assertTrue(testList.contains(board.getCellAt(11, 4)));
		assertTrue(testList.contains(board.getCellAt(11, 6)));
		assertTrue(testList.contains(board.getCellAt(10, 5)));
		assertTrue(testList.contains(board.getCellAt(12, 5)));
		assertEquals(4, testList.size());
		
		// Testing walkway in middle of walkway
		testList = board.getAdjList(13, 6);
		assertTrue(testList.contains(board.getCellAt(13, 5)));
		assertTrue(testList.contains(board.getCellAt(13, 7)));
		assertTrue(testList.contains(board.getCellAt(12, 6)));
		assertTrue(testList.contains(board.getCellAt(14, 6)));
		assertEquals(4, testList.size());
		
		//Testing walkway on bottom edge of board, next to one room wall
		testList = board.getAdjList(20, 7);
		assertTrue(testList.contains(board.getCellAt(19, 7)));
		assertTrue(testList.contains(board.getCellAt(20, 6)));
		assertEquals(2, testList.size());
		
		//Testing walkway below room entrance, next to room wall
		testList = board.getAdjList(6, 9);
		assertTrue(testList.contains(board.getCellAt(7, 9)));
		assertTrue(testList.contains(board.getCellAt(5, 9)));
		assertTrue(testList.contains(board.getCellAt(6, 10)));
		assertEquals(3, testList.size());
		
		//Testing walkway above room entrance
		testList = board.getAdjList(14, 11);
		assertTrue(testList.contains(board.getCellAt(14, 12)));
		assertTrue(testList.contains(board.getCellAt(14, 10)));
		assertTrue(testList.contains(board.getCellAt(15, 11)));
		assertTrue(testList.contains(board.getCellAt(13, 11)));
		assertEquals(4, testList.size());
		
		//Testing walkway to the left of room entrance
		testList = board.getAdjList(15, 14);
		assertTrue(testList.contains(board.getCellAt(15, 15)));
		assertTrue(testList.contains(board.getCellAt(15, 13)));
		assertTrue(testList.contains(board.getCellAt(16, 14)));
		assertTrue(testList.contains(board.getCellAt(14, 14)));
		assertEquals(4, testList.size());
		
		//Testing walkway between two rooms
		testList = board.getAdjList(3, 11);
		assertTrue(testList.contains(board.getCellAt(2, 11)));
		assertTrue(testList.contains(board.getCellAt(4, 11)));
		assertEquals(2, testList.size());
		
		//Testing walkway on right edge of board, next to two room walls
		testList = board.getAdjList(13, 20);
		assertTrue(testList.contains(board.getCellAt(12, 20)));
		assertEquals(1, testList.size());
	}
	

}
