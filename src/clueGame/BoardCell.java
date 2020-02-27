package clueGame;

/**
 * @author Sam Mills, Nadia Bixenman
 *
 */
public class BoardCell {
	private int row;
	private int column;
	private String roomInitial;
	private DoorDirection direction;
	private boolean isWalkway;
	private boolean isRoom;
	private boolean isDoorway;
	
	/** BoardCell constructor
	 * @param row
	 * @param column
	 */
	public BoardCell(int row, int column) {
		this.row = row;
		this.column = column;
		isWalkway = false;
		isDoorway = false;
		isRoom = false;
	}
	
	/** Returns integer row
	 * @return
	 */
	public int getRow() {
		return this.row;
	}
	
	/** Returns integer column
	 * @return
	 */
	public int getCol() {
		return this.column;
	}
	
	public boolean isWalkway() {
		return isWalkway;
	}
	
	public boolean isRoom() {
		return isRoom;
	}
	
	public boolean isDoorway() {
		return isDoorway;
	}
	
	public DoorDirection getDoorDirection() {
		return null;
	}
	
	public String getInitial() {
		return roomInitial;
	}
	
	public void setInitial(String initial) {
		roomInitial = initial;
	}
	
	public void setWalkway() {
		isWalkway = true;
	}
	
	public void setRoom() {
		isRoom = true;
	}
	
	public void setDoor(String door) {
		char directionChar = door.charAt(1);
		if(directionChar == 'R') {
			direction = DoorDirection.RIGHT;
		}
		if(directionChar == 'L') {
			direction = DoorDirection.LEFT;
		}
		if(directionChar == 'U') {
			direction = DoorDirection.UP;
		}
		if(directionChar == 'D') {
			direction = DoorDirection.DOWN;
		}
		isDoorway = true;
	}
	
}
