package battleField;
/**
 * 
 * @author Cody Mingus
 * 
 * The bomb is a type of harmful that moves three spaces towards the opposing territory, and does 25 points of damage.
 */
public class Bomb extends Harmful {

	/**
	 * @param team the Team of the Obstacle that made this Bomb.
	 */
	public Bomb(int team) {
		this.team = team;
		this.location = null;
		delay = 50;
	}
	
	@Override
	public int getDamage() {
		return 25;
	}

	@Override
	public Point getMove(SafeBattleField safeModel) {
		if (team == BattleFieldSimulation.RED) {
			return new Point(location.row, location.col-3);
		} else {
			return new Point(location.row, location.col+3);
		}
	}

	@Override
	public void setLocation(Point p) {
		location = p;
	}

	@Override
	public Point getLocation() {
		return location;
	}

}
