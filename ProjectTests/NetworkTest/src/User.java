import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 * @author Trey Briccetti
 * @version 1.0
 * User is our class representation for a user (or player)
 * Handles mostly the connection based algorithms
 */
public class User {


	private Client client;
	private TestBoard board;
	private final int id;

	/**
	 * Constructor function for our user class
	 * @param master parent component to output messages to
	 */
	public User(TestMenu master) {
		//PriorityQueue<String> commands = new PriorityQueue<>();
		
		String ip = "";
		ip += TestMenu.ip[0];
		ip += "."+ TestMenu.ip[1];
		ip += "." + TestMenu.ip[2];
		ip += "." + TestMenu.ip[3];
		


		System.out.println(ip);
		this.client = new Client(this, ip, TestMenu.port);
		System.out.println("Attempting to connect");
		this.id = client.connect();
		System.out.println("connected");
		board = new TestBoard(master, this, id);
		//board.setVisible(true);
		board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		board.setSize(master.getWidth(),master.getHeight());
		board.setLocationRelativeTo(master);
		master.setVisible(false);
		board.setVisible(true);
		try {
			Thread.sleep(200);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("" + id);
		if (id == -1) System.out.println("uhoh");

		UThread t = new UThread(client);
		t.start();






	}


	/**
	 * Sends message to server
	 * @param msg The message to send
	 * @throws Exception 
	 */
	public void sendMessage(String msg) throws Exception {

		try {
	    	client.sendMessage(id + " " + msg);
	    } catch (Exception ee) {
	    	System.out.println("Error: Client unable to send message");
	    	ee.printStackTrace();
	    }
	}


	/** Operates on a command by the server and forwards method calls to the display frame
	 * @param cmd The Command sent by the server
	 * @throws Exception If a message is corrupted during transit an exception is thrown
	 */

	public void operate(String cmd) throws Exception {
		if (cmd.equals("kicked")) {
			this.client.disconnect();
			return;
		}

		String[] args = cmd.split(" ");

		if (args.length == 0) {
			throw new IOException("Empty message from server");
		}



		if (args[0].equals("cards")) {


				int value1 = Integer.parseInt(args[1]);
				int suit1 = Integer.parseInt(args[2]);

				int value2 = Integer.parseInt(args[3]);
				int suit2 = Integer.parseInt(args[4]);

				this.board.setCards(new Card(value1, suit1), new Card(value2, suit2));


		} else if (args[0].equals("money")) {

			
				int id = Integer.parseInt(args[1]);
				int money = Integer.parseInt(args[2]);

				this.board.setMoney(id, money);


		} else if (args[0].equals("players")) {

				int num_players = Integer.parseInt(args[1]);
				int starting_money = Integer.parseInt(args[2]);
				ArrayList<Player> players = new ArrayList<>();
				
				int itr = 1;
				int i = 3;
				while (itr < num_players) {
					int id = Integer.parseInt(args[i++]);
					String name = args[i++];
					itr++;
					players.add(new Player(id, name, starting_money));
				}
				

				this.board.setPlayers(players);


		} else if (args[0].equals("raise")) {
			board.sendRaise(Integer.parseInt(args[1]), Integer.parseInt(args[2]));



		} else if (args[0].equals("check")) {
			//do something
			board.sendCheck(Integer.parseInt(args[1]));


		} else if (args[0].equals("disconnected")) {
			board.sendDisconnect(Integer.parseInt(args[1]));

		} else if (args[0].equals("blind")) {

			this.board.sendBet(Integer.parseInt(args[1]));


		} else if (args[0].equals("fold")) {
			if (args.length == 1) {
				this.board.fold();
			} else {
				board.sendFold(Integer.parseInt(args[1]));
			}


		} else if (args[0].equals("all-in")) {
			board.sendAllin(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
			
		} else if (args[0].equals("flop")) {
		

			if (args.length < 7) throw new IOException("bad flop");

			int val1 = Integer.parseInt(args[1]);
			int suit1 = Integer.parseInt(args[2]);

			int val2 = Integer.parseInt(args[3]);
			int suit2 = Integer.parseInt(args[4]);

			int val3 = Integer.parseInt(args[5]);
			int suit3 = Integer.parseInt(args[6]);

			Card card1  = new Card(val1, suit1);
			Card card2  = new Card(val2, suit2);
			Card card3  = new Card(val3, suit3);

			this.board.setFlop(card1, card2, card3);


		} else if (args[0].equals("turn")) {

			if (args.length < 3) throw new IOException("bad turn");

			int val = Integer.parseInt(args[1]);
			int suit = Integer.parseInt(args[2]);

			Card card  = new Card(val, suit);

			this.board.setTurn(card);


		} else if (args[0].equals("river")) {

			if (args.length < 3) throw new IOException("river");

			int val = Integer.parseInt(args[1]);
			int suit = Integer.parseInt(args[2]);

			Card card  = new Card(val, suit);

			this.board.setRiver(card);


		} else if (args[0].equals("pot")) {
			this.board.setPot(Integer.parseInt(args[1]));


		} else if (args[0].equals("winnings")) {
			
			this.board.setWinnings(Integer.parseInt(args[1]), Integer.parseInt(args[2]));


		} else if (args[0].equals("player-turn")) {
			this.board.sendTurn(Integer.parseInt(args[1]));
		} else if (args[0].equals("player-cards")) {
			this.board.sendPlayerCards(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]));
		}else if (args[0].equals("you-win")) {
			this.board.sendWin();
		} else if (args[0].equals("you-lose")) {
			this.board.sendLoss();
		} else if (args[0].equals("reset-hand")) {
		
			this.board.resetHand();

		} else if (args[0].equals("game-end")) {
			this.board.sendGameEnd();
		} else {
			System.out.println("I dont know what to do with this " + args[0]);
		}
	}

}
