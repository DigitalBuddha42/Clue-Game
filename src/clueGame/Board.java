package clueGame;

import java.awt.Color;
import java.io.*;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.*;


/**
 * @author Sam Mills, Nadia Bixenman
 *
 */
public class Board {
	private int numRows;
	private int numColumns;
	public final int MAX_BOARD_SIZE = 50; // Large enough so that it is not an issue
	private BoardCell[][] board;
	private Map<Character, String> legend;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private Set<BoardCell> targets;
	private String boardConfigFile;
	private String roomConfigFile;
	private String playerConfigFile;
	private String weaponConfigFile;
	Set<Character> allInitials;
	Set<BoardCell> visited;
	private Solution theAnswer;
	private ArrayList<Player> allPlayers;
	private Set<Card> deck;  
	private ArrayList<Card> playerDeck;

	// Singleton pattern, only one instance of board
	private static Board theInstance = new Board();
	private Board() {
		board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
		// HashMap and HashSet for efficiency and lack of need to be in order
		legend = new HashMap<Character, String>();
		adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
		targets = new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();
		deck = new HashSet<Card>();
		allPlayers = new ArrayList<Player>();
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
			loadPlayerConfig();
			loadWeaponFile();
		} catch (FileNotFoundException | BadConfigFormatException e) {
			e.getMessage();
		}
		calcAdjancencies();
		
	}
	
	/** Reads the legend file, ensuring that it has been correctly read and is correctly formatted, and adds to the legend map
	 * @throws FileNotFoundException
	 * @throws BadConfigFormatException
	 */
	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException{
		FileReader reader = new FileReader(roomConfigFile);
		Scanner in = new Scanner(reader);
		Card tempCard;
		
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
			if(roomType.equals("Card")) {
				tempCard = new Card(roomName, CardType.ROOM);
				deck.add(tempCard);
			}
		}
		// Create to check for wrong room exceptions
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
				// Parse line
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
					if(roomInitial.charAt(0) == 'W') {
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
			//check that there are the same number of rows and columns on every line
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
	
	public void loadPlayerConfig() throws FileNotFoundException, BadConfigFormatException{
		FileReader reader = new FileReader(playerConfigFile);
		Scanner in = new Scanner(reader);
		
		String line = " ";
		int row;
		int col;
		String name = " ";
		String colorString = " ";
		Color color = null;
		String playerType = " ";
		Card tempCard;
		
		while(in.hasNextLine()) {
			name = in.next();
			colorString = in.next();
			
			// Converting colorString to a java color
			try {
				Field field  = Class.forName("java.awt.Color").getField( colorString );
				color = (Color)field.get(null);
			}catch(Exception e){
				System.out.println( "The color entered for player " + name + " is not a valid color" );
			}
			
			playerType = in.next();
			// Exception to check player is either Human or Computer
			if(!playerType.equals("Human") && !playerType.equals("Computer")) {
				throw new BadConfigFormatException("The player type " + playerType + " is not a valid player type.");
			}
			
			row = Integer.parseInt(in.next());
			col = Integer.parseInt(in.next());
			
			// Add new person card to deck
			tempCard = new Card(name, CardType.PERSON);
			deck.add(tempCard);
			
			//Add new human or computer player to set of players
			if(playerType.equals("Human")){
				allPlayers.add(new HumanPlayer(name, row, col, color));
			}
			else if(playerType.equals("Computer")) {
				allPlayers.add(new ComputerPlayer(name, row, col, color));
			}
		}
		
		in.close();
	}
	
	public void loadWeaponFile() throws FileNotFoundException, BadConfigFormatException{
		FileReader reader = new FileReader(weaponConfigFile);
		Scanner in = new Scanner(reader);
		
		String line = "";
		String weapon = "";
		String type = "";
		int commaIndex = 0;
		Card tempCard;
		
		while(in.hasNextLine()) {
			line = in.nextLine();
			commaIndex = line.indexOf(','); //Find where the comma is to separate the weapon from type
			
			weapon = line.substring(0, commaIndex);
			type = line.substring(commaIndex + 1);
			
			// Check that the weapon is a card
			if(!type.equals("Card")) {
				throw new BadConfigFormatException("The type: " + type + " is not a valid card");
			}
			
			// Adding the weapon to the deck
			tempCard = new Card(weapon, CardType.WEAPON);
			deck.add(tempCard);
		}
		
		in.close();
	}
	
	public void calcAdjancencies() {
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				HashSet<clueGame.BoardCell> tempAdj = new HashSet<clueGame.BoardCell>(); //Temp set to add to adjacency matrix
				//If cell is doorway, add cell in appropriate direction
				if (board[i][j].isDoorway()) {
					if (board[i][j].getDoorDirection() == DoorDirection.UP) {
						tempAdj.add(board[i - 1][j]);
					}
					if (board[i][j].getDoorDirection() == DoorDirection.DOWN) {
						tempAdj.add(board[i + 1][j]);
					}
					if (board[i][j].getDoorDirection() == DoorDirection.RIGHT) {
						tempAdj.add(board[i][j + 1]);
					}
					if (board[i][j].getDoorDirection() == DoorDirection.LEFT) {
						tempAdj.add(board[i][j - 1]);
					}


				} 
				
				if (i > 0) { //Make sure we won't get an out of bounds exception
					// Add cell from doorway
					if (!board[i][j].isRoom() && board[i - 1][j].isDoorway() && board[i - 1][j].getDoorDirection() == DoorDirection.DOWN) {
						tempAdj.add(board[i - 1][j]);
					// Add cell from walkway if it is not a room or doorway
					} else if (!board[i][j].isRoom() && !board[i][j].isDoorway() && board[i - 1][j].isWalkway()) {
						tempAdj.add(board[i - 1][j]);
					}
				}
				if (i < numRows - 1) {//Make sure we won't get an out of bounds exception
					// Add cell from doorway
					if (!board[i][j].isRoom() && board[i + 1][j].isDoorway() && board[i + 1][j].getDoorDirection() == DoorDirection.UP) {
						tempAdj.add(board[i + 1][j]);
						// Add cell from walkway if it is not a room or doorway
					} else if (!board[i][j].isDoorway() && !board[i][j].isRoom() && board[i + 1][j].isWalkway()) {
						tempAdj.add(board[i + 1][j]);
					}
				}
				if (j > 0) {//Make sure we won't get an out of bounds exception
					// Add cell from doorway
					if (!board[i][j].isRoom() && board[i][j - 1].isDoorway() && board[i][j - 1].getDoorDirection() == DoorDirection.RIGHT) {
						tempAdj.add(board[i][j - 1]);
						// Add cell from walkway if it is not a room or doorway
					} else if (!board[i][j].isDoorway() && !board[i][j].isRoom() && board[i][j - 1].isWalkway()) {
						tempAdj.add(board[i][j - 1]);
					}
				}
				if (j < numColumns - 1) {//Make sure we won't get an out of bounds exception
					// Add cell from doorway
					if (!board[i][j].isRoom() && board[i][j + 1].isDoorway() && board[i][j + 1].getDoorDirection() == DoorDirection.LEFT) {
						tempAdj.add(board[i][j + 1]);
						// Add cell from walkway if it is not a room or doorway
					} else if (!board[i][j].isDoorway() && !board[i][j].isRoom() && board[i][j + 1].isWalkway()) {
						tempAdj.add(board[i][j + 1]);
					}
				}
				// Adding cells to adjacency matrix
				adjMatrix.put(board[i][j], tempAdj);
			}
		}
	}
			
	
	/** Sets the names of the layout and legend config files
	 * @param layoutName
	 * @param legendName
	 */
	
	public void setConfigFiles(String layoutName, String legendName) {
		roomConfigFile = legendName;
		boardConfigFile = layoutName;
		playerConfigFile = "CluePlayer.txt";
		weaponConfigFile = "ClueWeapons.txt";
	}
	public void setConfigFiles(String layoutName, String legendName, String playerName, String weaponsName) {
		roomConfigFile = legendName;
		boardConfigFile = layoutName;
		playerConfigFile = playerName;
		weaponConfigFile = weaponsName;
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
		return adjMatrix.get(board[row][column]);
	}
	
	public void calcTargets(int row, int column, int pathLength) {
		for(BoardCell cell : adjMatrix.get(board[row][column])) { //Iterate through adjacency set
			// Add cell to visited matrix
			visited.add(board[row][column]);
			// Don't add cell if its already been visited
			if(visited.contains(cell)) {
				continue;
			}
			// Don't add cell if its a room
			if (cell.isRoom()) {
				continue;
			}
			if (cell.isDoorway()) {
				// Adding cell from doorway based on direction
				if (targetHelper(row,column,cell)) {
					visited.add(cell);
					targets.add(cell);
					continue;
				}
				
			}
			// Adding cell from walkway
			visited.add(cell);
			if (pathLength == 1) {
				targets.add(cell); // If at desired distance from starting cell, a target has been reached
			} else {
				calcTargets(cell.getRow(), cell.getCol(), pathLength - 1); // If not at desired distance from starting cell, continue
			}
			// Remove cell after it has been added to targets
			visited.remove(cell);
		}
	}
	
	public Set<BoardCell> getTargets() {
		Set<BoardCell> tempTargets = new HashSet<BoardCell>();
		tempTargets.addAll(targets); //Create temporary set equal to targets to return
		targets.clear(); // Clear targets for next roll
		return tempTargets;
		
	}
	
	//Helper function to reduce code in calcTargets
	// Checks cells to add from doorway
	public boolean targetHelper(int row, int column, BoardCell cell) {
		// Checks cell is in appropriate direction from door
		if(cell.getDoorDirection() == DoorDirection.DOWN && column == cell.getCol() && row - cell.getRow() == 1) {
			return true;
		}
		if(cell.getDoorDirection() == DoorDirection.UP && column == cell.getCol() && cell.getRow() - row == 1) {
			return true;
		}
		if(cell.getDoorDirection() == DoorDirection.RIGHT && column - cell.getCol() == 1 && row == cell.getRow()) {
			return true;
		}
		if(cell.getDoorDirection() == DoorDirection.RIGHT && cell.getCol() - column == 1 && row == cell.getRow()) {
			return true;
		}
		return false;
	}
	
	/** Chooses the answer at random from the initial deck
	 * 
	 */
	public void selectAnswer() {
		String person = null;
		String weapon = null;
		String room = null;
		Random rand = new Random();
		Set<Card> dealtCards = new HashSet<Card>(); // Keep track of dealt cards
		Set<Card> solutionSet = new HashSet<Card>(); // The set of cards to be made into the Solution object
		while (solutionSet.size() < 3) {
			int cardNum = rand.nextInt(deck.size());
			int currentCard = 0;
			int cardType = 0;
			// Choose a random card of each type
			for (Card c: deck) {
				if (currentCard == cardNum && !dealtCards.contains(c)) {
					if (cardType == 0 && c.getCardType() == CardType.PERSON) {
						solutionSet.add(c);
						cardType++;
						dealtCards.add(c);
					} else if (cardType == 1 && c.getCardType() == CardType.ROOM) {
						solutionSet.add(c);
						cardType++;
						dealtCards.add(c);
					} else if (cardType == 2 && c.getCardType() == CardType.WEAPON) {
						solutionSet.add(c);
						cardType++;
						dealtCards.add(c);
					}
				}
			}
		}
		
		// Set the solution to the three cards chosen
		playerDeck = new ArrayList<Card>(deck);
		for (Card c: solutionSet) {
			if (c.getCardType() == CardType.PERSON) {
				person = c.getCardName();
			} else if (c.getCardType() == CardType.ROOM) {
				room = c.getCardName();
			} else if (c.getCardType() == CardType.WEAPON) {
				weapon = c.getCardName();
			}
			playerDeck.remove(c);
		}
		theAnswer = new Solution(person, room, weapon);
		
	}
	
	/** Deal cards to players
	 * 
	 */
	public void dealCards() {
		Random rand = new Random();
		Collections.shuffle(playerDeck); // randomize the available cards
		int player = 0;
		
		for(Card c: playerDeck) {
			allPlayers.get(player).dealCard(c); // Deal cards one at a time to each player
			player++;
			if(player == 6) { //Loop back to zero index to make sure there is no out of bounds error
				player = 0;
			}
		}

	}
	
	public Card handleSuggestion(Solution suggestion, Player suggester) {
		for (int i = allPlayers.indexOf(suggester) + 1; i < allPlayers.size(); i++) {
			if (allPlayers.get(i).disproveSuggestion(suggestion) != null) {
				return allPlayers.get(i).disproveSuggestion(suggestion);
			}
		}
		for (int i = 0; i < allPlayers.indexOf(suggester); i++) {
			if (allPlayers.get(i).disproveSuggestion(suggestion) != null) {
				return allPlayers.get(i).disproveSuggestion(suggestion);
			}
		}
		return null;
	}
	
	public boolean checkAccusation(Solution accusation) {
		if(accusation.person.equals(theAnswer.person) && accusation.weapon.equals(theAnswer.weapon) && accusation.room.equals(theAnswer.room)) {
			return true;
		}
		return false;
	}
	
	public ArrayList<Player> getPlayers() {
		return allPlayers;
	}
	
	public Set<Card> getDeck(){
		return deck;
	}
	
	public Solution getSolution() {
		return theAnswer;
	}
	
}
