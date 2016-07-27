package battleField;

/**
 * @author Cody Mingus
 *
 * An obstacle is a movable entity that can die, and has a team. 
 */
public interface Obstacle extends Locatable {

	/**
	 * @return this Obstacle's current health
	 */
	public int getHealth();
	
	/**
	 * @return this Obstacle's team
	 */
	public int getTeam();
	
	/**
	 * subtracts the passed value from health.
	 * 
	 * @param damage the quantity in which health is being reduced by
	 */
	public void receiveDamage(int damage);
	
	/**
	 * @return true if health is less than or equal to zero.
	 */
	public boolean isDead();
	
}
