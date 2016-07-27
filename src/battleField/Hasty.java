package battleField;
/**
 * Enemies with Hasty Thoughts try to move in front of the Player in order to
 * attack the Player.
 * 
 * @author Cody Mingus
 * 
 */
public class Hasty extends Thought {

	/**
	 * Movement Type: Go to player row
	 * Movement Frequency: 20 ticks
	 */
	public Hasty() {
		shouldAttack = false;
	}
	
	public Point getMove(Point self, SafeBattleField sm) {
		Point p = sm.getPlayerLocation();
		if (p.row < self.row) {
			if (sm.getPlayerLocation().row == self.row-1) {
				shouldAttack = true;
			} else {
				shouldAttack = false;
			}
			return new Point(self.row - 1, self.col);
		} else if (p.row > self.row) {
			if (sm.getPlayerLocation().row == self.row+1) {
				shouldAttack = true;
			} else {
				shouldAttack = false;
			}
			return new Point(self.row + 1, self.col);
		} else {
			if (sm.getPlayerLocation().row == self.row) {
				shouldAttack = true;
			} else {
				shouldAttack = false;
			}
			return self;
		}
	}

	public int getDelay() {
		return 20;
	}

}
