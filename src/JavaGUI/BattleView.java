package JavaGUI;




import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import battleField.BattleField;
import battleField.Bomb;
import battleField.Bullet;
import battleField.Enemy;
import battleField.Harmful;
import battleField.Obstacle;
import battleField.Player;
import battleField.Shockwave;



public class BattleView extends MasterViewPanel implements Observer, KeyListener {

	private BattleField bf;
	private Player p;
	private JPanel field;
	private JLabel player, health;
	private Map<Object, JLabel> map;
	private boolean isPaused;

	public BattleView(MasterView mv, BattleField bf) {
		super(mv);
		this.bf = bf;
		isPaused = false;
		System.out.print(bf.countObservers());
		map = new HashMap<Object, JLabel>();
		field = new JPanel();
		field.setLayout(null);
		field.setPreferredSize(mv.getSize());
		this.setLayout(new FlowLayout());
		p = bf.getPlayer();
		this.bf.addObserver(this);


		
		setUpHealthLabel();
		setUpField();
		addPlayerAndEnemies();
		
		this.setFocusable(true);
		this.addKeyListener(this);
		this.add(field);
		
		bf.start();
	}
	
	private void setUpField() {
		
		for (int col = 0; col < 6; col++) { // add the blue and red tiles in the proper positions
			for (int row = 0; row < 3; row++) {
				if (col < 3) { // add blue tiles
					JLabel blueTile = new JLabel(new ImageIcon("blueTile.png"));
					blueTile.setSize(120, 85);
					blueTile.setLocation(100*col+20, 60*row+25);
					field.add(blueTile);

				}
				else { // add red tiles
					JLabel redTile = new JLabel(new ImageIcon("redTile.png"));
					redTile.setSize(120, 85);
					redTile.setLocation(100*col+20, 60*row+25);
					field.add(redTile);
				}
			}
		}
			
		
	}

	private void addPlayerAndEnemies() {
		
		int row = p.getLocation().row, col = p.getLocation().col;
		player = new JLabel(new ImageIcon("player.png"));
		player.setSize(120, 85);
		player.setLocation(100*col+20, 60*row);
		field.add(player);
		field.setComponentZOrder(player, 0);
		
		Iterator<Obstacle> obsItr = bf.obstacles();
		
		while (obsItr.hasNext()) {	// iterate through all of the obstacles	
			Obstacle enemy = obsItr.next();
			if (enemy instanceof Enemy) { // if the obstacle is an enemy, create a new jlabel and map it to the enemy to add to the field
				JLabel enemyLabel = new JLabel(new ImageIcon("enemy.png"));
				enemyLabel.setSize(120, 85);
				row = enemy.getLocation().row;
				col = enemy.getLocation().col;
				enemyLabel.setLocation(100*col+20, 60*row); // set the enemy's location
				field.add(enemyLabel); // add the enemy to the JPanel field
				field.setComponentZOrder(enemyLabel, 0);
				map.put(enemy, enemyLabel); // map the enemy to it's jlabel
			
			}

		}

	}

	private void setUpHealthLabel() { // create the health label and add it the this panel
		health = new JLabel(("" + p.getHealth()));
		Font f = new Font("" + p.getHealth(), Font.PLAIN, 32);
		health.setFont(f);
		this.add(health);		
	}
	
	public void keyReleased(KeyEvent arg0) {
		
        if(arg0.getKeyCode() == KeyEvent.VK_DOWN)
            {
        	p.moveDown();
        }
        if(arg0.getKeyCode() == KeyEvent.VK_UP)
            {
        	p.moveUp();
        }
        if(arg0.getKeyCode() == KeyEvent.VK_LEFT)
            {
        	p.moveLeft();
        }
        if(arg0.getKeyCode() == KeyEvent.VK_RIGHT)
            {
        	p.moveRight();
        }
        
        if(arg0.getKeyCode() == KeyEvent.VK_Z)
        {
        	p.shoot();
        }	
        
        if(arg0.getKeyCode() == KeyEvent.VK_X)
        {
        	bf.pause();
        	isPaused = !isPaused;
        	if (isPaused)
			JOptionPane.showMessageDialog(null, "Paused.  Press OK or enter to unpause the game."); //display message that the game is paused
    		int pressedOK = JOptionPane.OK_OPTION;
    		if (pressedOK==0) { // once they press ok or enter, unpause the game
    			bf.pause();
    			isPaused = !isPaused;
    		}

        	

        }	
	}
	

	@Override
	public void update(Observable o, Object arg) {
		if (arg == null) { // if null is passed the game is over
			JOptionPane.showMessageDialog(null, "Game over!"); // display pop up with game over
			mv.changeView(Views.TITLE, null); // change view back to title view
			return;
		}

		health.setText("" + p.getHealth()); // update health
		
		if (arg instanceof Player) { // if it is a player, update it's position
    		int row = p.getLocation().col, col = p.getLocation().row;
    		player.setLocation(100*row+20, 60*col);
		}
		else if (arg instanceof Enemy) {
			Enemy en = (Enemy) arg; 
			if (en.getLocation() == null) { // if the enemy's location is null, remove it from the field
				if (map.containsKey(en)) {
					field.remove(map.get(en));
					map.remove(en);
				}
			}
				
			if (map.containsKey(en)) { // update the enemy's jlabel
	    		int row = en.getLocation().col, col = en.getLocation().row;
				map.get(en).setLocation(100*row+20, 60*col);
			}
		} 
		
		if (arg instanceof Harmful) {
			
			BufferedImage pic = null;
			if (arg instanceof Bullet) { //create bullet image
				try {
					pic = ImageIO.read( new File("bullet.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if (arg instanceof Shockwave) { // create shockwave image
				try {
					pic = ImageIO.read( new File("shockwave.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if (arg instanceof Bomb) { // create bomb image if arg is a bomb
				try {
					pic = ImageIO.read( new File("bomb.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			Harmful harm = (Harmful) arg;
			if (harm.getLocation() == null) { // if the point passed is null, remove the harmful
				if (map.containsKey(harm)) {
					field.remove(map.get(harm));
					map.remove(harm);
				}
			}

			else if (map.containsKey(harm)) { // if the harmful is already one the board, update it's position
				int row = harm.getLocation().row, col = harm.getLocation().col;
					map.get(harm).setLocation(100*col+20, 60*row);
				}
			else { // if the harmful is not already on the board, create a new one and add it
				int row = harm.getLocation().row, col = harm.getLocation().col;
				JLabel aHarmful = new JLabel(new ImageIcon(pic));
				aHarmful.setSize(120, 80);
				aHarmful.setLocation(100*col+20, 60*row);
				field.add(aHarmful);
				field.setComponentZOrder(aHarmful, 0);
				map.put(arg, aHarmful);
			}
			
		}
		
		repaint();

	}
	

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
