package battleField;
import java.util.Iterator;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * The simulation used to run the Battle System. All methods in this class are
 * static
 * 
 * @author Cody Mingus
 * 
 */
public class BattleFieldSimulation {

	private static SimulationObject sim = new SimulationObject();
	private static boolean hasStarted = false;
	
	/**
	 * The red team
	 */
	public static final int RED = 0;
	/**
	 * The blue team
	 */
	public static final int BLUE = 1;

	/**
	 * 
	 * This method adds a either a Harmful Object or a Obstacle Object to the
	 * simulation so that it's code will be executed on it's given interval.
	 * After starting the simulation, this method should not be called.
	 * 
	 * @param cc
	 *            The ComputerControllable Object to be added.
	 */
	public static void add(ComputerControllable cc) {
		if (!hasStarted) {
			Container c = new Container(cc, sim.getCurrentTick()
					+ cc.getDelay());
			sim.add(c);
		}
	}

	/**
	 * This method should be called when the user wants the player to attack.
	 * This will queue a Bullet Harmful to be executed from the space just right
	 * of the players position.
	 */
	public static void addPlayerAttack() {
		Container c = new Container(new PlayerControllable(sim.getPlayer()
				.getLocation()), sim.getCurrentTick() + 1);
		sim.add(c);
	}

	/**
	 * This method queues a movement from one tile to another for the player.
	 * The player's destination is determined by the point passed as this
	 * method's argument.
	 * 
	 * @param p
	 */
	public static void addPlayerMove(Point p) {
		Container c = new Container(new PlayerControllable(p),
				sim.getCurrentTick() + 1);
		sim.add(c);
	}

