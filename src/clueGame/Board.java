package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

/**
 * @author Sam Mills, Nadia Bixenman
 *
 */
public class Board {
	private int numRows;
	private int numColumns;
	public final int MAX_BOARD_SIZE = 50; // Large enough to not be an issue
	private BoardCell[][] board;
	private Map<Character, String> legend;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private Set<BoardCell> targets;
	private String boardConfigFile;
	private String roomConfigFile;
	Set<Character> allInitials;
	

	// Singleton pattern, only one instance of board
	private static Board theInstance = new Board();
	private Board() {
		board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
		// HashMap and HashSet for efficiency and lack of need to be in order
		legend = new HashMap<Character, String>();
		adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
		targets = new HashSet<BoardCell>();
	}
	public static Board getInstance() {
		return theInstance;
	}
	
	
	/** Loads the config files and handles their exceptions
	 */
	public void initialize() {
		try {
			loadRoomConfig();
			loadBoardConfig();
		} catch (FileNotFoundException | BadConfigFormatException e) {
			e.getMessage();
		}
		
	}
	
	/** Reads the legend file, ensuring that it has been correctly read and is correctly formatted, and adds to the legend map
	 * @throws FileNotFoundException
	 * @throws BadConfigFormatException
	 */
	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException{
		FileReader reader = new FileReader(roomConfigFile);
		Scanner in = new Scanner(reader);
		
		while(in.hasNextLine()) {
			String line = in.nextLine();
			char initial = line.charAt(0);
			int index = line.indexOf(",", 3);
			String roomName = line.substring(3, index);
			legend.put(initial, roomName);
			String roomType = line.substring(index + 2);
			if (!roomType.equals("Card") && !roomType.equals("Other")) {
				throw new BadConfigFormatException("Room type " + roomType + " is not Card or Other");
			}
		}
		allInitials = legend.keySet();
	}
	
	/** Reads the layout file, ensuring that it has been correctly read and is formatted correctly, and adds cells to the board, as well as
	 * calls the appropriate BoardCell methods for each type of cell, keeping track of number of columns and rows
	 * @throws FileNotFoundException
	 * @throws BadConfigFormatException
	 */
	public void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException{
		FileReader reader = new FileReader(boardConfigFile);
		Scanner in = new Scanner(reader);
		int row = 0;
		int numRows = 0;
		int length = 0;
		int column = 0;
		int numColumns = 0;
		int firstLength = 0;
		// Each line is a row the length of lines is counted as the number of commas to ensure consistent number of columns per row
		while(in.hasNextLine()) {
			column = 0;
			numColumns = 0;
			String line = in.nextLine();
			length = line.length();
			for (int i = 0; i<length; i++) {
				String roomInitial;
				if (line.charAt(i) != ',') {
					if ((length - i) < 3) {
						roomInitial = line.substring(i);
						i = length - 1;
					} else {
						roomInitial = line.substring(i, (line.indexOf(',', i)));
						i = line.indexOf(',', i);
					}
					if (!allInitials.contains(roomInitial.charAt(0))) {
						throw new BadConfigFormatException("The character " + roomInitial.charAt(0) + " does not correspond to a room in legend");
					}
					board[row][column] = new BoardCell(row, column);
					board[row][column].setInitial(roomInitial);
					
					// Walkways
					if(roomInitial == "W") {
						board[row][column].setWalkway();
					}
					// Doors have initials of two characters
					else if(roomInitial.length() > 1) {
						board[row][column].setDoor(roomInitial);
					}
					else {
						board[row][column].setRoom();
					}
					column++;
					numColumns++;
				}
			}
			if (row == 0) {
				firstLength = numColumns;
			} else if (numColumns != firstLength) {
				throw new BadConfigFormatException("The number of columns in row " + row + " is not equal to the number of rows in row 0");
			}
			row++;
			numRows++;
		}
		this.numRows = numRows;
		this.numColumns = numColumns;
	}
	
	public void calcAdjancencies() {
		
	}
	
	public void calcTargets(BoardCell cell, int pathLength) {
		
	}
	
	/** Sets the names of the layout and legend config files
	 * @param layoutName
	 * @param legendName
	 */
	public void setConfigFiles(String layoutName, String legendName) {
		roomConfigFile = legendName;
		boardConfigFile = layoutName;
	}
	
	public Map<Character, String> getLegend() {
		return legend;
	}
	
	public int getNumRows() {
		return numRows;
	}
	
	public int getNumColumns() {
		return numColumns;
	}
	
	public BoardCell getCellAt(int row, int column) {
		return board[row][column];
	}
	
	public Set<BoardCell> getAdjList(int row, int column) {
		Set<BoardCell> testList = new HashSet<BoardCell>(50);
		testList.add(new BoardCell(-10, -10));
		return testList; // empty but nonzero so all tests successfully fail
	}
	
	public void calcTargets(int row, int column, int pathLength) {
		
	}
	
	public Set<BoardCell> getTargets() {
		Set<BoardCell> testList = new HashSet<BoardCell>(50);
		testList.add(new BoardCell(-10, -10));
		return testList; // empty but nonzero so all tests successfully fail
	}
	
}
