package battleField;

/**
 * @author Cody Mingus
 *
 * A SafeTile is the immutable part of a Tile.
 */
public interface SafeTile {

	/**
	 * @return true if an Obstacle occupies this Tile
	 */
	public boolean hasObstacle();
	
	/**
	 * @return the Obstacle occupying this space. If an Obstacle does not occupy this Tile, null is returned.
	 */
	public Obstacle getObstacle();
	
	/**
	 * @return the Team that this Tile belongs to
	 */
	public int getTeam();
	
	/**
	 * @return The Point containing the row, column coordinates for this Tile.
	 */
	public Point getLocation();
	
}
