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

import java.awt.Color;

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
		
		for(Player p : board.getPlayers()) {
			if(p.getPlayerName().equals("Player1")) {
				assertEquals(Color.red, p.getPlayerColor());
				assertEquals(10, p.getPlayerRow());
				assertEquals(6, p.getPLayerCol());
				assert(p instanceof HumanPlayer); // Do we need this? Would the player methods even work if it wasn't one?
				playerCount++;
			}
			else if(p.getPlayerName().equals("Player2")) {
				assertEquals(Color.blue, p.getPlayerColor());
				assertEquals(6, p.getPlayerRow());
				assertEquals(16, p.getPLayerCol());
				assert(p instanceof ComputerPlayer);
				playerCount++;
			}
			else if(p.getPlayerName().equals("Player3")) {
				assertEquals(Color.green, p.getPlayerColor());
				assertEquals(20, p.getPlayerRow());
				assertEquals(13, p.getPLayerCol());
				assert(p instanceof ComputerPlayer);
				playerCount++;
			}
			else if(p.getPlayerName().equals("Player4")) {
				assertEquals(Color.orange, p.getPlayerColor());
				assertEquals(14, p.getPlayerRow());
				assertEquals(6, p.getPLayerCol());
				assert(p instanceof ComputerPlayer);
				playerCount++;
			}
			else if(p.getPlayerName().equals("Player5")) {
				assertEquals(Color.yellow, p.getPlayerColor());
				assertEquals(8, p.getPlayerRow());
				assertEquals(11, p.getPLayerCol());
				assert(p instanceof ComputerPlayer);
				playerCount++;
			}
			else if(p.getPlayerName().equals("Player6")) {
				assertEquals(Color.magenta, p.getPlayerColor());
				assertEquals(9, p.getPlayerRow());
				assertEquals(7, p.getPLayerCol());
				assert(p instanceof ComputerPlayer);
				playerCount++;
			}
			else {
				fail();
			}
		}
		
		assertEquals(NUM_PEOPLE, playerCount);
		
	}
	
	/*
	 * Test checks that the deck is complete and that there are the right number of each kind of card
	 */
	@Test
	public void deckTest() {
		
		assertEquals(NUM_CARDS, board.getDeck().size());
		
		int personCard = 0;
		int weaponCard = 0;
		int roomCard = 0;
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
		
		assertEquals(NUM_PEOPLE, personCard);
		assertEquals(NUM_WEAPONS, weaponCard);
		assertEquals(NUM_ROOMS, roomCard);
	}
	
	/*
	 * Checks that all cards are dealt, players have same number of cards within 1
	 */
	@Test
	public void dealCardTest() {
		int cardCount = 0;
		boolean firstLoop = true;
		int playerCards = 0;
		Set<Card> deck = new HashSet<Card>(board.getDeck());
		Set<Card> playerSet = new HashSet<Card>();
		
		for(Player p: board.getPlayers()) {
			if(firstLoop) {
				playerCards = p.getMyCards().size();
				firstLoop = false;
			} else {
				if(Math.abs(playerCards - p.getMyCards().size()) > 1) {
					fail();
				}
			}
			cardCount += p.getMyCards().size();
			for (Card c: p.getMyCards()) {
				playerSet.add(c);
			}
	
		}
		assertTrue(deck.containsAll(playerSet));
		assertEquals(deck.size(), playerSet.size() + 3);
		assertEquals(NUM_CARDS - 3, cardCount); 
	}
	
	/*
	 * Check that each card is only dealt once, including solution deck
	 */
	@Test
	public void checkPlayerCardsTest() {	
		fail();
	}

}
