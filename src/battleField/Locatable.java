package battleField;

/**
 * @author Cody Mingus
 *
 * A locatable object has a location in the form (row,col). This location can be retrieved and changed.
 */
public interface Locatable {

	/**
	 * @param p The Point that contains the new Coordinates for this Locatable
	 */
	public void setLocation(Point p);
	
	/**
	 * @return this Locatable's current location.
	 */
	public Point getLocation();
	
}
