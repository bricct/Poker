import java.awt.EventQueue;

import javax.swing.JFrame;

public class StartThread extends Thread {
	
	private HThread master;
	private StartBox startBox;
	
	public StartThread(HThread master) {
		this.master = master;
	}

	
	public void run() {
		EventQueue.invokeLater(() -> {
			this.startBox = new StartBox(master);
			this.startBox.setVisible(true);
			this.startBox.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		});
	}
}
