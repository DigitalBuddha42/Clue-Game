package experiment;

import java.util.*;

/**
 * @author Sam Mills, Nadia Bixenman
 *
 */
public class IntBoard {
	private BoardCell[][] grid;
	private Map<BoardCell, HashSet<BoardCell>> adjMtx;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	
	private static final int BOARD_SIZE = 4; // Constant representing both width and height of Board
	
	/** Constructor for IntBoard - Creates a grid of desired size, initializes the adjacency list, visited list, and target list, and calls calcAdjacencies
	 */
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
	
	/** Calculates the adjacency list
	 */
	private void calcAdjacencies() {
		// For each cell in the board
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
				adjMtx.put(grid[i][j], tempAdj); // Add this cell as a key, and all adjacent and valid cells as the corresponding set of cells
			}
		}
	}
	
	/** Returns the set of adjacent cells to the given cell
	 * @param cell
	 * @return
	 */
	public Set<BoardCell> getAdjList(BoardCell cell) {
		return adjMtx.get(cell);
	}
	
	/** Calculates all valid target cells the given length away from the given cell
	 * @param startCell
	 * @param pathLength
	 */
	public void calcTargets(BoardCell startCell, int pathLength) {
		for (BoardCell c: adjMtx.get(startCell)) {
			visited.add(startCell); // Ensure the starting cell is not revisited
			if (visited.contains(c)) {
				continue; // Do not visit the same cell twice
			}
			visited.add(c);
			if (pathLength == 1) {
				targets.add(c); // If at desired distance from starting cell, a target has been reached
			} else {
				calcTargets(c, pathLength - 1); // If not at desired distance from starting cell, continue
			}
			visited.remove(c);
		}
	}
	
	/** Return set of targets
	 * @return
	 */
	public Set<BoardCell> getTargets(){
		// Clear target list
		Set<BoardCell> tempTargets = new HashSet<BoardCell>(targets);
		targets.clear();
		
		return tempTargets;
	}
	
	/** Return the desired cell
	 * @param row
	 * @param column
	 * @return
	 */
	public BoardCell getCell(int row, int column) {
		return grid[row][column];
	}
	

}
