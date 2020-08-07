import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.PriorityQueue;

class CThread extends Thread {
	
private Socket s;
DataInputStream infromClient;
DataOutputStream outToClient;
private int id;
private PriorityQueue<String> cmds;

	CThread() throws IOException{}
	
	CThread(Socket s, int id, PriorityQueue<String> cmds) throws IOException{
	    this.s=s;
	    this.cmds = cmds;
	    this.id = id;
	    infromClient = new DataInputStream(s.getInputStream());
	    outToClient = new DataOutputStream(s.getOutputStream());
	}
		public void run(){  
			
			
	        try {
				outToClient.writeUTF(Integer.toString(id));
				//System.out.println("just wrote " + id + " to client");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			
			
			while(true) {
				try {
			        this.readCommand();
			    } catch (IOException ex) {
			        cmds.add(id + " disconnected");
			        try {
						s.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			        break;
			    }
				try {
					sleep(50);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
					break;
				}
			}
			
		}
		
		
		private synchronized void readCommand() throws IOException { 
			String msg = infromClient.readUTF();
	        this.cmds.add(msg);
		}
		
		public void sendMessage(String msg) throws IOException {
			outToClient.writeUTF(msg);
		}
	
	
}