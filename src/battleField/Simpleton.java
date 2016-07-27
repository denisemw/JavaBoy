package battleField;


/**
 * Enemies of this Family produce shockwaves and begin with 40 points of health
 * 
 * @author Cody Mingus
 * 
 */
public class Simpleton extends Family {

	private int count;

	/**
	 * Attack Produced: Shockwave Attack Frequency: 3 moves
	 */
	public Simpleton() {
		canAttack = false;
		count = 0;
	}

	public boolean canAttack() {
		if (count >= 2) {
			canAttack = true;
		}
		count++;
		return canAttack;
	}

	public Harmful attack() {
		canAttack = false;
		count = 0;
		return new Shockwave(BattleFieldSimulation.RED);
	}

	public int getStartingHealth() {
		return 40;
	}

}
