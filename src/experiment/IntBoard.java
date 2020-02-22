

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
		System.out.println(cell.getRow() + " " + cell.getCol() + ": ");
		System.out.print("    ");
		for (BoardCell c: adjMtx.get(cell)) {
			System.out.print(c.getRow() + " " + c.getCol() + "   ");
		}
		System.out.println();
		return adjMtx.get(cell);
	}
	
	public void calcTargets(BoardCell startCell, int pathLength) {
		
	}
	
	public Set<BoardCell> getTargets(){
		return null;
	}
	
	public BoardCell getCell(int row, int column) {
		return grid[row][column];
	}
	

}
