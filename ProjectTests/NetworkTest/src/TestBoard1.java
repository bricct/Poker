import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TestBoard1 extends JFrame {

	private static final long serialVersionUID = 1L;
	private JButton fold, call, raise;
	private TestPanel1 panel;
	private JPanel buttonPanel;
	private Card card1, card2;
//	private User user;
	private boolean turn;
	private int num_players;
	private int[] players;
	
	public TestBoard1(User user) {
		this.setSize(906, 520);		
//		this.user = user;
		this.buttonPanel = new JPanel(new GridLayout(1, 3));
		
		this.fold = new JButton("Fold");
		this.call = new JButton("Call");
		this.raise = new JButton("Raise");
		
		this.num_players = 1;
		this.players = new int[0];
		
		this.turn = false;
		
		this.card1 = null;
		this.card2 = null;
		this.panel = new TestPanel1(this.card1, this.card2);
		
		this.setIconImage(Sprite1.getIconSprite());
		
		
		
		this.fold.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				//if (!turn) return;
				try {
			    	user.sendMessage("fold");
			    	card1 = null;
					card2 = null;
					panel.fold();
					panel.setCards(card1, card2);
			    } catch (Exception ee) {
			    	System.out.println("oopsies");
			    	ee.printStackTrace();
			    }

				turn = false;
				
				panel.setCards(card1, card2);
			}
			
		});
		
		
		this.call.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				if (!turn) return;
				try {
			    	user.sendMessage("call");;
			    } catch (Exception ee) {
			    	System.out.println("oopsies");
			    	ee.printStackTrace();
			    }

				turn = false;
			}
			
		});
		
		
		this.raise.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				if (!turn) return;
				try {
			    	user.sendMessage("raise");
			    } catch (Exception ee) {
			    	System.out.println("oopsies");
			    	ee.printStackTrace();
			    }

				turn = false;
			}
			
		});
		
		this.buttonPanel.add(fold);
		this.buttonPanel.add(call);
		this.buttonPanel.add(raise);
		
		this.add(this.buttonPanel, BorderLayout.SOUTH);
		this.add(this.panel, BorderLayout.CENTER);
		
		
		
		
	}
	
	
	public void setCards(Card card1, Card card2) {
		this.card1 = card1;
		this.card2 = card2;
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
	
	
	
	
}
