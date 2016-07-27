package JavaGUI;



import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import battleField.BattleField;
import battleField.Player;


public class TitleView extends MasterViewPanel {


	private JButton[] buttons; // array of JButtons to hold the new game and exit button

	public TitleView(MasterView mv) {
		super(mv);
		this.setLayout(new GridLayout(3, 0));
		
		buttons = new JButton[2];
		
		buttons[0] = new JButton("Exit"); // buttons[0] will hold the exit button
		buttons[0].addActionListener(new ButtonListenerExit()); // adds button listener to switch to battleview and start new game
		buttons[0].addKeyListener(new KeyListenerEnter()); // the KeyListenerEnter will listen if the user presses enter
		buttons[0].addKeyListener(new KeyListenerSelect()); // the KeyListenerSelect will allow the user to select between buttons


		
		BufferedImage title = null;
		try {
			title = ImageIO.read( new File("javaBoy.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JLabel picLabel = new JLabel(new ImageIcon(title));
		this.add(picLabel);
		
		buttons[1] = new JButton("New Game"); // buttons[1] will hold the newGame button
		buttons[1].addActionListener(new ButtonListenerNewGame()); // adds button listener to switch to battleview and start new game
		buttons[1].addKeyListener(new KeyListenerEnter()); // the KeyListenerEnter will listen if the user presses enter
		buttons[1].addKeyListener(new KeyListenerSelect()); // the KeyListenerSelect will allow the user to select between buttons
				
		this.add(buttons[1]); // add the buttons to the panel
		this.add(buttons[0]);
		
		mv.setFocusable(true);
		mv.addKeyListener(new KeyListenerSelect());
	}
	
	private class ButtonListenerNewGame  implements ActionListener {
		// No constructor needed here
		// Must have this method to implement ActionListener
		@Override
		public void actionPerformed(ActionEvent arg0) {
			mv.changeView(Views.BATTLE, new BattleField(new Player()));
			// TODO Auto-generated method stub
			
		}	
	}
	
	private class ButtonListenerExit implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
			
		}	
	}

	private class KeyListenerEnter implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
	         if (e.getKeyChar() == KeyEvent.VK_ENTER) {
		            ((JButton) e.getComponent()).doClick();
		         }			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	   
	private class KeyListenerSelect implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			 for (int i = 0; i < buttons.length; i++) {
      		   if (e.getKeyCode()== KeyEvent.VK_DOWN) {
      			   if (i > 0)
      				   buttons[i - 1].requestFocusInWindow();
      		   }
           	  if (e.getKeyCode()== KeyEvent.VK_UP) {
           		  if (i < buttons.length - 1)
           			  buttons[i + 1].requestFocusInWindow();
           	  }
      	   }			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}

}
