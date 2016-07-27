package JavaGUI;

import javax.swing.JPanel;

public abstract class MasterViewPanel extends JPanel {
	
	protected MasterView mv;
	
	public MasterViewPanel(MasterView mv) {
		this.mv = mv;
		
	}
}
