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
	private final int startingMoney, bblind, port;
	
	/** Constructs a server that sends and receives game state update messages to and from the client
	 * @param num_players The max number of players
	 * @param startingMoney The starting money every player gets
	 * @param bblind The amount of money the big blind is
	 * @param port The port the server is being hosted on
	 * @param master The Thread which creates the server
	 * @param status An integer that allows the user to start the game with any number of players less than 5 connected
	 * @throws IOException 
	 * @throws InterruptedException
	 */
	public Server(int num_players, int startingMoney, int bblind, int port, HThread master, AtomicInteger status) throws IOException, InterruptedException{   
		
		
		this.clients = new AtomicInteger(0);
		this.commands = new PriorityQueue<>();
		this.threads = new ArrayList<>();
		this.disconnected = new HashSet<Integer>();
		this.startingMoney = startingMoney;
		this.bblind = bblind;
		this.port = port;
	    
		while((clients.intValue() < 5 && (status.intValue() == 0))){
			System.out.println(clients.intValue() + "");
	        ServerSocket ss=new ServerSocket(this.port);
	        System.out.println("Waiting for Clients to connect"); 
	        
	        Socket s=ss.accept();
	        CThread t=new CThread(s, clients.getAndIncrement(), commands);
	        threads.add(t);
	        t.start();
	        Thread.sleep(20);
	        ss.close();
	        master.updateConnected(clients.intValue());
	        System.out.println("status " + status.intValue());
	    
	    }    
		
		System.out.println("Server Starting");
		
		if (status.intValue() < 0) {
			sendGameEnd();
			throw new InterruptedException("Host has cancelled the server");
		}
	    
		
		Thread.sleep(1000);
		
		this.players = new ArrayList<>();
		
		String id_name = commands.poll();
		int i = 0;
		while (id_name != null) {
			System.out.println("received " + id_name);
			String[] args = id_name.split(" ");
			int id = -1;
			
			try {
				id = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				id_name = commands.poll();
				continue;
			}
			
			
			players.add(new Player(id, args[1], startingMoney));
			sendMoney(id, startingMoney);
			if (++i >= threads.size()) break;
			id_name = commands.poll();
		}
		
		
		sendPlayerInfo();
		
		
		Game game = new Game(this.bblind/2, this.bblind, this.players, this);
		game.playGame();

	    }
	
	
	
	/** Stalls the server until a message is received from a particular user
	 * @param id The user id
	 * @return An array of parsed strings representing the message form the user
	 * @throws InterruptedException
	 */
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
	    		
	    	}
	    	Thread.sleep(20);
	    }
		
		return ret;
		
	}
	
	
	/** Executes a command on the server
	 * @param process The command message
	 * @return 0 if the message was a known operation otherwise it returns -1
	 */
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
		
		
		if (args.length >= 3)  {
			try {
				Integer.parseInt(args[2]);
			} catch (NumberFormatException e) {
				return -1;
			}
		}
		
		String cmd = args[1];
		
		if (cmd.equals("disconnected")) disconnected.add(new Integer(id));
			
		if (cmd.equals("fold")) {
			distributeCmd(-1, cmd);
		} else if (cmd.equals("check")) {
			distributeCmd(-1, cmd + " " + id);
		} else if (cmd.equals("disconnected")) {
			distributeCmd(id, cmd);
		} else {
			return -1;
		}
			
		return 0;
	}
	
	
	
	/** Distributes a command to all clients except that with user id == id
	 * @param id The user who sent the message, -1 if from the server
	 * @param cmd The command to be distriubuted to the clients
	 */
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
	
	
	/** Sends cards to all active players
	 * @param queue The queue of people to recieve cards
	 */
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
		
		

	
	private void sendPlayerInfo() {
		for (int i = 0; i < this.threads.size(); i++) {
			if (!disconnected.contains(new Integer(i))) {
				try {
					
					int active_players = this.players.size() - this.disconnected.size();
					String players_out = "";
					for (int j = 0; j < this.players.size(); j++) {
						int id = this.players.get(j).getid();
						String name = this.players.get(j).getName();
						if (!disconnected.contains(new Integer(id)) && id != i) {
							players_out += (" " + id + " " + name);
						}
					}
					
					threads.get(i).sendMessage("players" + " " + active_players  + " " + startingMoney +  players_out);
	
					System.out.println("sending players " + active_players +  players_out + " to client " + (i));
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

	
	/** Sends a message to the clients that a person has folded
	 * @param id The id of the player who folded
	 */
	public void forceFold(int id) {
		sendCmd(id, "fold");
		
		distributeCmd(id, "fold");
		
	}
	
	/** Sends the current pot amount to all players
	 * @param pot The amount of money in the pot
	 */
	public void sendPot(int pot) {
		distributeCmd(-1, "pot " + pot);
		
	}

	/** Sends the money of a player all players
	 * @param id the id of the player in question
	 * @param money the amount of money the player has
	 */
	public void sendMoney(int id, int money) {
		distributeCmd(-1, "money " + id + " " + money);

		
	}

	/** Sends the blinds to a player
	 * @param id the id of the player in question
	 * @param val The amount of money the blind is
	 */
	public void sendBlind(int id, int val) {
		sendCmd(id, "blind " + val);
		
	}

	/** Sends the winnings of a player to all players
	 * @param id the id of the player in question
	 * @param pot The amount of money in the pot
	 */
	public void sendWinnings(int id, int pot) {
		distributeCmd(-1, "winnings " + id + " " + pot);
		
	}

	
	/** Sends the flop cards to all players
	 * @param card1 The first card in the flop
	 * @param card2 The second card in the flop
	 * @param card3 The third card in the flop
	 */
	public void sendFlop(Card card1, Card card2, Card card3) {
		distributeCmd(-1, "flop " + card1.value() + " " + card1.suit() + " " + card2.value() + " " + card2.suit() + " " + card3.value() + " " + card3.suit());
		
	}

	
	/** Sends the turn card to all players
	 * @param card the turn card
	 */
	public void sendTurn(Card card) {
		distributeCmd(-1, "turn " + card.value() + " " + card.suit());
		
	}


	/** Sends the river card all players
	 * @param card the river card
	 */
	public void sendRiver(Card card) {
		distributeCmd(-1, "river " + card.value() + " " + card.suit());
		
	}


	/** Sends the reset ui command to all players
	 * 
	 */
	public void sendStartHand() {
		distributeCmd(-1, "reset-hand");
		
	}
	
	
	/** Sends the raise of a player to all players
	 * @param id the id of the player in question
	 * @param bet the amount of money raised by the player
	 */
	public void sendRaise(int id, int bet) {
		distributeCmd(-1,  "raise " + id + " " + bet);
	}

	/** Sends the all in of a player to all players
	 * @param id the id of the player in question 
	 * @param bet the amount of money the player went all in with
	 */
	public void sendAllin(int id, int bet) {
		distributeCmd(-1, "all-in " + id + " " + bet);
		
	}
	
	/**  Sends a win to a player
	 * @param id the id of the player in question
	 */
	public void sendWin(int id) {
		sendCmd(id, "you-win");
	}
	
	/** Sends a loss to a player
	 * @param id the id of the player in question
	 */
	public void sendLose(int id) {
		sendCmd(id, "you-lose");
	}

	/** Sends the game ending message to all players
	 * 
	 */
	public void sendGameEnd() {
		distributeCmd(-1, "game-end");
	}
	 
	/** Sends whose turn it is to all players
	 * @param id the id of the player in question
	 */
	public void sendTurn(int id) {
		distributeCmd(-1, "player-turn " + id);
	}

	/** Exposes the cards of a player to all other players
	 * @param id the id of the player in question
	 * @param firstCard The first card of the player
	 * @param secCard The second card of the player
	 */
	public void exposeCards(int id, Card firstCard, Card secCard) {
		
		distributeCmd(id, "player-cards " + id + " " + firstCard.value() + " " + firstCard.suit() + " " + secCard.value() + " " + secCard.suit());
		
	}
	
}