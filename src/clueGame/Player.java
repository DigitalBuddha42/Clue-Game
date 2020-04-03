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
	private ArrayList<Card> seenCards;
	
	
	public Player(String playerName, int row, int column, Color color) {
		super();
		this.playerName = playerName;
		this.row = row;
		this.column = column;
		this.color = color;
		myCards = new ArrayList<Card>();
		seenCards = new ArrayList<Card>();
	}


	public Card disproveSuggestion(Solution suggestion) {
		
		for(Card c: myCards) {
			if(c.getCardType() == CardType.PERSON) {
				if(c.getCardName().equals(suggestion.person)) {
					return c;
				}
			}
			else if(c.getCardType() == CardType.ROOM) {
				if(c.getCardName().equals(suggestion.room)) {
					return c;
				}
			}
			else if(c.getCardType() == CardType.WEAPON) {
				if(c.getCardName().equals(suggestion.weapon)) {
					return c;
				}
			}
		}
		return null;
	}
	
	public String getPlayerName() {
		return playerName;
	}
	
	public int getPlayerRow() {
		return row;
	}
	
	public int getPlayerCol() {
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
		seenCards.add(card);
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	
	public void setColumn(int column) {
		this.column = column;
	}
	
	public void setSeenCards(ArrayList<Card> seenCards) {
		this.seenCards = seenCards;
	}
	
	public ArrayList<Card> getSeenCards(){
		return seenCards;
	} 
}
