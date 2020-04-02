package tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import java.awt.Color;
import java.util.*;

import clueGame.Board;
import clueGame.ComputerPlayer;
import clueGame.BoardCell;

/**
 * @author Sam Mills, Nadia Bixenman
 *
 */
public class gameActionTests {
	private static Board board;

	@BeforeClass
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueLegend.txt", "CluePlayer", "ClueWeapons");		
		board.initialize();
		board.selectAnswer();
		board.dealCards();
	}

// Choose target tests (ComputerPlayer)
	// No room cells available
	/*
	 * Player starts at cell 6,15
	 * With a roll of 2, player should visit cells 7,16 (cell1) 8,15 (cell2) 7,14 (cell3) 6,13 (cell4) 5,14 (cell5) and 4,15 (cell6) only 
	 */
	@Test
	public void targetNoRooms() {
		int testRow;
		int testCol;
		int roomCount = 0;
		
		//variables to make sure each room is only counted towards roomcount once
		boolean cell1 = false;
		boolean cell2 = false;
		boolean cell3 = false;
		boolean cell4 = false;
		boolean cell5 = false;
		boolean cell6 = false;
		boolean cell7 = false;
		
		while(roomCount < 5) {
			ComputerPlayer testPlayer = new ComputerPlayer("testPlayer", 6, 15, Color.red);
			board.calcTargets(6, 15, 2);
			Set<BoardCell> targets = new HashSet<BoardCell>(board.getTargets());
			BoardCell testCell = testPlayer.pickLocation(targets);
			testRow = testCell.getRow();
			testCol = testCell.getCol();
			
			if(testRow == 7 && testCol == 16) {
				if(!cell1) {
					roomCount++;
					cell1 = true;
				}
			}
			else if(testRow == 8 && testCol == 15) {
				if(!cell2) {
					roomCount++;
					cell2 = true;
				}
			}
			else if(testRow == 7 && testCol == 14) {
				if(!cell3) {
					roomCount++;
					cell3 = true;
				}
			}
			else if(testRow == 4 && testCol == 15) {
				if(!cell6) {
					roomCount++;
					cell6 = true;
				}
			}
			else if(testRow == 5 && testCol == 16) {
				if(!cell7) {
					roomCount++;
					cell7 = true;
				}
			}
			else {
				System.out.println(testRow);
				System.out.println(testCol);
				fail(); //Test should fail if testPlayer picks location that isn't a target
			}
		}
		
		assertEquals(5, roomCount); //Tests that all rooms are visited at least once
		
	}

	// A cell in a room the player didn't just occupy is available
	@Test
	public void targetNewRoom() {
		fail("Not yet implemented");
	}

	// A cell in a room the player just occupied is available
	@Test
	public void targetVisitedRoom() {
		fail("Not yet implemented");
	}


// Check accusation tests (Board)
	// The accusation is the solution
	@Test
	public void accusationCorrect() {
		fail("Not yet implemented");
	}

	// The accusation has the wrong player
	@Test
	public void accusationWrongPlayer() {
		fail("Not yet implemented");
	}

	// The accusation has the wrong weapon
	@Test
	public void accusationWrongWeapon() {
		fail("Not yet implemented");
	}

	// The accusation has the wrong room
	@Test
	public void accusationWrongRoom() {
		fail("Not yet implemented");
	}


// Making suggestion tests (ComputerPlayer)
	// Tests that a suggestion can only be made in the correct room
	@Test
	public void suggestionCorrectRoom() {
		fail("Not yet implemented");
	}

	// There is only one option (of weapon and/or player), it must be chosen
	@Test
	public void suggestionOneOptionOnly() {
		fail("Not yet implemented");
	}

	// There are several options of weapons, randomly chosen
	@Test
	public void suggestionMultipleWeapons() {
		fail("Not yet implemented");
	}

	// There are several options of players, randomly chosen
	@Test
	public void suggestionMultiplePlayers() {
		fail("Not yet implemented");
	}


// Disproving suggestion tests (Player)
	// Player has only one matching card, this card must be chosen
	@Test
	public void disproveOneMatching() {
		fail("Not yet implemented");
	}

	// Player has several matching cards, card selected randomly
	@Test
	public void disproveManyMatching() {
		fail("Not yet implemented");
	}

	// Player has no matching cards, returns null
	@Test
	public void disproveNoMatching() {
		fail("Not yet implemented");
	}



// Handling suggestion tests (Board)
	// Suggestion can not be disproved by any player, must return null
	@Test
	public void cannotBeDisproved() {
		fail("Not yet implemented");
	}

	// Only the suggester can disprove the suggestion, must return null
	@Test
	public void suggesterCanDisprove() {
		fail("Not yet implemented");
	}

	// The human player can disprove the suggestion, must return the answer/disproving card
	@Test
	public void humanCanDisprove() {
		fail("Not yet implemented");
	}

	// The human player can disprove the suggestion, but is the suggester, must return null
	@Test
	public void humanSuggesterCanDisprove() {
		fail("Not yet implemented");
	}

	// Multiple ComputerPlayers can disprove, must be the next in list
	@Test
	public void multipleCompCanDisprove() {
		fail("Not yet implemented");
	}

	// Human player and other player(s) can disprove, must be the non-human player
	@Test
	public void humanAndCompCanDisprove() {
		fail("Not yet implemented");
	}

}
