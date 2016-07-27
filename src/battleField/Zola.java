package battleField;



/**
 * 
 * @author Denise Werchan
 *
 *additional family -__-
 */

public class Zola extends Family {
	
	private int count;
	
	public Zola() {
		count = 0;
	}
	
	public boolean canAttack() {
		count++;
		return count >= 5;
	}

	@Override
	public int getStartingHealth() {
		return 70;
	}

	@Override
	public Harmful attack() {
		count = 0;
		Shockwave sw = new Shockwave(0);
		return sw;
	}

}
