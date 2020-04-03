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
		Solution temp = new Solution("", "", "");
		return temp;
	}
}
