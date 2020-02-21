package tests;

import static org.junit.Assert.*;
import org.junit.*;
import experiment.BoardCell;
import experiment.IntBoard;
import java.util.*;

public class IntBoardTests {
	IntBoard gameBoard;
	
	@Before
	public void beforeAll() {
		gameBoard = new IntBoard();
	}

	@Test
	public void testAdjacancy0_0() {
		BoardCell cell = gameBoard.getCell(0,0);
		Set<BoardCell> testList = gameBoard.getAdjList(cell);
		assertTrue(testList.contains(gameBoard.getCell(1, 0)));
		assertTrue(testList.contains(gameBoard.getCell(0, 1)));
		assertEquals(2, testList.size());
	}
	
	@Test
	public void testAdjacancy3_3() {
		BoardCell cell = gameBoard.getCell(3,3);
		Set<BoardCell> testList = gameBoard.getAdjList(cell);
		assertTrue(testList.contains(gameBoard.getCell(3, 2)));
		assertTrue(testList.contains(gameBoard.getCell(2, 3)));
		assertEquals(2, testList.size());
	}
	
	@Test
	public void testAdjacancy1_3() {
		BoardCell cell = gameBoard.getCell(1,3);
		Set<BoardCell> testList = gameBoard.getAdjList(cell);
		assertTrue(testList.contains(gameBoard.getCell(0, 3)));
		assertTrue(testList.contains(gameBoard.getCell(1, 2)));
		assertTrue(testList.contains(gameBoard.getCell(2, 3)));
		assertEquals(3, testList.size());
	}
	
	@Test
	public void testAdjacancy3_0() {
		BoardCell cell = gameBoard.getCell(3,0);
		Set<BoardCell> testList = gameBoard.getAdjList(cell);
		assertTrue(testList.contains(gameBoard.getCell(2, 0)));
		assertTrue(testList.contains(gameBoard.getCell(3, 1)));
		assertEquals(2, testList.size());
	}
	
	@Test
	public void testAdjacancy1_1() {
		BoardCell cell = gameBoard.getCell(1,1);
		Set<BoardCell> testList = gameBoard.getAdjList(cell);
		assertTrue(testList.contains(gameBoard.getCell(0, 1)));
		assertTrue(testList.contains(gameBoard.getCell(2, 1)));
		assertTrue(testList.contains(gameBoard.getCell(1, 0)));
		assertTrue(testList.contains(gameBoard.getCell(1, 2)));
		assertEquals(4, testList.size());
	}
	
	@Test
	public void testAdjacancy2_2() {
		BoardCell cell = gameBoard.getCell(2,2);
		Set<BoardCell> testList = gameBoard.getAdjList(cell);
		assertTrue(testList.contains(gameBoard.getCell(1, 2)));
		assertTrue(testList.contains(gameBoard.getCell(3, 2)));
		assertTrue(testList.contains(gameBoard.getCell(2, 1)));
		assertTrue(testList.contains(gameBoard.getCell(2, 3)));
		assertEquals(4, testList.size());
	}
	
	@Test
	public void testTargets0_0() {
		BoardCell cell = gameBoard.getCell(0, 0);
		
		// Length 4
		gameBoard.calcTargets(cell, 4);
		Set targets = gameBoard.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(gameBoard.getCell(0, 2)));
		assertTrue(targets.contains(gameBoard.getCell(1, 1)));
		assertTrue(targets.contains(gameBoard.getCell(1, 3)));
		assertTrue(targets.contains(gameBoard.getCell(2, 0)));
		assertTrue(targets.contains(gameBoard.getCell(2, 2)));
		assertTrue(targets.contains(gameBoard.getCell(3, 1)));
		
		//Length 6
		gameBoard.calcTargets(cell, 6);
		Set targets6 = gameBoard.getTargets();
		assertEquals(7, targets6.size());
		assertTrue(targets6.contains(gameBoard.getCell(0, 2)));
		assertTrue(targets6.contains(gameBoard.getCell(1, 1)));
		assertTrue(targets6.contains(gameBoard.getCell(1, 3)));
		assertTrue(targets6.contains(gameBoard.getCell(2, 0)));
		assertTrue(targets6.contains(gameBoard.getCell(2, 2)));
		assertTrue(targets6.contains(gameBoard.getCell(3, 1)));
		assertTrue(targets6.contains(gameBoard.getCell(3, 3)));
		
	}
	
	@Test
	public void testTargets1_1() {
		BoardCell cell = gameBoard.getCell(2, 2);
		
		// Length 2
		gameBoard.calcTargets(cell, 2);
		Set targets = gameBoard.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(gameBoard.getCell(0, 2)));
		assertTrue(targets.contains(gameBoard.getCell(1, 3)));
		assertTrue(targets.contains(gameBoard.getCell(1, 1)));
		assertTrue(targets.contains(gameBoard.getCell(2, 0)));
		assertTrue(targets.contains(gameBoard.getCell(3, 3)));
		assertTrue(targets.contains(gameBoard.getCell(3, 1)));
		
		//Length 3
		gameBoard.calcTargets(cell, 3);
		Set targets3 = gameBoard.getTargets();
		assertEquals(8, targets3.size());
		assertTrue(targets3.contains(gameBoard.getCell(0, 1)));
		assertTrue(targets3.contains(gameBoard.getCell(0, 3)));
		assertTrue(targets3.contains(gameBoard.getCell(1, 0)));
		assertTrue(targets3.contains(gameBoard.getCell(1, 2)));
		assertTrue(targets3.contains(gameBoard.getCell(2, 1)));
		assertTrue(targets3.contains(gameBoard.getCell(2, 3)));
		assertTrue(targets3.contains(gameBoard.getCell(3, 0)));
		assertTrue(targets3.contains(gameBoard.getCell(3, 2)));
	}

}
