package experiment;

/**
 * @author Sam Mills, Nadia Bixenman
 *
 */
public class BoardCell {
	private int row;
	private int column;
	
	/** BoardCell constructor
	 * @param row
	 * @param column
	 */
	public BoardCell(int row, int column) {
		this.row = row;
		this.column = column;
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
}
