package tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import java.awt.Color;
import java.util.*;

import clueGame.Board;
import clueGame.ComputerPlayer;
import clueGame.Solution;
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
				fail(); //Test should fail if testPlayer picks location that isn't a target
			}
		}
		
		assertEquals(5, roomCount); //Tests that all rooms are visited at least once
		
	}

	// A cell in a room the player didn't just occupy is available
	@Test
	public void targetNewRoom() {
		int testRow;
		int testCol;

		//Test that Computer chooses room with a path length of 2, starting from cell 18,7
		ComputerPlayer test1Player = new ComputerPlayer("testPlayer", 18, 7, Color.red);
		board.calcTargets(18, 7, 2);
		Set<BoardCell> targets = new HashSet<BoardCell>(board.getTargets());
		BoardCell testCell = test1Player.pickLocation(targets);
		testRow = testCell.getRow();
		testCol = testCell.getCol();
		if(testRow != 18 || testCol != 5) {
			fail(); //If the testPlayer does not choose the unvisited room, the test fails
		}
		
		//Test that Computer chooses room with a path length of 3, starting from cell 10,6
		ComputerPlayer test2Player = new ComputerPlayer("testPlayer", 10, 6, Color.red);
		board.calcTargets(10, 6, 3);
		targets = board.getTargets();
		testCell = test2Player.pickLocation(targets);
		testRow = testCell.getRow();
		testCol = testCell.getCol();
		if(testRow != 11 || testCol != 4) {
			fail(); //If the testPlayer does not choose the unvisited room, the test fails
		}
		
		//Test that Computer chooses room with a path length of 4, starting from cell 18,13
		ComputerPlayer test3Player = new ComputerPlayer("testPlayer", 18, 13, Color.red);
		board.calcTargets(18, 13, 4);
		targets = board.getTargets();
		testCell = test3Player.pickLocation(targets);
		testRow = testCell.getRow();
		testCol = testCell.getCol();
		if(testRow != 16 || testCol != 15) {
			fail(); //If the testPlayer does not choose the unvisited room, the test fails
		}
	}

	// A cell in a room the player just occupied is available
	@Test
	public void targetVisitedRoom() {
		
		int testRow;
		int testCol;
		
		//Calc targets once to avoid redundancy, target list includes three possible rooms that the player could enter
		board.calcTargets(15, 13, 4);
		Set<BoardCell> targets = new HashSet<BoardCell>(board.getTargets());
		
		//Run loop 100 times to make sure the player consistently chooses appropriate rooms
		for(int i = 0; i < 100; i++) {

			ComputerPlayer testPlayer = new ComputerPlayer("testPlayer", 15, 13, Color.red);

			//Player picks a location from the target list, should be one of the three rooms within range
			BoardCell firstRoom = testPlayer.pickLocation(targets);
			assertTrue(firstRoom.isDoorway()); 

			//Player picks location - it should be a different room than the player previously entered
			BoardCell secondRoom = testPlayer.pickLocation(targets);
			assertTrue(secondRoom != firstRoom); //Test that the player does not enter the room previously entered
			assertTrue(secondRoom.isDoorway());	//Test that the player entered a room

			//Player should pick a room that isn't the second room, but could be the first room
			BoardCell thirdRoom = testPlayer.pickLocation(targets);
			assertTrue(thirdRoom != secondRoom);
			assertTrue(thirdRoom.isDoorway()); //Test that player entered an available room
		}
		
	}


// Check accusation tests (Board)
	@Test
	public void accusationTests() {
		//Check accusation with the correct solution
		Solution correctSol = new Solution(board.getSolution());
		assertTrue(board.checkAccusation(correctSol));
		
		Solution wrongPerson = new Solution(board.getSolution());
		wrongPerson.person = "wrongPerson";
		assertFalse(board.checkAccusation(wrongPerson));
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
