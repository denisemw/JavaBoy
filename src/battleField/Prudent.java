package battleField;



/**
 * 
 * @author Denise Werchan
 *
 *an extra thought ._.
 */

public class Prudent extends Thought {

	@Override
	public Point getMove(Point self, SafeBattleField safeModel) {
		return new Point(self.row - 1, self.col - 1);
	}

	@Override
	public int getDelay() {
		return 50;
	}

}
