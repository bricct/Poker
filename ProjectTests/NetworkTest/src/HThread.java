import java.io.IOException;

public class HThread extends Thread {
	private final int port, starting_cash, bblind;
	private Server server;
	
	public HThread(int port, int starting_cash, int bblind) {
		this.port = port;
		this.starting_cash = starting_cash;
		this.bblind = bblind;
	}
	
	
	public void run() {
		
		try {
			this.server = new Server(2, starting_cash, bblind, port);
		} catch (IOException | InterruptedException e) {
			System.out.println("ERROR: Server Failure");
		}
		
		
	}
}
