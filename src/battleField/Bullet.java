package battleField;
import java.util.Iterator;

/**
 * A bullet travels along a row and hits the first Obstacle that crosses it's
 * path.
 * 
 * @author Cody Mingus
 * 
 */
public class Bullet extends Harmful {

	private boolean hasAttacked;

	/**
	 * A bullet hits the first Obstacle on the same row.
	 * 
	 * @param team
	 *            The team of the creator of this bullet.
	 */
	public Bullet(int team) {
		this.team = team;
		location = null;
		// bullets should travel as fast as possible
		delay = 1;
		hasAttacked = false;
	}

	public int getDamage() {
		hasAttacked = true;
		// bullets always do two points of damage
		return 2;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point p) {
		location = p;
	}

	public Point getMove(SafeBattleField sm) {
		if (hasAttacked) {
			return null;
		}
		Iterator<Obstacle> itr = sm.obstacles();
		Obstacle target = null;
		while (itr.hasNext()) {
			Obstacle o = itr.next();
			if (o.getLocation().row == location.row) {
				// Blue team shoots to the right
				if (team == BattleFieldSimulation.BLUE) {
					if (o.getLocation().col > location.col) {// target must be
																// in front of
																// this
						if (target == null) {
							target = o;
						} else {
							if (o.getLocation().col < target.getLocation().col) {
								target = o;
							}
						}
					}
					// Red team shoots to the left
				} else {
					if (o.getLocation().col < location.col) {
						if (target == null) {
							target = o;
						} else {
							if (o.getLocation().col > target.getLocation().col) {
								target = o;
							}
						}
					}
				}
			}
		}
		if (target == null) {
			return null;
		}
		return target.getLocation();
	}

}
