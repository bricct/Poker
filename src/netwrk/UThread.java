package netwrk;

/**
 * UThread is a thread class for constantly pulling messages
 * from the server to the client
 */
public class UThread extends Thread {
	private Client client;
	//private PriorityQueue<String> cmds;

	/**
	 * Constructor function for our thread class
	 * @param client Client to send messages to
	 */
	public UThread(Client client) {
		this.client = client;
		//this.cmds = cmds;
	}

	/**
	 * Our standard pull thread function
	 */
	public void run() {
		while(true) {

			String msg;
			try {
				msg = client.getMessage();
				client.sendMessageToUser(msg);
				System.out.println(msg +  " ");
			} catch (Exception e) {
				//e.printStackTrace();
				System.out.println("Disconnected from server");

				client.sendMessageToUser("game-end -1");
				//client.sendMessageToUser("kicked");
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
