package tests;

import static org.junit.Assert.*;
import org.junit.*;

import clueGame.Board;

public class FileInitTests {
	public static final int NUM_ROOMS = 9;
	public static final int NUM_COLS = 21;
	public static final int NUM_ROWS = 21;
	public static final int NUM_DOORS = 17;
	public Board board;
	
	
	@Before
	public void setUp() {
		board = Board.getInstance();
	}

	@Test
	public void testLoadLegend() {
		assertEquals(NUM_ROOMS, board.getLegend().size());
	}
	
	@Test
	public void testDimensions() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testDoorDirections() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testNumDoors() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testInitials() {
		fail("Not yet implemented");
	}

}
