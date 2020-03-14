package tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;

import java.awt.Color;

public class gameSetupTests {
	private static Board board;
	public static final int NUM_PEOPLE = 6;
	public static final int NUM_WEAPONS = 6;
	public static final int NUM_CARDS = 21;
	public static int myCardTotal = 0; //Variable to record total number of cards to compare to NUM_CARDS
	
	@BeforeClass
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueLegend.txt", "CluePlayer.txt", "ClueWeapons.txt");		
		board.initialize();
	}

	@Test
	public void loadPeopleTest() {
		
		assertEquals(board.getPlayers().size(), NUM_PEOPLE);
		
		for(Player p : board.getPlayers()) {
			if(p.getPlayerName().equals("Player1")) {
				assertEquals(p.getPlayerColor(), Color.red);
				assertEquals(p.getPlayerRow(), 10);
				assertEquals(p.getPLayerCol(), 6);
				assert(p instanceof HumanPlayer);
				myCardTotal++;
			}
			else if(p.getPlayerName().equals("Player2")) {
				assertEquals(p.getPlayerColor(), Color.blue);
				assertEquals(p.getPlayerRow(), 6);
				assertEquals(p.getPLayerCol(), 16);
				assert(p instanceof ComputerPlayer);
				myCardTotal++;
			}
			else if(p.getPlayerName().equals("Player3")) {
				assertEquals(p.getPlayerColor(), Color.green);
				assertEquals(p.getPlayerRow(), 20);
				assertEquals(p.getPLayerCol(), 13);
				assert(p instanceof ComputerPlayer);
				myCardTotal++;
			}
			else if(p.getPlayerName().equals("Player4")) {
				assertEquals(p.getPlayerColor(), Color.orange);
				assertEquals(p.getPlayerRow(), 14);
				assertEquals(p.getPLayerCol(), 6);
				assert(p instanceof ComputerPlayer);
				myCardTotal++;
			}
			else if(p.getPlayerName().equals("Player5")) {
				assertEquals(p.getPlayerColor(), Color.yellow);
				assertEquals(p.getPlayerRow(), 8);
				assertEquals(p.getPLayerCol(), 11);
				assert(p instanceof ComputerPlayer);
				myCardTotal++;
			}
			else if(p.getPlayerName().equals("Player6")) {
				assertEquals(p.getPlayerColor(), Color.magenta);
				assertEquals(p.getPlayerRow(), 9);
				assertEquals(p.getPLayerCol(), 7);
				assert(p instanceof ComputerPlayer);
				myCardTotal++;
			}
		}
		
	}

}
