package clueGame;

import java.awt.Color;
import java.util.*;

/**
 * @author Sam Mills, Nadia Bixenman
 *
 */
public class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	private ArrayList<Card> myCards;
	private Set<Card> seenCards;
	
	
	public Player(String playerName, int row, int column, Color color) {
		super();
		this.playerName = playerName;
		this.row = row;
		this.column = column;
		this.color = color;
		myCards = new ArrayList<Card>();
		seenCards = new HashSet<Card>();
	}


	public Card disproveSuggestion(Solution suggestion) {
		return null;
	}
	
	public String getPlayerName() {
		return playerName;
	}
	
	public int getPlayerRow() {
		return row;
	}
	
	public int getPLayerCol() {
		return column;
	}
	
	public Color getPlayerColor() {
		return color;
	}
	
	public ArrayList<Card> getMyCards(){
		return myCards;
	}
	
	public void dealCard(Card card) {
		myCards.add(card);
	}
}
