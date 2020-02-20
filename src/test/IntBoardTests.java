package test;

import static org.junit.Assert.*;

import org.junit.*;

import experiments.BoardCell;
import experiments.IntBoard;

public class IntBoardTests {
	IntBoard gameBoard;
	
	@Before
	public void beforeAll() {
		gameBoard = new IntBoard();
	}

	@Test
	public void testAdjacancy0() {
		BoardCell cell = gameBoard.getCell(0,0);
		fail("Not yet implemented");
	}
	
	@Test
	public void testAdjacancy3_3() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testAdjacancy1_3() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testAdjacancy3_0() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testAdjacancy1_1() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testAdjacancy2_2() {
		fail("Not yet implemented");
	}

}
