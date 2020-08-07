package poker.networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicInteger;

class Multi extends Thread{
private Socket s;
DataInputStream infromClient;
DataOutputStream outToClient;
private AtomicInteger clients;
private PriorityQueue<String> cmds;
Multi() throws IOException{


}
Multi(Socket s, AtomicInteger clients, PriorityQueue<String> cmds) throws IOException{
    this.s=s;
    this.clients = clients;
    this.cmds = cmds;
    infromClient = new DataInputStream(s.getInputStream());
    outToClient = new DataOutputStream(s.getOutputStream());
}
	public void run(){  
		
		int id = clients.incrementAndGet();
		
        try {
			outToClient.writeUTF(Integer.toString(id));
			System.out.println("just wrote " + id + " to client");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		
		while(true) {
			try {
		        this.readCommand();
		    } catch (IOException ex) {
		        cmds.add("client " + id + " disconnected");
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
	
	
}



public class Server {

public static void main(String args[]) throws IOException, 
InterruptedException{   
	AtomicInteger clients = new AtomicInteger(0);
	PriorityQueue<String> commands = new PriorityQueue<>();
    while(clients.intValue() < 2){
        ServerSocket ss=new ServerSocket(11112);
        System.out.println("Waiting for Clients to connect"); 
        Socket s=ss.accept();
        Multi t=new Multi(s, clients, commands);
        t.start();
        Thread.sleep(200);
        ss.close();
    }    
    
    while(true) {
    	String cmd = commands.poll();
    	if (cmd != null) {
    		System.out.println(cmd);
			System.out.println(commands.size());
    	}
    	Thread.sleep(20);
    }




    }   
}