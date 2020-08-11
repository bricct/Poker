
public class UThread extends Thread {
	private Client client;
	//private PriorityQueue<String> cmds;
	
	public UThread(Client client) {
		this.client = client;
		//this.cmds = cmds;
	}
	
	public void run() {
		while(true) {
			
			String msg;
			try {
				msg = client.getMessage();
				client.sendMessageToUser(msg);
				System.out.println(msg +  " ");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Disconnected from server");
				break;
			}
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
