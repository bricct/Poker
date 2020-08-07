package poker.networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;


public class Client {
	private Socket socketConnection;
	private DataOutputStream outToServer;
	private DataInputStream inFromServer;
	private int client_id;
	public Client() {
	}
	
	public int connect() {
		try {
			
			socketConnection = new Socket("127.0.0.1", 11112);
			
			
			//QUERY PASSING
			outToServer = new DataOutputStream(socketConnection.getOutputStream());
			inFromServer = new DataInputStream(socketConnection.getInputStream());
			
			
			try {
				
				this.client_id = Integer.parseInt(getMessage());
				
				outToServer.writeUTF(this.client_id + " connected");
				
				return this.client_id;
			} catch (NumberFormatException e) { 
				System.out.println("Oopsies something went wrong");
			}
			
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return -1;
	}
	
	
	public void sendMessage(String msg) throws Exception {
		outToServer.writeUTF(msg);
	}
	
	
	public String getMessage() throws Exception {
		return this.inFromServer.readUTF();
	}
	
	
	
	
}