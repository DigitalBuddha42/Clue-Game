

package experiments;

import java.util.*;

public class IntBoard {
	private BoardCell[][] grid;
	private Map<BoardCell, Set<BoardCell>> adjMtx;
	
	public IntBoard() {
		grid = new BoardCell[4][4];
		calcAdjacencies();
	}
	
	private void calcAdjacencies() {
		
	}
	
	public Set<BoardCell> getAdjList() {
		return null;
	}
	
	public void calcTargets(int startCell, int pathLength) {
		
	}
	
	public Set<BoardCell> getTargets(){
		return null;
	}
	
	public BoardCell getCell(int row, int column) {
		return null;
	}
	

}
