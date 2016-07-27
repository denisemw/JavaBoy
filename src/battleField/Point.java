package battleField;

/**
 * @author Cody Mingus
 *
 * This class just stores two coordinates representing a row and a column.
 */
public class Point {

	/**
	 * This Point's row coordinate.
	 */
	public final int row;
	/**
	 * This Point's column coordinate.
	 */
	public final int col;
	
	/**
	 * @param row the row coordinate this Point should store
	 * @param col the column coordinate this Point should store.
	 */
	public Point(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
}
