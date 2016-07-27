package battleField;
import java.util.Random;

/**
 * Odd enemies move randomly about the BattleField.
 * 
 * @author Cody Mingus
 * 
 */
public class Odd extends Thought {

	/**
	 * Movement Type: random Movement Frequency: 34 ticks
	 */
	public Odd() {
		shouldAttack = false;
	}

	public Point getMove(Point self, SafeBattleField sm) {
		Random r = new Random();
		int row = r.nextInt(3);
		int col = r.nextInt(3) + 3;
		if (sm.getPlayerLocation().row == row) {
			shouldAttack = true;
		} else {
			shouldAttack = false;
		}
		return new Point(row, col);
	}

	public int getDelay() {
		return 34;
	}

}
