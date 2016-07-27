package battleField;

/**
 * The part of the enemy that controls where the enemy should move to, how often
 * to move, and whether or not to attack.
 * 
 * @author Cody Mingus
 */
public abstract class Thought {

	protected boolean shouldAttack;

	/**
	 * @return true if in a good attacking position.
	 */
	public boolean shouldAttack() {
		return shouldAttack;
	}

	/**
	 * Returns where the enemy associated with this thought should move. The
	 * destination is determined by the specific thought.
	 * 
	 * @param self
	 *            the Point where this Enemy is currently placed.
	 * @param sm
	 *            the SafeModel used to decide where to go.
	 * @return the Point that this enemy should move to.
	 */
	public abstract Point getMove(Point self, SafeBattleField sm);

	/**
	 * @return the amount of time in ticks that should pass before the enemy
	 *         associated with this thought moves again.
	 */
	public abstract int getDelay();

}
