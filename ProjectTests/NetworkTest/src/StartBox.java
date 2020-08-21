import javax.swing.JFrame;

public class StartBox extends JFrame {

	private static final long serialVersionUID = 1L;
	private HThread master;
	private StartPanel panel;
	public StartBox(HThread master) {
		this.master = master;
		this.panel = new StartPanel(this);
		//this.setResizable(false);
		this.setSize(400, 400);
		
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
	}
}
