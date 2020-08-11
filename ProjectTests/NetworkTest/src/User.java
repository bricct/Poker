import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class User {
	
	
	private Client client;
	private TestBoard board;
	private final int id;
	public User() {
	
		
		
		//PriorityQueue<String> commands = new PriorityQueue<>();
		
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
		
		
		
		
		board = new TestBoard(this);
		board.setVisible(true);
		board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
		System.out.println("gg");
		
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
	
	
	public void operate(String cmd) {
		if (cmd.equals("disconnected")) { 
			this.client.disconnect();
			return;
		}
		
		String[] args = cmd.split(" ");
		
		if (args.length == 0) {
			System.out.println("uh-oh empty command from the server");
			return;
		}
		
		
		
		if (args[0].equals("cards")) {
			try {
				
				int value1 = Integer.parseInt(args[1]);
				int suit1 = Integer.parseInt(args[2]);
				
				int value2 = Integer.parseInt(args[3]);
				int suit2 = Integer.parseInt(args[4]);
				
				this.board.setCards(new Card(value1, suit1), new Card(value2, suit2));
				
			} catch (NumberFormatException e1) {
				System.out.println("Error: cannot parse card values");
				return;
			} catch (IndexOutOfBoundsException e2) {
				System.out.println("Error: not enough card values passed in");
				return;
			}
		}
			
			
//		} else if (cmd.equals("raise")) {
//			//do something
//			distributeCmd(id, cmd);
//		} else if (cmd.equals("check")) {
//			//do something
//			distributeCmd(id, cmd);
//		} else if (cmd.equals("call")) {
//			//do something
//			distributeCmd(id, cmd);
//		} else {
//			//distributeCmd(id, cmd);
//			return -1;
//		}
	}
	
	
	
	
	
}