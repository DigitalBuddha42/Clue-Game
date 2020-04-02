package tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import java.util.*;
import java.lang.Math;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;

import java.awt.Color;

/**
 * @author Sam Mills, Nadia Bixenman
 *
 */
public class gameSetupTests {
	private static Board board;
	public static final int NUM_PEOPLE = 6;
	public static final int NUM_WEAPONS = 6;
	public static final int NUM_ROOMS = 9;
	public static final int NUM_CARDS = 21;

	@BeforeClass
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueLegend.txt", "CluePlayer", "ClueWeapons");		
		board.initialize();
		board.selectAnswer();
		board.dealCards();

	}

	/*
	 * Test checks that all players are read in with correct row, column, color, and playerType
	 */
	@Test
	public void loadPeopleTest() {

		assertEquals(board.getPlayers().size(), NUM_PEOPLE);
		int playerCount = 0;
		
		//Check that every card has the correct color, row, and column
		for(Player p : board.getPlayers()) {
			if(p.getPlayerName().equals("Player1")) {
				assertEquals(Color.red, p.getPlayerColor());
				assertEquals(10, p.getPlayerRow());
				assertEquals(6, p.getPLayerCol());
				playerCount++;
			}
			else if(p.getPlayerName().equals("Player2")) {
				assertEquals(Color.blue, p.getPlayerColor());
				assertEquals(6, p.getPlayerRow());
				assertEquals(15, p.getPLayerCol());
				playerCount++;
			}
			else if(p.getPlayerName().equals("Player3")) {
				assertEquals(Color.green, p.getPlayerColor());
				assertEquals(20, p.getPlayerRow());
				assertEquals(13, p.getPLayerCol());
				playerCount++;
			}
			else if(p.getPlayerName().equals("Player4")) {
				assertEquals(Color.orange, p.getPlayerColor());
				assertEquals(14, p.getPlayerRow());
				assertEquals(7, p.getPLayerCol());
				playerCount++;
			}
			else if(p.getPlayerName().equals("Player5")) {
				assertEquals(Color.yellow, p.getPlayerColor());
				assertEquals(7, p.getPlayerRow());
				assertEquals(11, p.getPLayerCol());
				playerCount++;
			}
			else if(p.getPlayerName().equals("Player6")) {
				assertEquals(Color.magenta, p.getPlayerColor());
				assertEquals(9, p.getPlayerRow());
				assertEquals(7, p.getPLayerCol());
				playerCount++;
			}
			else {
				fail();
			}
		}

		assertEquals(NUM_PEOPLE, playerCount); //Checks that all player cards are accounted for

	}

	/*
	 * Test checks that the deck is complete and that there are the right number of each kind of card
	 */
	@Test
	public void deckTest() {

		assertEquals(NUM_CARDS, board.getDeck().size()); // Check that the deck is the expected size

		int personCard = 0;
		int weaponCard = 0;
		int roomCard = 0;
		
		// Count the number of each type of card
		for(Card testCard : board.getDeck()) {
			if(testCard.getCardType() == CardType.PERSON) {
				personCard ++;
			}
			else if(testCard.getCardType() == CardType.WEAPON) {
				weaponCard ++;
			}
			else if(testCard.getCardType() == CardType.ROOM) {
				roomCard ++;
			}
		}
		
		// Compare the counted cards with the expected amounts
		assertEquals(NUM_PEOPLE, personCard);
		assertEquals(NUM_WEAPONS, weaponCard);
		assertEquals(NUM_ROOMS, roomCard);
	}

	/*
	 * Checks that all cards are dealt only once, and that players have same number of cards within 1
	 */
	@Test
	public void dealCardTest() {
		int cardCount = 0;
		boolean firstLoop = true;
		int playerCards = 0;
		Solution solution = board.getSolution();
		Set<Card> deck = new HashSet<Card>(board.getDeck());
		Set<Card> playerSet = new HashSet<Card>();

		for(Player p: board.getPlayers()) {
			if(firstLoop) {
				playerCards = p.getMyCards().size(); // Check the number of cards held by the first player to be compared with other players
				firstLoop = false;
			} else {
				// Verify that each player has within 1 card of each other
				if(Math.abs(playerCards - p.getMyCards().size()) > 1) {
					fail();
				}
			}
			cardCount += p.getMyCards().size();
			for (Card c: p.getMyCards()) {
				// Verify that no duplicate cards are held by players
				if (playerSet.contains(c)) {
					fail();
				} else {
					playerSet.add(c);
				}
			}

		}
		// Verify that the players have been dealt all but 3 cards contained in the deck
		assertTrue(deck.containsAll(playerSet));
		assertEquals(deck.size(), playerSet.size() + 3);

		assertEquals(NUM_CARDS - 3, cardCount); // Verify that the players have right number of cards total (no duplicates)

		deck.removeAll(playerSet);

		assertEquals(3, deck.size()); // Verify that the deck, when the dealt cards are removed, contains only the solution

		// Verify that the remaining cards in the deck are in fact the correct solution
		for (Card c: deck) {
			if (c.getCardType() == CardType.PERSON) {
				assertTrue(c.getCardName().equals(solution.person));
			} else if (c.getCardType() == CardType.ROOM) {
				assertTrue(c.getCardName().equals(solution.room));
			} else if (c.getCardType() == CardType.WEAPON) {
				assertTrue(c.getCardName().equals(solution.weapon));
			}
		}
	}
}

