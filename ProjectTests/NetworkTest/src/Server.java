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
	private Deck deck;
	private final int startingMoney;
	
	public Server(int players) throws IOException, InterruptedException{   
		
		
		this.clients = new AtomicInteger(0);
		this.commands = new PriorityQueue<>();
		this.threads = new ArrayList<>();
		this.disconnected = new HashSet<Integer>();
		
	    
		while(clients.intValue() < players){
			System.out.println(clients.intValue() + "");
	        ServerSocket ss=new ServerSocket(11112);
	        System.out.println("Waiting for Clients to connect"); 
	        Socket s=ss.accept();
	        CThread t=new CThread(s, clients.incrementAndGet(), commands);
	        threads.add(t);
	        t.start();
	        Thread.sleep(20);
	        ss.close();
	    
	    }    
	    
		
		this.startingMoney = 1500;
		
		
		
		this.deck = new Deck();
		this.deck.shuffle();
		
	    sendPlayerInfo();
	    sendCards();
	    sendInitialMoney();
	    
	    
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
	
	
	
	
//	private void sendCmd(int id, String cmd) {
//		
//	}
	
	private void sendCards() {
		for (int i = 0; i < this.threads.size(); i++) {
			
			if (!disconnected.contains(new Integer(i))) {
				try {
					Card card1 = this.deck.draw();
					Card card2 = this.deck.draw();
					
					threads.get(i).sendMessage("cards" + " " + card1.value() + " " + card1.suit() + " " + card2.value() + " " + card2.suit());
	
					System.out.println("sending cards " + card1.value() + " " + card1.suit() + " " + card2.value() + " " + card2.suit() + " to client " + i);
				} catch (IOException e) {
					System.out.println("someting wong");
				}
			}	
		}
	}
	
	
	private void sendInitialMoney() {
		for (int i = 0; i < this.threads.size(); i++) {
					
			if (!disconnected.contains(new Integer(i))) {
				try {
					
					threads.get(i).sendMessage("money " + this.startingMoney);
	
					System.out.println("sending money " + this.startingMoney + " to client " + i);
				} catch (IOException e) {
					System.out.println("someting wong");
				}
			}	
		}
	}
	
	private void sendPlayerInfo() {
		for (int i = 0; i < this.threads.size(); i++) {
			if (!disconnected.contains(new Integer(i))) {
				try {
					
					int active_players = this.threads.size() - this.disconnected.size();
					String players = "";
					for (int j = 0; j < this.threads.size(); j++) {
						if (!disconnected.contains(new Integer(j)) && j != i) {
							players += (" " + j);
						}
					}
					
					threads.get(i).sendMessage("players" + " " + active_players +  players);
	
					System.out.println("sending players " + active_players +  players + " to client " + i);
				} catch (IOException e) {
					System.out.println("someting wong");
				}
			}	
		}
	}
	
	
	
}