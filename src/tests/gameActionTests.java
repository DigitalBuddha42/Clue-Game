package tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import java.awt.Color;
import java.util.*;

import clueGame.Board;
import clueGame.ComputerPlayer;
import clueGame.Player;
import clueGame.Solution;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;

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
			testPlayer.movePlayer(firstRoom);
			assertTrue(firstRoom.isDoorway()); 

			//Player picks location - it should be a different room than the player previously entered
			BoardCell secondRoom = testPlayer.pickLocation(targets);
			testPlayer.movePlayer(secondRoom);
			assertTrue(secondRoom != firstRoom); //Test that the player does not enter the room previously entered
			assertTrue(secondRoom.isDoorway());	//Test that the player entered a room

			//Player should pick a room that isn't the second room, but could be the first room
			BoardCell thirdRoom = testPlayer.pickLocation(targets);
			testPlayer.movePlayer(thirdRoom);
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

		Solution wrongWeapon = new Solution(board.getSolution());
		wrongWeapon.weapon = "wrongWeapon";
		assertFalse(board.checkAccusation(wrongWeapon));

		Solution wrongRoom = new Solution(board.getSolution());
		wrongRoom.room = "wrongRoom";
		assertFalse(board.checkAccusation(wrongRoom));
	}


	// Making suggestion tests (ComputerPlayer)
	// Tests that a suggestion can only be made in the correct room
	@Test
	public void suggestionCorrectRoom() {
		ComputerPlayer testPlayer = (ComputerPlayer) board.getPlayers().get(1);

		//Run test 100 times to make sure the correct room is chosen each time
		for(int i = 0; i < 100; i++) {
			testPlayer.setRow(11);
			testPlayer.setColumn(4); //Set testPlayer location to the doorway of the bedroom

			Solution roomSuggestion = testPlayer.createSuggestion();
			assertEquals(roomSuggestion.room, "Bedroom"); //Test that the suggested room is the room the player is in

			testPlayer.setRow(6);
			testPlayer.setColumn(13); //Set testPlayer location to the doorway of the hall

			roomSuggestion = testPlayer.createSuggestion();
			assertEquals(roomSuggestion.room, "Hall"); //Test that the suggested room is the room the player is in
		}

	}

	// There is only one option (of weapon and/or player), it must be chosen
	@Test
	public void suggestionOneOptionOnly() {
		ComputerPlayer testPlayer = (ComputerPlayer) board.getPlayers().get(1); //Set testPlayer to Player2

		testPlayer.setRow(11);
		testPlayer.setColumn(4); //Set testPlayer location to the doorway of the bedroom

		ArrayList<Card> tempSeenCards = new ArrayList();
		for(Card c: board.getDeck()) {
			if(!c.getCardName().equals("Player3") && !c.getCardName().equals("Sword")) { //Add all cards to seenCards except for player3 and sword cards
				tempSeenCards.add(c);
			}
		}

		testPlayer.setSeenCards(tempSeenCards);

		Solution testSuggestion = testPlayer.createSuggestion();

		assertEquals(testSuggestion.person, "Player3");
		assertEquals(testSuggestion.weapon, "Sword"); //Assert that the computerPlayer selects the only possible solution
	}

	// There are several options of weapons, randomly chosen
	@Test
	public void suggestionMultipleWeapons() {
		ComputerPlayer testPlayer = (ComputerPlayer) board.getPlayers().get(1);

		testPlayer.setRow(11);
		testPlayer.setColumn(4);

		ArrayList<Card> tempSeenCards = new ArrayList();
		for(Card c: board.getDeck()) {
			if(!c.getCardName().equals("Player3") && !c.getCardName().equals("Sword")
					&& !c.getCardName().equals("Poison") && !c.getCardName().equals("Knife")) { //Add all cards to seenCards except for player3 and three weapon cards
				tempSeenCards.add(c);
			}
		}

		testPlayer.setSeenCards(tempSeenCards);

		Boolean hasBeenSword = false;
		Boolean hasBeenPoison = false;
		Boolean hasBeenKnife = false;
		while (!hasBeenSword || !hasBeenPoison || !hasBeenKnife) {
			Solution testSuggestion = testPlayer.createSuggestion();

			if (testSuggestion.weapon.equals("Sword")) {
				hasBeenSword = true;
			}
			if (testSuggestion.weapon.equals("Poison")) {
				hasBeenPoison = true;
			}
			if (testSuggestion.weapon.equals("Knife")) {
				hasBeenKnife = true;
			}

			assertEquals("Player3", testSuggestion.person);
			assertTrue(testSuggestion.weapon.equals("Sword") || testSuggestion.weapon.equals("Poison")
					|| testSuggestion.weapon.equals("Knife"));
		}


	}

	// There are several options of players, randomly chosen
	@Test
	public void suggestionMultiplePlayers() {
		ComputerPlayer testPlayer = (ComputerPlayer) board.getPlayers().get(1);

		testPlayer.setRow(11);
		testPlayer.setColumn(4);

		ArrayList<Card> tempSeenCards = new ArrayList();
		for(Card c: board.getDeck()) {
			if(!c.getCardName().equals("Player3") && !c.getCardName().equals("Sword")
					&& !c.getCardName().equals("Player1") && !c.getCardName().equals("Player4")) { //Add all cards to seenCards except for sword and three player cards
				tempSeenCards.add(c);
			}
		}

		testPlayer.setSeenCards(tempSeenCards);

		Boolean hasBeenPlayer1 = false;
		Boolean hasBeenPlayer3 = false;
		Boolean hasBeenPlayer4 = false;
		while (!hasBeenPlayer1 || !hasBeenPlayer3 || !hasBeenPlayer4) {
			Solution testSuggestion = testPlayer.createSuggestion();

			if (testSuggestion.person.equals("Player1")) {
				hasBeenPlayer1 = true;
			}
			if (testSuggestion.person.equals("Player3")) {
				hasBeenPlayer3 = true;
			}
			if (testSuggestion.person.equals("Player4")) {
				hasBeenPlayer4 = true;
			}

			assertEquals("Sword", testSuggestion.weapon);
			assertTrue(testSuggestion.person.equals("Player1") || testSuggestion.person.equals("Player3")
					|| testSuggestion.person.equals("Player4"));
		}
		
	}


	// Disproving suggestion tests (Player)
	// Player has only one matching card, this card must be chosen
	@Test
	public void disproveOneMatching() {
		Solution testSolution = new Solution("Player1", "Bedroom", "Sword");
		Card testRoom = new Card("Bedroom", CardType.ROOM);
		Card testWeapon = new Card("Sword", CardType.WEAPON);
		Card testRoom2 = new Card("Hallway", CardType.ROOM); //Card that isn't in the solution
		
		//Create a player with only one of the cards
		ComputerPlayer testPlayer = new ComputerPlayer("testPlayer", 0, 0, Color.red);
		testPlayer.dealCard(testRoom);
		testPlayer.dealCard(testRoom2);
		
		//Create another player with one of the different cards
		ComputerPlayer testPlayer2 = new ComputerPlayer("testPlayer", 0, 0, Color.red);
		testPlayer2.dealCard(testWeapon);
		
		//Run loop 100 times to make sure the players consistently return the correct card
		for(int i = 0; i < 100; i++) {
			assertTrue(testPlayer.disproveSuggestion(testSolution).equals(testRoom));
			assertTrue(testPlayer2.disproveSuggestion(testSolution).equals(testWeapon));
		}
	}

	// Player has several matching cards, card selected randomly
	@Test
	public void disproveManyMatching() {
		Solution testSolution = new Solution("Player1", "Bedroom", "Sword");
		Card testRoom = new Card("Bedroom", CardType.ROOM);
		Card testWeapon = new Card("Sword", CardType.WEAPON);
		Card testPerson = new Card("Player1", CardType.PERSON);
		Card testRoom2 = new Card("Hallway", CardType.ROOM); //Card that isn't in the solution
		
		//Create a player with all of the solution cards dealt to them, and one card that isnt in the suggestion
		ComputerPlayer testPlayer = new ComputerPlayer("testPlayer", 0, 0, Color.red);
		testPlayer.dealCard(testRoom);
		testPlayer.dealCard(testWeapon);
		testPlayer.dealCard(testPerson);
		testPlayer.dealCard(testRoom2);
		
		//Variables to make sure each card is dealt at least once - tests that player is choosing randomly
		int roomCount = 0;
		int weaponCount = 0;
		int personCount = 0;
		
		for(int i = 0; i < 100; i++) {
			Card testCard = testPlayer.disproveSuggestion(testSolution);
			if(testCard.equals(testRoom)) {
				roomCount++;
			}
			else if(testCard.equals(testWeapon)) {
				weaponCount++;
			}
			else if(testCard.equals(testPerson)) {
				personCount++;
			}
			else {
				fail(); //If the player doesn't return one of the three cards in the suggestion, the test fails
			}
		}
		
		assertTrue(roomCount>0);
		assertTrue(weaponCount>0);
		assertTrue(personCount>0); //Test that each card was chosen at least once
		
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
