package JavaGUI;



import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.*;


import battleField.BattleField;
import battleField.Player;

public class MasterView extends JFrame {
	
		
	private JPanel body;
	private JPanel main;
	
	
	public static void main(String[] args) {
		
		new MasterView();
	}
	
	public MasterView() {	
		this.setSize(640, 480);
        this.setVisible(true);	
		this.setTitle("JavaBoy");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUpBody(); // make the JPanels
		setUpMenuBar();
		requestFocusInWindow(); 

		
	}
		
	
	private void setUpMenuBar() { // i think i'm done with this one?
		JMenuBar jmenuBar = new JMenuBar();
		
		JMenu file = new JMenu("File");
		JMenuItem fileExit = new JMenuItem("Exit");
		fileExit.addActionListener(new ButtonListenerExit());
		file.add(fileExit);
		
		JMenu about = new JMenu("About");
		JMenuItem aboutGame = new JMenuItem("About"); // adds an about menu item to about
		aboutGame.addActionListener(new AboutListener());
		about.add(aboutGame);
		
		jmenuBar.add(file); // adds the file menu to the bar
		jmenuBar.add(about); //adds the about menu to the bar
		
		this.add(jmenuBar, BorderLayout.NORTH); //adds the jmenubar to the game
	}
	
	private void setUpBody() {
		TitleView title = new TitleView(this); // construct new title JPanel
		body = new JPanel(new CardLayout());
		String titleStr = "Title";
		body.add(title, titleStr);
		main = new JPanel();
		body.add(main, "temp"); // temporary JPanel where main will be--will be replaced with a BattleView JPanel when changeView(battleview) is called
		this.add(body, BorderLayout.CENTER); // add the card layout JPanel to the south of the JFrame
		CardLayout cl = (CardLayout) body.getLayout();
		body.getComponent(0).requestFocusInWindow();
		cl.show(body, "Title"); // show the title screen when the game starts

	}
	
	public void changeView(Views v, Object o) {
		CardLayout cl = (CardLayout) (body.getLayout());
		if (v == Views.BATTLE) {
			body.remove(main); // remove the old jpanel so that it can be replaced w/ a new jpanel with a new battlefield
			BattleView main = new BattleView(this, new BattleField(new Player())); // construct new battle JPanel
			String battleStr = "Battle";
			body.add(main, battleStr); 
			cl.show(body, "Battle");
			body.getComponent(1).requestFocusInWindow();
		}
		if (v == Views.TITLE) {
			body.getComponent(0).requestFocusInWindow();
			cl.show(body, "Title");


		}
		
	}
	
	private class ButtonListenerExit implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
			
		}
		
		
	}
	
	private class AboutListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			JOptionPane.showMessageDialog(null, "This is my JavaBoy Game!");
		}
		
	}


}
