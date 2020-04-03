package clueGame;

import java.awt.Color;
import java.util.*;

/**
 * @author Sam Mills, Nadia Bixenman
 */
public class ComputerPlayer extends Player {
	
	private int previousRow;
	private int previousCol;

	public ComputerPlayer(String playerName, int row, int column, Color color) {
		super(playerName, row, column, color);
		previousRow = 0;
		previousCol = 0; //Set to values that we know will be overridden because 0,0 is inside a room
	}

	public BoardCell pickLocation(Set<BoardCell> targets) {
		
		Random rand = new Random();
		int randomIndex = rand.nextInt(targets.size());
		int index = 0;
		
		Set<BoardCell> targetRooms = new HashSet();
		
		for(BoardCell cell : targets) {
			if(cell.isDoorway()) {
				if(cell.getRow() != previousRow || cell.getCol() != previousCol) {
					targetRooms.add(cell);
				}
			}
		}
		
		if(targetRooms.size() >0) {
			randomIndex = rand.nextInt(targetRooms.size());
			for(BoardCell cell : targetRooms) {
				if(index == randomIndex) {
					this.setRow(cell.getRow());
					this.setColumn(cell.getCol());
					previousRow = cell.getRow();
					previousCol = cell.getCol();
					return cell;
				}
				index++;
			}
		}
		
		for(BoardCell cell : targets) {
			if(index == randomIndex) {
				this.setRow(cell.getRow());
				this.setColumn(cell.getCol());
				return cell;
			}
			index++;
		}
		
		return null;
	}
	
	public void makeAccusation() {
		
	}
	
	public Solution createSuggestion() {
		
		
		BoardCell cell = Board.getInstance().getCellAt(getPlayerRow(),getPlayerCol());
		
		ArrayList<Card> mySeenCards = this.getSeenCards();
		
		if(cell.isDoorway()) {
			String roomName = Board.getInstance().getLegend().get(cell.getInitial());
			
			ArrayList<String> possiblePeople = new ArrayList<String>();
			ArrayList<String> possibleWeapons = new ArrayList<String>();
			
			for (Card c: Board.getInstance().getDeck()){
				if(!mySeenCards.contains(c)) {
					if(c.getCardType() == CardType.PERSON) {
						possiblePeople.add(c.getCardName());
					}
					else if(c.getCardType() == CardType.WEAPON) {
						possibleWeapons.add(c.getCardName());
					}
				}
			}
			
			Random rand = new Random();
			int randomIndex = rand.nextInt(possiblePeople.size());
			String personChoice = possiblePeople.get(randomIndex);
			randomIndex = rand.nextInt(possibleWeapons.size());
			String weaponChoice = possibleWeapons.get(randomIndex);

			Solution temp = new Solution(personChoice, roomName , weaponChoice);
			return temp;
		} else {
			return null;
		}
	}
}
