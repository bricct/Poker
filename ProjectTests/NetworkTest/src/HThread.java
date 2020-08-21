import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.JFrame;

public class HThread extends Thread {
	private final int port, starting_cash, bblind;
	private Server server;
	private static final int max_connected = 5;
	private StartBox startBox;
	private int connected = 1;
	
	public HThread(int port, int starting_cash, int bblind) {
		this.port = port;
		this.starting_cash = starting_cash;
		this.bblind = bblind;
	}
	
	
	public void run() {
		
		StartThread t = new StartThread(this);
		t.start();
		
		
		try {
			this.server = new Server(max_connected, starting_cash, bblind, port, this);
			System.out.println("serverConstruction finished");
			this.startBox.dispose();
		} catch (IOException | InterruptedException e) {
			System.out.println("ERROR: Server Failure");
		}
		
		
		
	}
	
	public void sendStart() {
		this.server.start();
	}
	
	public void sendStop() {
		this.server.stop();
	}
	
	public void updateConnected(int val) {
		this.connected = val;
	}
	
	public int getConnected () {
		return this.connected;
	}
	
}
