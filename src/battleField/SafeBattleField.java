package battleField;
import java.util.Iterator;


/**
 * @author Cody Mingus
 *
 * This interface defines all the information a BattleField should give without being modifiable.
 */
public interface SafeBattleField {

	/**
	 * @return An iterator of the immutable versions of the Tiles in this BattleField.
	 */
	public Iterator<SafeTile> tiles();
	
	/**
	 * @return An iterator of all the Obstacles on the BattleField (enemies and harmfuls)
	 */
	public Iterator<Obstacle> obstacles();
	
	/**
	 * @return A point containing the Player's coordinates.
	 */
	public Point getPlayerLocation();
	
	/**
	 * @return true if all enemies are dead or true if the Player is dead.
	 */
	public boolean isOver();
	
}
