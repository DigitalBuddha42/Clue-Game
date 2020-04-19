package clueGame;

import java.awt.Color;
import java.awt.Graphics;
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
		ArrayList<Card> possibleCards = new ArrayList();
		
		for(Card c: seenCards) {
			if(c.getCardType() == CardType.PERSON) {
				if(c.getCardName().equals(suggestion.person)) {
					possibleCards.add(c);
				}
			}
			else if(c.getCardType() == CardType.ROOM) {
				if(c.getCardName().equals(suggestion.room)) {
					possibleCards.add(c);
				}
			}
			else if(c.getCardType() == CardType.WEAPON) {
				if(c.getCardName().equals(suggestion.weapon)) {
					possibleCards.add(c);
				}
			}
		}
		
		if(possibleCards.size() > 0) {
			Random rand = new Random();
			int randomIndex = rand.nextInt(possibleCards.size());
			return possibleCards.get(randomIndex);
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
	
	// Draws a circle of the correct color for each player
	public void draw(Graphics g) {
		int width = 25;
		int height = 25;
		g.setColor(color);
		g.fillOval(column + width*column, row + height*row, width, height);
	}
	
	public void makeMove(BoardCell cell) {
		setRow(cell.getRow());
		setColumn(cell.getCol());
	}
	
	public void makeMove(Set<BoardCell> targets) {
		Random rand = new Random();
		int choice = rand.nextInt(targets.size());
		int count = 0;
		for (BoardCell c: targets) {
			if (count == choice) {
				this.row = c.getRow();
				this.column = c.getCol();
				return;
			}
			count++;
		}
	}
	
	
}
