package battleField;

/**
 * Shockwaves travel down a row, damaging every Obstacle of the opposing Team it
 * touches.
 * 
 * @author Cody Mingus
 * 
 */
public class Shockwave extends Harmful {

	/**
	 * Creates a Shockwave that travels down one row, damaging all that cross
	 * it's path.
	 * 
	 * @param team
	 *            The team of the creator of this Shockwave
	 */
	public Shockwave(int team) {
		this.team = team;
		location = null;
		delay = 17;
	}

	public int getDamage() {
		return 10; // Shockwaves do ten points of damage
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point p) {
		location = p;
	}

	public Point getMove(SafeBattleField sm) {
		if (team == BattleFieldSimulation.RED) {
			return new Point(location.row, location.col - 1);
		} else {
			return new Point(location.row, location.col + 1);
		}
	}

}
