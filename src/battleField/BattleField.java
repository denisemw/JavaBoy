package battleField;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Random;


/**
 * A Battlefield is composed of many Tiles. With a reference to each Tile, the
 * battlefield can control interaction between the inhabitants of the Tiles
 * (Obstacles and Harmfuls) This class offers information about it's current
 * state and allows the state to be changed. No Harmful or Obstacle should have
 * a reference to a BattleField object.
 * 
 * @author Cody Mingus
 * 
 */
public class BattleField extends Observable implements SafeBattleField {

	private Tile[][] field;
	private Player player;

	/**
	 * Upon creation, a BattleField populates itself with 18 Tiles in a 3 x 6
	 * formation. The left half of these Tiles are Blue, and the right half are
	 * Red. The Player is then placed in the center Tile of the Blue area. 1 - 3
	 * enemies are created then randomly placed within the red area. Each
	 * obstacle gets added to the BattleFieldSimulation, then the
	 * BattleFieldSimulation gets started.
	 * 
	 * @param p
	 *            The human controlled player
	 */
	public BattleField(Player p) {
		// Make the field
		field = new Tile[3][6];
		for (int r = 0; r < 3; r++) {
			// Make the blue side
			for (int c = 0; c < 3; c++) {
				field[r][c] = new Tile(new Point(r, c), BattleFieldSimulation.BLUE);
			}
			// Make the red side
			for (int c = 3; c < 6; c++) {
				field[r][c] = new Tile(new Point(r, c), BattleFieldSimulation.RED);
			}
		}

		// add the player
		player = p;
		field[1][1].add(p);

		// add the enemies
		Random r = new Random();
		int numEnemies = r.nextInt(3) + 1;
		while (numEnemies > 0) {
			int row = r.nextInt(3);
			int col = r.nextInt(3) + 3;
			Enemy e = new Enemy();
			if (field[row][col].add(e)) {
				numEnemies--;
				BattleFieldSimulation.add(e); // add to the simulation
			}
		}
		System.out.println("" + countObservers());
	}

	/**
	 * Moves a harmful from it's previous Tile to the Tile located at the passed
	 * Point. If the destination Point is out of bounds, the Harmful will be
	 * removed. If the destination is null, the Harmful will be removed. If the
	 * destination is already populated with a Harmful, this Harmful will be
	 * removed.
	 * 
	 * @param h
	 *            The harmful moving
	 * @param p
	 *            The destination point
	 */
	public void move(Harmful h, Point p) {
		if (p == null) {
			Point curr = h.getLocation();
			field[curr.row][curr.col].removeHarmful();
			h.setLocation(null);
		} else if (p.row > 2 || p.row < 0 || p.col < 0 || p.col > 5) {
			Point curr = h.getLocation();
			field[curr.row][curr.col].removeHarmful();
			h.setLocation(null);
		} else {
			Point old = h.getLocation();
			if (!field[p.row][p.col].add(h)) {
				h.setLocation(null);
			}
			if (old != null) {
				field[old.row][old.col].removeHarmful();
			}
		}
		setChanged();
		notifyObservers(h);
	}

	/**
	 * Moves the passed obstacle from it's current Tile to the Tile associated
	 * with the point passed. If the destination point is out of bounds, the
	 * obstacle will not move. If the destination point is already populated by
	 * an Obstacle, the Obstacle will not move. If the destination Tile's team
	 * doesn't match the Obstacle's team, the Obstacle will not move. If the
	 * Obstacle's destination point is null, the Obstacle will be removed.
	 * 
	 * @param o
	 *            The Obstacle moving
	 * @param p
	 *            The destination Point
	 */
	public void move(Obstacle o, Point p) {
		if (p == null) {
			field[o.getLocation().row][o.getLocation().col].removeObstacle();
			o.receiveDamage(999);
			o.setLocation(null);
			setChanged();
			notifyObservers(o);
			return;
		}
		if (p.row > 2 || p.row < 0 || p.col < 0 || p.col > 5) {
			return;
		} else { // And the point is in bounds
			Point old = o.getLocation();
			if (field[p.row][p.col].add(o)) {
				field[old.row][old.col].removeObstacle();
				o.setLocation(p);
			}
		}
		setChanged();
		notifyObservers(o);
	}

	public Iterator<SafeTile> tiles() {
		return new TileIterator();
	}

	public Iterator<Obstacle> obstacles() {
		return new ObstacleIterator();
	}

	public Point getPlayerLocation() {
		return player.getLocation();
	}

	/**
	 * A SafeModel contains information on where obstacles are in the
	 * battlefield and the state that Tiles are in.
	 * 
	 * @return a SafeModel with information about this BattleField's state.
	 */
	public SafeBattleField safeBattleField() {
		return new SafeModel();
	}

	/**
	 * @return true if all members of one of the teams (red or blue) have died.
	 */
	public boolean isOver() {
		if (player.getHealth() < 1) {
			setChanged();
			notifyObservers(null);
			return true;
		} else {
			Iterator<Obstacle> itr = obstacles();
			itr.remove();
			if (itr.hasNext()) {
				return false;
			} else {
				setChanged();
				System.out.println("dead");
				notifyObservers(null);
				return true;
			}
		}
	}

	/**
	 * Starts the BattleFieldSimulation
	 */
	public void start() {
		BattleFieldSimulation.start(this);
	}

	/**
	 * Pauses/Unpauses the BattleFieldSimulation
	 */
	public void pause() {
		BattleFieldSimulation.pause();
	}

	private class SafeModel implements SafeBattleField {

		public Iterator<SafeTile> tiles() {
			return new TileIterator();
		}

		public Iterator<Obstacle> obstacles() {
			return new ObstacleIterator();
		}

		public Point getPlayerLocation() {
			return player.getLocation();
		}

		@Override
		public boolean isOver() {
			return BattleField.this.isOver();
		}

	}

	private class TileIterator implements Iterator<SafeTile> {

		private LinkedList<SafeTile> list;

		/**
		 * Creates an iterator containing the Tiles of the BattleField
		 */
		public TileIterator() {
			list = new LinkedList<SafeTile>();
			for (int r = 0; r < 3; r++) {
				for (int c = 0; c < 6; c++) {
					list.add(field[r][c].safeTile());
				}
			}
		}

		public boolean hasNext() {
			return !list.isEmpty();
		}

		public SafeTile next() {
			return list.remove();
		}

		public void remove() {
			list.remove();
		}

	}

	private class ObstacleIterator implements Iterator<Obstacle> {

		private LinkedList<Obstacle> list;

		/**
		 * Creates an iterator containing all Obstacles on the BattleField.
		 */
		public ObstacleIterator() {
			list = new LinkedList<Obstacle>();
			Iterator<SafeTile> itr = new TileIterator();
			while (itr.hasNext()) {
				SafeTile tile = itr.next();
				if (tile.hasObstacle()) {
					list.add(tile.getObstacle());
				}
			}
		}

		public boolean hasNext() {
			return !list.isEmpty();
		}

		public Obstacle next() {
			return list.remove();
		}

		public void remove() {
			list.remove();
		}

	}

	public String toString() {
		String str = "*******************" + '\n';
		for (int r = 0; r < 3; r++) {
			for (int c = 0; c < 6; c++) {
				str += '|' + field[r][c].toString();
			}
			str += "|\n";
		}
		str += "*******************";
		return str;
	}

	/**
	 * @return the Player of this battle
	 */
	public Player getPlayer() {
		return player;
	}

}
