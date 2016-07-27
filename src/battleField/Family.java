package battleField;

/**
 * A Family controls how an Enemy attacks and how much health it starts out
 * with.
 * 
 * @author Cody Mingus
 * 
 */
public abstract class Family {

	protected boolean canAttack;

	/**
	 * This is exists to make it possible to have two different intervals for
	 * attacking and moving. An enemy can move every x ticks, and attack every y
	 * ticks.
	 * 
	 * @return true if this Enemy can attack
	 */
	public boolean canAttack() {
		return canAttack();
	}

	/**
	 * @return the Type of attack this Family produces.
	 */
	public abstract Harmful attack();

	/**
	 * @return the starting health for this type of Family.
	 */
	public abstract int getStartingHealth();

}
