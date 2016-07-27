package battleField;

/**
 * Enemies are randomly generated upon creation. They have two parts: a Family,
 * and a Thought. Family determines how an Enemy attacks and how much health it
 * has. Thought determines how and how often an Enemy moves.
 * 
 * @author Cody Mingus
 * 
 */
public class Enemy extends ComputerControllable implements Obstacle {

	private int health;
	private Point location;
	private Family family;
	private Thought thought;

	/**
	 * Randomly chooses a family and thought for this enemy. A health value is
	 * set from there and the location is set to null.
	 */
	public Enemy() {
		if (Math.random() < .5) {
			family = new Simpleton();
		} else {
			family = new Berserker();
		}
		if (Math.random() < .5) {
			thought = new Odd();
		} else {
			thought = new Hasty();
		}
		health = family.getStartingHealth();
		location = null;
		delay = thought.getDelay();
	}

	public int getHealth() {
		return health;
	}

	public int getTeam() {
		return BattleFieldSimulation.RED;
	}

	public void receiveDamage(int damage) {
		health -= damage;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point p) {
		location = p;
	}

	public Point getMove(SafeBattleField sm) {
		if (isDead()) {
			return null;
		} else {
			return thought.getMove(location, sm);
		}
	}

	/**
	 * 
	 * @return The type of attack this Enemy produces. If null, no attack will
	 *         be produced.
	 */
	public Harmful attack() {
		if (thought.shouldAttack()) {
			if (family.canAttack()) {
				return family.attack();
			}
		}
		return null;
	}

	public boolean isDead() {
		if (health < 1) {
			return true;
		} else {
			return false;
		}
	}

}
