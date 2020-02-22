

package experiment;

import java.util.*;

public class IntBoard {
	private BoardCell[][] grid;
	private Map<BoardCell, HashSet<BoardCell>> adjMtx;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	
	private static final int BOARD_SIZE = 4;
	
	public IntBoard() {
		grid = new BoardCell[BOARD_SIZE][BOARD_SIZE];
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				grid[i][j] = new BoardCell(i, j);
			}
		}
		
		adjMtx = new HashMap<BoardCell, HashSet<BoardCell>>();
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		
		calcAdjacencies();
	}
	
	private void calcAdjacencies() {
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				HashSet<BoardCell> tempAdj = new HashSet<BoardCell>();
				if (i > 0) {
					tempAdj.add(grid[i - 1][j]);
				}
				if (i < BOARD_SIZE - 1) {
					tempAdj.add(grid[i + 1][j]);
				}
				if (j > 0) {
					tempAdj.add(grid[i][j - 1]);
				}
				if (j < BOARD_SIZE - 1) {
					tempAdj.add(grid[i][j + 1]);
				}
				adjMtx.put(grid[i][j], tempAdj);
			}
		}
	}
	
	public Set<BoardCell> getAdjList(BoardCell cell) {
		return adjMtx.get(cell);
	}
	
	public void calcTargets(BoardCell startCell, int pathLength) {
		for (BoardCell c: adjMtx.get(startCell)) {
			visited.add(startCell);
			if (visited.contains(c)) {
				continue;
			}
			visited.add(c);
			if (pathLength == 1) {
				targets.add(c);
			} else {
				calcTargets(c, pathLength - 1);
			}
			visited.remove(c);
		}
	}
	
	public Set<BoardCell> getTargets(){
		Set<BoardCell> tempTargets = new HashSet<BoardCell>(targets);
		targets.clear();
		return tempTargets;
	}
	
	public BoardCell getCell(int row, int column) {
		return grid[row][column];
	}
	

}
