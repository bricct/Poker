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
	private ArrayList<Player> players;
	//private Deck deck;
	private final int startingMoney;
	
	public Server(int num_players) throws IOException, InterruptedException{   
		
		
		this.clients = new AtomicInteger(0);
		this.commands = new PriorityQueue<>();
		this.threads = new ArrayList<>();
		this.disconnected = new HashSet<Integer>();
		this.startingMoney = 1500;
	    
		while(clients.intValue() < num_players){
			System.out.println(clients.intValue() + "");
	        ServerSocket ss=new ServerSocket(11112);
	        System.out.println("Waiting for Clients to connect"); 
	        Socket s=ss.accept();
	        CThread t=new CThread(s, clients.getAndIncrement(), commands);
	        threads.add(t);
	        t.start();
	        Thread.sleep(20);
	        ss.close();
	    
	    }    
	    
		this.players = new ArrayList<>();
		
		for (int i = 0; i < threads.size(); i++) {
			players.add(new Player(i, ((int)i)+"", startingMoney));
			sendMoney(i, startingMoney);
		}
		
		
		sendPlayerInfo();
		
		
		Game game = new Game(5, 20, this.players, this);
		game.playGame();
		
		
		//this.deck = new Deck();
		//this.deck.shuffle();
		
	    
	    
	    
	    
	    //sendCards();
	    //sendInitialMoney();
	    
	    
	    
	    
	
	
	
	
	    }
	
	public String[] nextCmd(int id) throws InterruptedException {
		boolean next_cmd_found = false;
		int cmd_id = -1;
		String[] ret = null;
		System.out.println("waiting for reponse from client " + id + "...");
		while(!next_cmd_found) {
	    	String cmd = commands.poll();
	    	
	    	if (cmd != null) {
	    		
	    		
	    		String[] args = cmd.split(" ");
	    		
	    		try {
	    			cmd_id = Integer.parseInt(args[0]);
	    		} catch (NumberFormatException e) {
	    			e.printStackTrace();
	    		}
	    		
	    		if (cmd_id == id) {
	    			System.out.println("reponse from client " + id + " recieved");
	    			executeCmd(cmd);
	    			next_cmd_found = true;
	    			ret = new String[args.length-1];
	    			for (int i = 1; i < args.length; i++) ret[i-1] = args[i];
	    			
	    		}
	    		
	    		
	    		//System.out.println(cmd);
	    		//executeCmd(cmd);
	    	}
	    	Thread.sleep(20);
	    }
		
		return ret;
		
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
		
		int val = 0;
		
		if (args.length >= 3)  {
			try {
				val = Integer.parseInt(args[2]);
			} catch (NumberFormatException e) {
				return -1;
			}
		}
		
		String cmd = args[1];
		
		if (cmd.equals("disconnected")) disconnected.add(new Integer(id));
			
		if (cmd.equals("fold")) {
			//do something
			distributeCmd(id, cmd);
		} else if (cmd.equals("raise")) {
			//do something
			distributeCmd(id, cmd + " " + val);
		} else if (cmd.equals("check")) {
			//do something
			distributeCmd(id, cmd);
		} else if (cmd.equals("disconnected")) {
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
			if (i != id) {
				if (!disconnected.contains(new Integer(id))) {
					try {
						threads.get(i).sendMessage(cmd +" "+ id);
						System.out.println("forwarding message " + cmd + " " + id + " to client " + (i));
					} catch (IOException e) {
						System.out.println("someting wong");
					}
				}
			}
			
		}
		
		
	}
	
	
	
	
	private void sendCmd(int id, String cmd) {
		if (!disconnected.contains(new Integer(id))) {
			try {
				
				threads.get(id).sendMessage(cmd);

				System.out.println("sending " + cmd + " to client " + id);
			} catch (IOException e) {
				System.out.println("someting wong");
			}
		}
	}
	
	public void sendCards(ArrayList<PlayerTuple> queue) {
		
		for (int i = 0; i < queue.size(); i++) {
			Player p = queue.get(i).player;
			int id = p.getid();
			
			Card card1 = p.firstCard();
			Card card2 = p.secCard();
			
			try {
				threads.get(id).sendMessage("cards" + " " + card1.value() + " " + card1.suit() + " " + card2.value() + " " + card2.suit());
			} catch (IOException e) {
				System.out.println("someting wong");
			}
			
		}
		
		
		
	}	
		
		
		
		
//		for (int i = 0; i < this.threads.size(); i++) {
//			
//			if (!disconnected.contains(new Integer(i))) {
//				try {
//					
//					threads.get(i).sendMessage("cards" + " " + card1.value() + " " + card1.suit() + " " + card2.value() + " " + card2.suit());
//	
//					System.out.println("sending cards " + card1.value() + " " + card1.suit() + " " + card2.value() + " " + card2.suit() + " to client " + i);
//				} catch (IOException e) {
//					System.out.println("someting wong");
//				}
//			}	
//		}
	
	
//	private void sendInitialMoney() {
//		for (int i = 0; i < this.threads.size(); i++) {
//					
//			if (!disconnected.contains(new Integer(i))) {
//				try {
//					
//					threads.get(i).sendMessage("money " + this.startingMoney);
//	
//					System.out.println("sending money " + this.startingMoney + " to client " + i);
//				} catch (IOException e) {
//					System.out.println("someting wong");
//				}
//			}	
//		}
//	}
	
	private void sendPlayerInfo() {
		for (int i = 0; i < this.threads.size(); i++) {
			if (!disconnected.contains(new Integer(i))) {
				try {
					
					int active_players = this.threads.size() - this.disconnected.size();
					String players = "";
					for (int j = 0; j < this.threads.size(); j++) {
						if (!disconnected.contains(new Integer(j)) && j != i) {
							players += (" " + (j));
						}
					}
					
					threads.get(i).sendMessage("players" + " " + active_players +  players);
	
					System.out.println("sending players " + active_players +  players + " to client " + (i));
				} catch (IOException e) {
					System.out.println("someting wong");
				}
			}	
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	public void forceFold(int id) {
		// TODO Auto-generated method stub
		sendCmd(id, "fold");
		
		distributeCmd(id, "fold");
		
	}
	
	//send to all
	public void sendPot(int pot) {
		distributeCmd(-1, "pot " + pot);
		
	}

	public void sendMoney(int id, int money) {
		sendCmd(id, "money " + money);

		
	}

	public void sendBlind(int id, int val) {
		sendCmd(id, "blind " + val);
		
	}

	public void sendWinnings(int id, int pot) {
		sendCmd(id, "winnings " + pot);
		
	}

	
	//send to all
	public void sendFlop(Card card1, Card card2, Card card3) {
		distributeCmd(-1, "flop " + card1.value() + " " + card1.suit() + " " + card2.value() + " " + card2.suit() + " " + card3.value() + " " + card3.suit());
		
	}
	
	//send to all
	public void sendTurn(Card card) {
		distributeCmd(-1, "turn " + card.value() + " " + card.suit());
		
	}

	//send to all
	public void sendRiver(Card card) {
		distributeCmd(-1, "river " + card.value() + " " + card.suit());
		
	}

	//send to all
	public void sendStartHand() {
		distributeCmd(-1, "reset-hand");
		
	}

	
	
	
}