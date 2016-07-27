package battleField;
/**
 * @author Cody Mingus
 * 
 *         A computer controllable object has a delay that tells the simulation
 *         how often to call the getMove() method. The getMove method figures
 *         out where this ComputerControllable wants to move to next.
 */
public abstract class ComputerControllable {

	protected int delay;

	/**
	 * @return The number of ticks this ComputerControllable object waits before
	 *         acting again.
	 */
	public int getDelay() {
		return delay;
	}

	/**
	 * @param safeModel
	 *            A safe copy of the BattleField this Computer Controllable
	 *            object resides in.
	 * @return the coordinates of where this object wants to move to next.
	 */
	public abstract Point getMove(SafeBattleField safeModel);

}
