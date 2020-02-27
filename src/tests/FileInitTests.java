package tests;

import static org.junit.Assert.*;

import java.util.*;
import org.junit.*;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

public class FileInitTests {
	public static final int LEGEND_SIZE = 11;
	public static final int NUM_COLS = 21;
	public static final int NUM_ROWS = 21;
	public static final int NUM_DOORS = 17;
	public static Board board;
	
	
	@Before
	public void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueLegend.txt");		
		board.initialize();
	}

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
	
	@Test
	public void testDimensions() {
		assertEquals(NUM_COLS, board.getNumColumns());
		assertEquals(NUM_ROWS, board.getNumRows());
	}
	
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
	
	@Test
	public void testNumDoors() {
		int numDoors = 0;
		for (int i = 0; i < board.getNumRows(); i++) {
			for (int j = 0; j < board.getNumColumns(); j++)
			if (board.getCellAt(i,  j).isRoom()) {
				numDoors++;
			}
		}
		assertEquals(NUM_DOORS, numDoors);
	}
	
	@Test
	public void testInitials() {
		// First Cell
		assertEquals("G", board.getCellAt(0, 0).getInitial());
		// Last Cell
		assertEquals("K", board.getCellAt(20, 20).getInitial());
		// Walkway
		assertEquals("W", board.getCellAt(10, 6).getInitial());
		// Other
		assertEquals("L", board.getCellAt(2, 19).getInitial());
		assertEquals("P", board.getCellAt(18, 2).getInitial());
		assertEquals("D", board.getCellAt(10, 19).getInitial());
		assertEquals("O", board.getCellAt(3, 9).getInitial());
		assertEquals("H", board.getCellAt(3, 13).getInitial());
		assertEquals("R", board.getCellAt(11, 3).getInitial());
	}

}
