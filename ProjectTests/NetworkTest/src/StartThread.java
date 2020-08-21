import java.awt.EventQueue;


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
		});
	}
	
	public void close() {
		this.startBox.dispose();
	}
}
