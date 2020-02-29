package tests;

import static org.junit.Assert.*;

import java.util.*;
import org.junit.*;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

/**
 * @author Sam Mills, Nadia Bixenman
 *
 */
public class FileInitTests {
	// Appropriate constants for our config files
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_COLS = 21;
	public static final int NUM_ROWS = 21;
	public static final int NUM_DOORS = 17;
	public static Board board;
	
	// Create the board, set the config files, initialize the board
	@Before
	public void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueLegend.txt");		
		board.initialize();
	}

	// Tests that the legend is the correct size and that several of the entries are read correctly
	@Test
	public void testLegend() {
		Map<Character, String> legend = board.getLegend();
		
		assertEquals(LEGEND_SIZE, legend.size());
		
		assertEquals("Parlor", legend.get('P'));
		assertEquals("Banquet Hall", legend.get('B'));
		assertEquals("Bedroom", legend.get('R'));
		assertEquals("Living Room", legend.get('L'));
		assertEquals("Walkway", legend.get('W'));
	}
	
	// Tests that the board dimensions are correct
	@Test
	public void testDimensions() {
		assertEquals(NUM_COLS, board.getNumColumns());
		assertEquals(NUM_ROWS, board.getNumRows());
	}
	
	// Tests that a door in each direction is read and entered correctly, as well as that a walkway and room cell are not recognized as doors
	@Test
	public void testDoorDirections() {
		BoardCell cell = board.getCellAt(15, 10);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.UP, cell.getDoorDirection());
		
		cell = board.getCellAt(7, 3);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.RIGHT, cell.getDoorDirection());
		
		cell = board.getCellAt(5, 9);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.DOWN, cell.getDoorDirection());
		
		cell = board.getCellAt(11, 16);
		assertTrue(cell.isDoorway());
		assertEquals(DoorDirection.LEFT, cell.getDoorDirection());
		
		// Walkway
		cell = board.getCellAt(10, 14);
		assertFalse(cell.isDoorway());
		
		// Room
		cell = board.getCellAt(2, 13);
		assertFalse(cell.isDoorway());
		
	}
	
	// Tests that the board contains the correct number of doors
	@Test
	public void testNumDoors() {
		int numDoors = 0;
		for (int i = 0; i < board.getNumRows(); i++) {
			for (int j = 0; j < board.getNumColumns(); j++)
			if (board.getCellAt(i,  j).isDoorway()) {
				numDoors++;
			}
		}
		assertEquals(NUM_DOORS, numDoors);
	}
	
	// Tests that the first and last cells, a walkway cell, and several other cells have the correct initials
	@Test
	public void testInitials() {
		// First Cell in Board
		assertEquals('G', board.getCellAt(0, 0).getInitial());
		// Last Cell in Board
		assertEquals('K', board.getCellAt(20, 20).getInitial());
		// Walkway
		assertEquals('W', board.getCellAt(10, 6).getInitial());
		// Storage
		assertEquals('X', board.getCellAt(9, 9).getInitial());
		// Other
		assertEquals('P', board.getCellAt(18, 2).getInitial());
		assertEquals('D', board.getCellAt(10, 19).getInitial());
		assertEquals('O', board.getCellAt(3, 9).getInitial());
		assertEquals('H', board.getCellAt(3, 13).getInitial());
		assertEquals('R', board.getCellAt(11, 3).getInitial());
	}

}
