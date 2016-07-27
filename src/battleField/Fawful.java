package battleField;



/**
 * 
 * @author Denise Werchan
 * 
 * additional family for extra credit :j
 *
 */

public class Fawful extends Family {
	
	private int count;
	
	public Fawful() {
		count = 0;
	}

	@Override
	public int getStartingHealth() {
		return 50;
	}
	
	public boolean canAttack() {
		count++;
		return count >=3;
	}

	@Override
	public Harmful attack() {
		count = 0;
		Bullet b = new Bullet(0);
		return b;
	}
	
	

}