	/**
	 * This method starts the simulation. This method should be called after the
	 * enemies have been added to the simulation. The Player doesn't need to be
	 * added to the simulation and Harmfuls will be added by the simulation as
	 * it runs.
	 * 
	 * @param bf
	 *            - The battlefield this simulation will manipulate.
	 */
	public static void start(BattleField bf) {
		sim.setBattleField(bf);
		Iterator<Obstacle> itr = bf.obstacles();
		while (itr.hasNext()) {
			Obstacle o = itr.next();
			if (o instanceof Player) {
				sim.setPlayer((Player) o);
				hasStarted = true;
				sim.start();
				return;
			}
		}
		try {
			throw new Exception("No Player found on the BattleField");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Pauses the simulation
	 */
	public static void pause() {
		sim.pause();
	}

	/**
	 * Do not call this method.
	 */
	public static void reset() {
		sim = new SimulationObject();
		hasStarted = false;
	}

	/*
	 * Contains the queue of actions for the battle. Actions are processed along
	 * "Ticks" which occur every 30 milliseconds. This results in about 33 ticks
	 * per second.
	 */
	private static class SimulationObject extends Thread {

		private PriorityBlockingQueue<Container> queue;
		private volatile int currentTick;
		private BattleField bf;
		private volatile Player player;
		private boolean active;
		private boolean paused;

		/*
		 * Initializes the queue and starts the tick counter. The name passed is
		 * the name shown is stack traces when an exception is thrown in this
		 * thread.
		 */
		public SimulationObject() {
			queue = new PriorityBlockingQueue<Container>();
			currentTick = 0;
			paused = false;
		}

		/*
		 * Sets the player used for player operations (shooting and moving)
		 */
		public void setPlayer(Player player) {
			this.player = player;
		}

		/*
		 * returns the player used for player operations
		 */
		public Player getPlayer() {
			return player;
		}

		/*
		 * returns true if the battle is not over. Becomes false when the battle
		 * is over. Becomes true again if a new battle starts.
		 */
		@SuppressWarnings("unused")
		public boolean active() {
			return active;
		}

		/*
		 * sets the battlefield that will be manipulated during the simulation
		 */
		public void setBattleField(BattleField bf) {
			this.bf = bf;
		}

		/*
		 * Adds the passed container to the queue
		 */
		public void add(Container c) {
			if (!paused) {
				queue.add(c);
			}
		}

		/*
		 * returns the current tick of the simulation
		 */
		public int getCurrentTick() {
			return currentTick;
		}

		public void pause() {
			paused = !paused;
		}

		public void run() {
			active = true;
			while (!bf.isOver()) {
				if (!paused) {
					wake();
				}

				 //System.out.print("\n\n" + bf.toString());

				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			cleanUp();
		}

		private void wake() {
			while (queue.peek().getTick() == currentTick) {
				try {
					Container c = queue.take();
					// Player processing
					if (c.getCC() instanceof PlayerControllable) {
						Point p = c.getCC().getMove(null);
						if (p.row == player.getLocation().row
								&& p.col == player.getLocation().col) {
							Harmful h = new Bullet(BattleFieldSimulation.BLUE);
							bf.move(h, player.getLocation());
							if (h.getLocation() != null) {
								bf.move(h, h.getMove(bf.safeBattleField()));
								Container temp = new Container(h,
										currentTick + 1);
								queue.add(temp);
							}
						} else {
							bf.move(player, p);
						}
					} else {
						// Enemy processing
						try {
							Enemy e = (Enemy) c.getCC();
							if (!e.isDead()) {
								bf.move(e, e.getMove(bf.safeBattleField()));
								Harmful attack = e.attack();
								if (attack != null) {
									bf.move(attack, e.getLocation());
									if (attack.getLocation() != null) {
										bf.move(attack, attack.getMove(bf
												.safeBattleField()));
										Container temp = new Container(attack,
												currentTick + attack.getDelay());
										queue.add(temp);
									}
								}
								c.setTick(currentTick + e.getDelay());
								queue.add(c);
							} else {
								bf.move(e, e.getMove(bf.safeBattleField()));
							}
							// Harmful processing
						} catch (ClassCastException e) {
							Harmful h = (Harmful) c.getCC();
							if (!h.hasExited()) {
								bf.move(h, h.getMove(bf.safeBattleField()));
								c.setTick(currentTick + h.getDelay());
								queue.add(c);
							}
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			// If the simulation somehow skipped
			if (queue.peek().getTick() < currentTick) {
				currentTick = queue.peek().getTick() - 1;
			} else {
				currentTick++;
			}

		}

		private void cleanUp() {
			queue = null;
			currentTick = 0;
			player = null;
			bf = null;
			active = false;
			BattleFieldSimulation.reset();
		}

	}

	/*
	 * Allows player actions to be added to the queue, even though the player is
	 * not computer controllable.
	 */
	private static class PlayerControllable extends ComputerControllable {

		private Point p;

		/*
		 * Takes a point argument for where the player should be moving to. If
		 * the player is shooting rather than moving, the player's current
		 * location should be passed. Do not pass null as it will cause this to
		 * not get processed.
		 */
		public PlayerControllable(Point p) {
			delay = 1;
			this.p = p;
		}

		public Point getMove(SafeBattleField sm) {
			return p;
		}

	}

	/*
	 * A container holds computer controllables and an integer together. This
	 * integer is the tick when this computer controllable object should be
	 * executed next.
	 */
	private static class Container implements Comparable<Container> {

		private ComputerControllable cc;
		private int tick;

		/*
		 * Takes a computer controllable and a tick as an argument. The passed
		 * tick should be higher than the currentTick in the Simulation.
		 */
		public Container(ComputerControllable cc, int tick) {
			this.cc = cc;
			this.tick = tick;
		}

		/*
		 * Allows the tick of a currently existing container to be changed.
		 */
		public void setTick(int newTick) {
			tick = newTick;
		}

		/*
		 * The tick that this container should be executed on.
		 */
		public int getTick() {
			return tick;
		}

		/*
		 * return the computer controllable object stored in this container
		 */
		public ComputerControllable getCC() {
			return cc;
		}

		public int compareTo(Container other) {
			return this.tick - other.tick;
		}
	}

}
