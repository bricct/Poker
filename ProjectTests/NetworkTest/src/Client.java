import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class Client {
	private Socket socketConnection;
	private DataOutputStream outToServer;
	private DataInputStream inFromServer;
	private User user;
	private int client_id;
	public Client(User user) {
		this.user = user;
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
	
	public void sendMessageToUser(String msg) {
		System.out.println("sending message from client to user class -> " + msg);
		this.user.operate(msg);
	}
	
	public void disconnect() {
		try {
			this.socketConnection.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}