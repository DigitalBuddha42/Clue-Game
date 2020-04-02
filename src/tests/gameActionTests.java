/**
 * 
 */
package tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Sam Mills, Nadia Bixenman
 *
 */
public class gameActionTests {


	@BeforeClass
	public static void setUp() {

	}

	// Target tests (ComputerPlayer)
	// No room cells available
	@Test
	public void targetNoRooms() {
		fail("Not yet implemented");
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
