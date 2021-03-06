package clueGame;

/**
 * @author Sam Mills, Nadia Bixenman
 *
 */
public class Card {
	private String cardName;
	private CardType type;
	
	public Card(String cardName, CardType type) {
		this.cardName = cardName;
		this.type = type;
	}
	
	public boolean equals() {
		return false;
	}
	
	public CardType getCardType() {
		return type;
	}
	
	public String getCardName() {
		return cardName;
	}
}
