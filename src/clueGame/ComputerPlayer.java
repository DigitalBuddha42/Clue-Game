package clueGame;

import java.awt.Color;
import java.util.*;

/**
 * @author Sam Mills, Nadia Bixenman
 */
public class ComputerPlayer extends Player {

	public ComputerPlayer(String playerName, int row, int column, Color color) {
		super(playerName, row, column, color);
	}

	public BoardCell pickLocation(Set<BoardCell> targets) {
		
		Random rand = new Random();
		int randomIndex = rand.nextInt(targets.size());
		int index = 0;
		
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
	
	public void createSuggestion() {
		
	}
}
