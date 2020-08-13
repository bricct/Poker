import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TestBoard extends JFrame {

	private static final long serialVersionUID = 1L;
	private JButton fold, call, raise;
	private TestPanel panel;
	private JPanel buttonPanel;
	// private Card card1, card2;
//	private User user;
	//private boolean turn;
	private int num_players;
	private int[] players;
	private Card[] table_cards;
	
	public TestBoard(User user) {
		this.setSize(906, 520);		
//		this.user = user;
		this.buttonPanel = new JPanel(new GridLayout(1, 3));
		
		this.fold = new JButton("Fold");
		this.call = new JButton("Call");
		this.raise = new JButton("Raise");
		
		this.num_players = 1;
		this.players = new int[0];
		
		//this.turn = false;
		
		// this.card1 = null;
		// this.card2 = null;
		System.out.println("reinit");
		this.panel = new TestPanel(null, null);
		
		this.setIconImage(Sprite1.getIconSprite());
		
		
		
		this.fold.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				//if (!turn) return;
				try {
			    	user.sendMessage("fold");
			    	//fold();
			    } catch (Exception ee) {
			    	System.out.println("oopsies");
			    	ee.printStackTrace();
			    }

				//turn = false;
				
				// panel.setCards(card1, card2);
			}
			
		});
		
		
		this.call.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				

				try {
			    	user.sendMessage("check");
			    } catch (Exception ee) {
			    	System.out.println("oopsies");
			    	ee.printStackTrace();
			    }


			}
			
		});
		
		
		this.raise.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				

				try {
					user.sendMessage("raise " + 20);
					panel.addToPot(20);
			    } catch (Exception ee) {
			    	System.out.println("oopsies");
			    	ee.printStackTrace();
			    }


			}
			
		});
		
		this.buttonPanel.add(fold);
		this.buttonPanel.add(call);
		this.buttonPanel.add(raise);
		
		this.add(this.buttonPanel, BorderLayout.SOUTH);
		this.add(this.panel, BorderLayout.CENTER);
		
		
		
		
	}
	
	
	public void fold() {
		System.out.println("folding");
		// card1 = null;
		// card2 = null;
		panel.fold();
		panel.setCards(null, null);
	}
	
	
	
	public void setCards(Card card1, Card card2) {
		// this.card1 = card1;
		// this.card2 = card2;
		panel.setCards(card1, card2);
	}
	
	public void setMoney(int money) {
		this.panel.setMoney(money);
	}
	
	
	public void setPlayers(int[] players) {
		this.num_players = players.length;
		this.players = players;
		this.panel.setPlayers(this.num_players, this.players);
	}
	
	
	public void setPot(int pot) {
		this.panel.setPot(pot);
	}
	
	
	public void setFlop(Card card1, Card card2, Card card3) {
			table_cards = new Card[5];
			
			table_cards[0] = card1;
			table_cards[1] = card2;
			table_cards[2] = card3;
			table_cards[3] = null;
			table_cards[4] = null;
			
		panel.flop(table_cards);
	}
	
	
	public void setTurn(Card card) {
		
		table_cards[3] = card;
		table_cards[4] = null;
		
		panel.turn(table_cards);
	}
	
	public void setRiver(Card card) {

		table_cards[4] = card;
		
		panel.river(table_cards);
	}
	
	public void setWinnings(int pot) {
		this.panel.winHand(pot);
	}
	
	public void resetHand() {
		// this.card1 = null;
		// this.card2 = null;
		System.out.println("????");
		table_cards = new Card[5];
		this.panel.reset();
	}


	public void sendBet(int bet) {
		this.panel.addToPot(bet);
	}
	
	
}
