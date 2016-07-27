package battleField;

/**
 * @author Cody Mingus
 *
 * A Player is type of Obstacle that is controlled by the user.
 */
public class Player implements Obstacle {

	private int health;
	private Point location;
	
	/**
	 * Sets the player's Health to 100 and location to null.
	 */
	public Player() {
		health = 100;
		location = null;
	}
	
	@Override
	public int getHealth() {
		return health;
	}

	/**
	 * Tells the BattleFieldSimulation that the Player wants to attack.
	 */
	public void shoot() {
		BattleFieldSimulation.addPlayerAttack();
	}
	
	/**
	 * Tells the BattleFieldSimulation that the Player wants to move left.
	 */
	public void moveLeft() {
		BattleFieldSimulation.addPlayerMove(new Point(location.row, location.col-1));
		//location = new Point(location.row, location.col-1);
	}
	
	/**
	 * Tells the BattleFieldSimulation that the Player wants to move right.
	 */
	public void moveRight() {
		BattleFieldSimulation.addPlayerMove(new Point(location.row, location.col+1));
		//location = new Point(location.row, location.col+1);
	}
	
	/**
	 * Tells the BattleFieldSimulation that the Player wants to move up.
	 */
	public void moveUp() {
		BattleFieldSimulation.addPlayerMove(new Point(location.row-1, location.col));
		//location = new Point(location.row-1, location.col);
	}
	
	/**
	 * Tells the BattleFieldSimulation that the Player wants to move down.
	 */
	public void moveDown() {
		BattleFieldSimulation.addPlayerMove(new Point(location.row+1, location.col));
		//location = new Point(location.row+1, location.col);
	}
	
	@Override
	public int getTeam() {
		return BattleFieldSimulation.BLUE;
	}

	@Override
	public void receiveDamage(int damage) {
		health -= damage;
		if (health > 100) {
			health = 100;
		} else if (health < 0) {
			health = 0;
		}
	}

	@Override
	public boolean isDead() {
		return health < 1;
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
