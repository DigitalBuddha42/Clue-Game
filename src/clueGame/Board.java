package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Board {
	private int numRows;
	private int numColumns;
	public final int MAX_BOARD_SIZE = 50;
	private BoardCell[][] board;
	private Map<Character, String> legend;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private Set<BoardCell> targets;
	private String boardConfigFile;
	private String roomConfigFile;
	Set<Character> allInitials;
	

	// variable used for singleton pattern
	private static Board theInstance = new Board();
	// constructor is private to ensure only one can be created
	private Board() {
		board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
		legend = new HashMap<Character, String>();
		adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
		targets = new HashSet<BoardCell>();
	}
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}
	
	public void initialize() {
		
	}
	
	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException{
		FileReader reader = new FileReader(roomConfigFile);
		Scanner in = new Scanner(reader);
		
		while(in.hasNextLine()) {
			String line = in.nextLine();
			char initial = line.charAt(0);
			int index = line.indexOf(",", 3);
			String roomName = line.substring(3, index-1);
			legend.put(initial, roomName);
			String roomType = line.substring(index + 2);
			if (roomType != "Card" && roomType != "Other") {
				throw new BadConfigFormatException("Room type " + roomType + " is not Card or Other");
			}
		}
		allInitials = legend.keySet();
	}
	
	public void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException{
		FileReader reader = new FileReader(boardConfigFile);
		Scanner in = new Scanner(reader);
		int row = 0;
		int numRows = 0;
		int length = 0;
		int column = 0;
		int numColumns = 0;
		while(in.hasNextLine()) {
			String line = in.nextLine();
			if(row !=0) {
				if(line.length() != length) {
					throw new BadConfigFormatException("The number of columns in row " + row + " is not equal to the number of rows in row " + (row -1));
				}
			}
			length = line.length();
			for (int i = 0; i<length; i++) {
				System.out.println("Iterating over line");
				String roomInitial;
				column = 0;
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
					
					if(roomInitial.length()>1) {
						board[row][column].setDoor(roomInitial);
					}
					else if(roomInitial == "W") {
						board[row][column].setWalkway();
					}
					else {
						board[row][column].setRoom();
					}
					column++;
					numColumns++;
				}
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
	
	public void setConfigFiles(String layoutName, String legendName) {
		roomConfigFile = legendName;
		boardConfigFile = layoutName;
	}
	
	public Map<Character, String> getLegend() {
		return new HashMap<Character, String>(50);
	}
	
	public int getNumRows() {
		return 0;
	}
	
	public int getNumColumns() {
		return 0;
	}
	
	public BoardCell getCellAt(int row, int column) {
		return new BoardCell(0, 0);
	}
	
}
