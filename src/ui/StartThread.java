package ui;

import java.awt.EventQueue;

import netwrk.HThread;
import ui.frames.StartBox;


/**
 * @author Trey Briccetti
 * @version 1.0
 */
public class StartThread extends Thread {
	
	private HThread master;
	private StartBox startBox;
	
	/** Creates a thread to run the start server frame and panel
	 * @param master The Hosting thread which created this thread
	 */
	public StartThread(HThread master) {
		this.master = master;
	}

	
	/** Runs the thread to construct the frame and panel
	 *
	 */
	public void run() {
		EventQueue.invokeLater(() -> {
			this.startBox = new StartBox(master);
			this.startBox.setVisible(true);
		});
	}
	
	/** Closes the frame and panel
	 * 
	 */
	public void close() {
		this.startBox.dispose();
	}
}
