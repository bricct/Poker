package ui.frames;

import java.awt.Color;

import javax.swing.JFrame;

import netwrk.HThread;
import ui.panels.StartPanel;

public class StartBox extends JFrame {

	private static final long serialVersionUID = 1L;
	private HThread master;
	private StartPanel panel;
	public StartBox(HThread master) {
		this.master = master;
		this.setSize(400, 200);
		this.setResizable(false);
		this.setBackground(Color.black);

		this.panel = new StartPanel(this);
		this.panel.setBackground(Color.black);


		this.add(this.panel);



	}

	public int getConnected() {
		return master.getConnected();
	}

	public void startServer() {
		master.sendStart();
		this.dispose();
	}

	public void cancelServer() {
		master.sendStop();
		this.dispose();
	}
}
