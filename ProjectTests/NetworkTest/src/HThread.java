//import java.awt.EventQueue;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

//import javax.swing.JFrame;

public class HThread extends Thread {
	private final int port, starting_cash, bblind;
	private static final int max_connected = 5;
	private int connected = 0;
	private AtomicInteger status;
	private TestMenu master;
	private StartThread t;
	
	public HThread(int port, int starting_cash, int bblind, TestMenu master) {
		this.port = port;
		this.starting_cash = starting_cash;
		this.bblind = bblind;
		this.master = master;
		this.status = new AtomicInteger(0);
	}
	
	
	public void run() {
		
		t = new StartThread(this);
		t.start();
		
		
		try {
			new Server(max_connected, starting_cash, bblind, port, this, this.status);
			System.out.println("serverConstruction finished");
			//this.startBox.dispose();
		} catch (IOException | InterruptedException e) {
			System.out.println("ERROR: Server Failure");
		}
		
		
		
	}
	
	public void sendStart() {
		this.status.incrementAndGet();
		System.out.println("updating status to start");
		master.game(true);
	}
	
	public void sendJoin() {
		t.close();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Joining own game");
		master.game(true);
		
	}
	
	public void sendStop() {
		this.status.decrementAndGet();
		master.game(true);
	}
	
	public void updateConnected(int val) {
		this.connected = val;
	}
	
	public int getConnected () {
		return this.connected;
	}
	
}
