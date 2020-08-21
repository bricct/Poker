import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Client is our class representation of our clients that will
 * be connecting to our server (handling each connected player)
 */
public class Client {
	private Socket socketConnection;
	private DataOutputStream outToServer;
	private DataInputStream inFromServer;
	private User user;
	private int client_id, port;
	private String ip;

	/**
	 * Constructor for our client class
	 * @param user The user for our client class
	 * @param ip ip of the host
	 * @param port port of the host
	 */
	public Client(User user, String ip, int port) {
		this.user = user;
		this.ip = ip;
		this.port = port;
	}
	
	/**
	 * Connect function to connect to the server
	 * @return returns id of the connection if connects correctly
	 */
	public int connect() {
		try {
			
			socketConnection = new Socket(this.ip, this.port);
			
			
			//QUERY PASSING
			outToServer = new DataOutputStream(socketConnection.getOutputStream());
			inFromServer = new DataInputStream(socketConnection.getInputStream());
			
			
			try {
				
				this.client_id = Integer.parseInt(getMessage());
				
				//outToServer.writeUTF(this.client_id + " connected");
				sendMessage(this.client_id + " " + TestMenu.name);
				return this.client_id;
			} catch (NumberFormatException e) { 
				System.out.println("Oopsies something went wrong");
			}
			
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return -1;
	}
	
	/**
	 * Send a message to the server
	 * @param msg the message to send
	 * @throws Exception
	 */
	public void sendMessage(String msg) throws Exception {
		System.out.println("sending message from client " + this.client_id + " to server class -> " + msg);
		outToServer.writeUTF(msg);
	}
	
	/**
	 * Pulls message from the server
	 * @return The message that was pulled
	 * @throws Exception
	 */
	public String getMessage() throws Exception {
		String msg = this.inFromServer.readUTF();
		if (msg.equals("game-end -1")) socketConnection.shutdownOutput();
		return msg;
	}
	
	/**
	 * Sends a message to a user
	 * @param msg the message to send
	 */
	public void sendMessageToUser(String msg) {
		System.out.println("sending message from client to user class -> " + msg);
		try {
			this.user.operate(msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Disconnects the user from the server
	 */
	public void disconnect() {
		try {
			this.socketConnection.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}