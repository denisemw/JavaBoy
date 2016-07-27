package battleField;



/**
 * 
 * @author Denise Werchan
 *
 *additional family class o_0
 */

public class VillageIdiot extends Family {
	
	private int count;
	
	public VillageIdiot() {
		count = 0;
	}

	public boolean canAttack() {
		count++;
		return count >= 2;
	}
	
	@Override
	public int getStartingHealth() {
		return 10;
	}

	@Override
	public Harmful attack() {
		count = 0;
		Bomb b = new Bomb(0);
		return b;
	}
	

}
