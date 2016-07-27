package battleField;
/**
 * @author Cody Mingus
 * 
 *         A Tile represents the smallest unit on the Battlefield. It can
 *         contain up to 1 Harmful AND up to 1 Obstacle at any given moment.
 *         Tiles belong to certain teams. Obstacles of opposing teams can not be
 *         stored in this Tile. Any harmful can be stored in this Tile.
 */
public class Tile implements SafeTile {

	private Obstacle o;
	private Harmful h;
	private int team;
	private Point location;

	/**
	 * Creates a new Tile at the specified location and sets it's team to the
	 * passed parameter. The tile starts without any Obstalces or Harmfuls.
	 * 
	 * @param p
	 *            the location of this Tile on the BattleField.
	 * @param team
	 *            the team (red or blue) being assigned to this Tile.
	 */
	public Tile(Point p, int team) {
		o = null;
		h = null;
		location = p;
		this.team = team;
	}

	/**
	 * Adds an Obstacle to this Tile provided that the Obstalce's team and the
	 * Tile's team match, and No Obstacle is already contained in this Tile.
	 * When added, the Obstacles location is set to the Tiles. If a Harmful of
	 * the opposing team is contained in this Tile, the Obstacle takes damage
	 * from it. If the Harmful killed the Obstacle, the Obstacle is removed
	 * 
	 * @param toAdd
	 *            The Obstacle being added to this Tile
	 * @return true if the Obstacle was added (even if it died afterwards)
	 */
	public boolean add(Obstacle toAdd) {
		if (toAdd.getTeam() == team) {
			if (o == null) {
				o = toAdd;
				o.setLocation(location);
				checkCollision();
				if (o.isDead()) {
					o = null;
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * Adds the passed Harmful to this Tile provided that no Harmful is already
	 * contained in this Tile. If an Obstacle of the opposite team is contained
	 * in this Tile, it will take damage. If it dies, it is removed from the
	 * Tile.
	 * 
	 * @param toAdd
	 *            the Harmful being added to this Tile
	 * @return true if the Harmful was added.
	 */
	public boolean add(Harmful toAdd) {
		if (h == null) {
			h = toAdd;
			h.setLocation(location);
			checkCollision();
			if (o != null && o.isDead()) {
				o = null;
			}
			return true;
		}
		return false;
	}

	/**
	 * If there is a Harmful contained in this Tile, it is removed.
	 * 
	 * @return true if a Harmful was removed.
	 */
	public boolean removeHarmful() {
		if (h == null) {
			return false;
		} else {
			h = null;
			return true;
		}
	}

	/**
	 * If this Tile contains an Obstacle, it is removed
	 * 
	 * @return true if an Obstacle was removed
	 */
	public boolean removeObstacle() {
		if (o == null) {
			return false;
		} else {
			o = null;
			return true;
		}
	}

	private void checkCollision() {
		if (o != null && h != null) { // if this Tile contains both.
			if (o.getTeam() != h.getUnharmedTeam()) { // and they're of opposite
														// teams
				o.receiveDamage(h.getDamage()); //Damage the obstacle
			}
		}
	}

	@Override
	public boolean hasObstacle() {
		return o != null;
	}

	@Override
	public Obstacle getObstacle() {
		return o;
	}

	@Override
	public int getTeam() {
		return team;
	}

	@Override
	public Point getLocation() {
		return location;
	}

	/**
	 * @return An immutable version of this Tile.
	 */
	public SafeTile safeTile() {
		return new safeModel();
	}

	private class safeModel implements SafeTile {

		@Override
		public boolean hasObstacle() {
			return o != null;
		}

		@Override
		public Obstacle getObstacle() {
			return o;
		}

		@Override
		public int getTeam() {
			return team;
		}

		@Override
		public Point getLocation() {
			return location;
		}

	}

}
