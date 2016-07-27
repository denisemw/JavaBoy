package battleField;

/**
 * @author Cody Mingus
 *
 * A harmful is a type of attack that can be produced by Obstacles.
 */
public abstract class Harmful extends ComputerControllable implements Locatable {

	protected int team;
	protected Point location;
	
	/**
	 * @return this Harmful's team or the team that can not be harmed by this obstacle.
	 */
	public int getUnharmedTeam() {
		return team;
	}
	
	/**
	 * @return true if the coordinates of this harmful's location are outside of the 3x6 grid area.
	 */
	public boolean hasExited() {
		if (location == null) {
			return true;
		}
		if (location.row < 0 || location.row > 2 || location.col < 0 || location.col > 5) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * @return the amount of damage this Harmful causes.
	 */
	public abstract int getDamage();
	
}
