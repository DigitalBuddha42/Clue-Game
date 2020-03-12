package tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Player;


public class gameSetupTests {
	private Board board;
	public static final int NUM_PEOPLE = 6;
	public static final int NUM_WEAPONS = 6;
	public static final int NUM_CARDS = 21;
	
	@BeforeClass
	public void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueLegend.txt", "CluePlayer.txt", "ClueWeapons.txt");		
		board.initialize();
	}

	@Test
	public void loadPeopleTest() {
		Player testPlayer = new Player;
		
	}

}
