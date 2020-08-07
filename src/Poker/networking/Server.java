<<<<<<< HEAD
=======
package poker.networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
>>>>>>> 3c370aeb900136177088a03e117c90b442afa20d
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicInteger;



public class Server {

	private AtomicInteger clients;
	private PriorityQueue<String> commands;
	private ArrayList<CThread> threads;
	private HashSet<Integer> disconnected;
	
	
	public Server(int players) throws IOException, InterruptedException{   
		
		
		this.clients = new AtomicInteger(0);
		this.commands = new PriorityQueue<>();
		this.threads = new ArrayList<>();
		this.disconnected = new HashSet<Integer>();
		
	    while(clients.intValue() < players){
	        ServerSocket ss=new ServerSocket(11112);
	        System.out.println("Waiting for Clients to connect"); 
	        Socket s=ss.accept();
	        CThread t=new CThread(s, clients.incrementAndGet(), commands);
	        threads.add(t);
	        t.start();
	        Thread.sleep(200);
	        ss.close();
	    }    
	    
	    while(true) {
	    	String cmd = commands.poll();
	    	if (cmd != null) {
	    		System.out.println(cmd);
	    		executeCmd(cmd);
	    	}
	    	Thread.sleep(20);
	    }
	
	
	
	
	    }
	
	
	public int executeCmd(String process) {
		String[] args = process.split(" ");
		
		if (args.length <= 1)  {
			return -1;
		}
		
		int id;
		try {
			id = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			return -1;
		}
		
		String cmd = args[1];
		
		if (cmd.equals("disconnected")) disconnected.add(new Integer(id));
			
		if (cmd.equals("fold")) {
			//do something
			distributeCmd(id, cmd);
		} else if (cmd.equals("raise")) {
			//do something
			distributeCmd(id, cmd);
		} else if (cmd.equals("check")) {
			//do something
			distributeCmd(id, cmd);
		} else if (cmd.equals("call")) {
			//do something
			distributeCmd(id, cmd);
		} else {
			//distributeCmd(id, cmd);
			return -1;
		}
			
		return 0;
	}
	
	private void distributeCmd(int id, String cmd) {
		
		for (int i = 0; i < this.threads.size(); i++) {
			if (i != id - 1) {
				if (!disconnected.contains(new Integer(id))) {
					try {
						threads.get(i).sendMessage(id +" "+ cmd);
						System.out.println("forwarding message " + id + " " + cmd + " to client " + (i+1));
					} catch (IOException e) {
						System.out.println("someting wong");
					}
				}
			}
			
		}
		
		
	}
	
	
	
}