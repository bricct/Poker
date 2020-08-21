import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author Trey Briccetti
 * @version 1.0
 */
public class HThread extends Thread {
	private final int port, starting_cash, bblind;
	private static final int max_connected = 5;
	private int connected = 0;
	private AtomicInteger status;
	private TestMenu master;
	private StartThread t;
	
	/** Creates a thread to create and control when a server starts
	 * @param port The port the server will be hosted on
	 * @param starting_cash Starting cash of the players
	 * @param bblind Blind amount of the game
	 * @param master The main menu
	 */
	public HThread(int port, int starting_cash, int bblind, TestMenu master) {
		this.port = port;
		this.starting_cash = starting_cash;
		this.bblind = bblind;
		this.master = master;
		this.status = new AtomicInteger(0);
	}
	
	
	/**
	 * Creates a server and a frame to control the server
	 */
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
	
	/** Starts the server and joins the host
	 * 
	 */
	public void sendStart() {
		this.status.incrementAndGet();
		System.out.println("updating status to start");
		master.game(true);
	}
	
	/** Cancels the server
	 * 
	 */
	public void sendStop() {
		this.status.decrementAndGet();
		master.game(true);
	}
	
	/** Updates the number of people connected to the server
	 * @param val The number of connections to the server
	 */
	public void updateConnected(int val) {
		this.connected = val;
	}
	
	/** Returns the number of connections to the server
	 * @return The number of connections to the server
	 */
	public int getConnected () {
		return this.connected;
	}
	
}
