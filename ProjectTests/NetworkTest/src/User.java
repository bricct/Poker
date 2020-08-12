import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;

public class User {
	
	
	private Client client;
	private TestBoard board;
	private final int id;
	public User() {
	
		
		
		//PriorityQueue<String> commands = new PriorityQueue<>();
		
		board = new TestBoard(this);
		board.setVisible(true);
		board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			Thread.sleep(200);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		this.client = new Client(this);
		this.id = client.connect();
		System.out.println("" + id);
		if (id == -1) System.out.println("uhoh");
//		JFrame frame = new JFrame();
//		frame.setVisible(true);
//		frame.setResizable(false);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton b = new JButton("send message");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			  {
			    try {
			    	client.sendMessage(id + " fold");
			    } catch (Exception ee) {
			    	System.out.println("oopsies");
			    	ee.printStackTrace();
			    }
			  }
		});
		
//		frame.add(b);
//		frame.pack();
		
		UThread t = new UThread(client);
		t.start();
		
		
		
		
		
		
		
//			while(true) {
//		    	String cmd = commands.poll();
//		    	if (cmd != null) {
//		    		System.out.println(cmd);
//		    		
//		    	}
//		    	try {
//					Thread.sleep(20);
//				} catch (InterruptedException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//		    }
		
		
	   
	}
	
	public void sendMessage(String msg) throws Exception {
		try {
	    	client.sendMessage(id + " " + msg);
	    } catch (Exception ee) {
	    	System.out.println("oopsies");
	    	ee.printStackTrace();
	    }
	}
	
	
	public void operate(String cmd) throws Exception {
		if (cmd.equals("kicked")) { 
			this.client.disconnect();
			return;
		}
		
		String[] args = cmd.split(" ");
		
		if (args.length == 0) {
			throw new IOException("Empy message from server");
		}
		
		
		
		if (args[0].equals("cards")) {
//			try {
				
				int value1 = Integer.parseInt(args[1]);
				int suit1 = Integer.parseInt(args[2]);
				
				int value2 = Integer.parseInt(args[3]);
				int suit2 = Integer.parseInt(args[4]);
				
				this.board.setCards(new Card(value1, suit1), new Card(value2, suit2));
				
//			} catch (NumberFormatException e1) {
//				System.out.println("Error: cannot parse card values");
//				return;
//			} catch (IndexOutOfBoundsException e2) {
//				System.out.println("Error: not enough card values passed in");
//				return;
//			}
		} else if (args[0].equals("money")) {
//			try {

				int money = Integer.parseInt(args[1]);
				
				System.out.println(money + " " + (this.board == null));
				
				this.board.setMoney(money);
				
//			} catch (NumberFormatException e1) {
//				System.out.println("Error: cannot parse money value");
//				return;
//			}
			
		} else if (args[0].equals("players")) {
//			try {

				int num_players = Integer.parseInt(args[1]);
				
				int[] players = new int[num_players];
				
				for (int i = 1; i < num_players; i++) {
					players[i-1] = Integer.parseInt(args[i+1]);
				}

				this.board.setPlayers(players);
				
//			} catch (NumberFormatException e1) {
//				System.out.println("Error: cannot parse player values");
//				return;
//			}
		
		} else if (args[0].equals("raise")) {
			//do something
			
			
			
		} else if (args[0].equals("check")) {
			//do something
			
			
			
		} else if (args[0].equals("disconnected")) {
			//do something
			
		} else if (args[0].equals("blind")) {
			
			this.board.sendBet(Integer.parseInt(args[1]));
			
			
		} else if (args[0].equals("fold")) {
			if (args.length == 1) {
				this.board.fold();
			}
		
			
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
			this.board.setWinnings(Integer.parseInt(args[1]));
			
			
		} else if (args[0].equals("reset-hand")) {
			this.board.resetHand();
			
		} else {
			System.out.println("I dont know what to do with this " + args[0]);
		}
	}
	
	
	
	
	
}