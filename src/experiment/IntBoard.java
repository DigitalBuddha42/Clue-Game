

package experiment;

import java.util.*;

public class IntBoard {
	private BoardCell[][] grid;
	private Map<BoardCell, Set<BoardCell>> adjMtx;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	
	public IntBoard() {
		grid = new BoardCell[4][4];
		calcAdjacencies();
	}
	
	private void calcAdjacencies() {
		
	}
	
	public Set<BoardCell> getAdjList(BoardCell cell) {
		return null;
	}
	
	public void calcTargets(BoardCell startCell, int pathLength) {
		
	}
	
	public Set<BoardCell> getTargets(){
		return null;
	}
	
	public BoardCell getCell(int row, int column) {
		return null;
	}
	

}
