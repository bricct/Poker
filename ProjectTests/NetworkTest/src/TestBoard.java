import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 * @author Trey Briccetti
 * @version v1.0
 *
 */
public class TestBoard extends JFrame {

	private static final long serialVersionUID = 1L;
	private JButton fold, call, raise;
	private TestPanel panel;
	private JPanel buttonPanel;
	private Card[] table_cards;
	private TestMenu master;
	private JSpinner raise_spinner;
	/**
	 * @param master
	 * @param user
	 * @param id
	 */
	public TestBoard(TestMenu master, User user, int id) {

		this.master = master;
		this.buttonPanel = new JPanel(new GridLayout(1, 4));

		this.fold = new JButton("Fold");
		this.call = new JButton("Call");
		this.raise = new JButton("Raise");
		this.raise_spinner = new JSpinner(new SpinnerNumberModel(1, 1, 1500, 1));

		System.out.println("reinit");
		this.panel = new TestPanel(this, null, null, id);

		this.setIconImage(Sprite.getIconSprite());



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
					user.sendMessage("raise " + (int) raise_spinner.getValue());
					panel.addToPot((int) raise_spinner.getValue());
			    } catch (Exception ee) {
			    	System.out.println("oopsies");
			    	ee.printStackTrace();
			    }


			}

		});

		this.buttonPanel.add(fold);
		this.buttonPanel.add(call);
		this.buttonPanel.add(raise);
		this.buttonPanel.add(raise_spinner);

		this.add(this.buttonPanel, BorderLayout.SOUTH);
		this.add(this.panel, BorderLayout.CENTER);

	}





	/**
	 * 
	 */
	public void fold() {
		System.out.println("folding");
		panel.repaint();
		panel.setCards(null, null);
	}



	/**
	 * @param card1
	 * @param card2
	 */
	public void setCards(Card card1, Card card2) {
		panel.setCards(card1, card2);
	}

	/**
	 * @param id
	 * @param money
	 */
	public void setMoney(int id, int money) {
		this.panel.setMoney(id, money);
	}


	/**
	 * @param players
	 */
	public void setPlayers(ArrayList<Player> players) {
		this.panel.setPlayers(players);
	}


	/**
	 * @param pot
	 */
	public void setPot(int pot) {
		this.panel.setPot(pot);
	}


	/**
	 * @param card1
	 * @param card2
	 * @param card3
	 */
	public void setFlop(Card card1, Card card2, Card card3) {
			table_cards = new Card[5];

			table_cards[0] = card1;
			table_cards[1] = card2;
			table_cards[2] = card3;
			table_cards[3] = null;
			table_cards[4] = null;

		panel.flop(table_cards);
	}


	/**
	 * @param card
	 */
	public void setTurn(Card card) {

		table_cards[3] = card;
		table_cards[4] = null;

		panel.turn(table_cards);
	}

	/**
	 * @param card
	 */
	public void setRiver(Card card) {

		table_cards[4] = card;

		panel.river(table_cards);
	}

	/**
	 * @param id
	 * @param pot
	 */
	public void setWinnings(int id, int pot) {
		this.panel.winHand(id, pot);
	}
	
	/**
	 * @param id
	 */
	public void sendTurn(int id) {
		this.panel.turn(id);
	}

	/**
	 * 
	 */
	public void resetHand() {
		table_cards = new Card[5];
		this.panel.reset();
	}


	/**
	 * @param bet
	 */
	public void sendBet(int bet) {
		this.panel.addToPot(bet);
	}


	/**
	 * 
	 */
	public void sendLoss() {
		this.panel.lose();
		
	}





	/**
	 * @param id
	 */
	public void sendFold(int id) {
		this.panel.fold(id);
		
	}


	/**
	 * @param id
	 */
	public void sendDisconnect(int id) {
		this.panel.dc(id);
		
	}


	/**
	 * @param id
	 */
	public void sendCheck(int id) {
		this.panel.check(id);
		
	}


	/**
	 * @param id
	 * @param bet
	 */
	public void sendRaise(int id, int bet) {
		this.panel.raise(id, bet);
		
	}
	
	/**
	 * @param id
	 * @param value1
	 * @param suit1
	 * @param value2
	 * @param suit2
	 */
	public void sendPlayerCards(int id, int value1, int suit1, int value2, int suit2) {
		this.panel.setPlayerCards(id, new Card(value1, suit1), new Card(value2, suit2));
		
	}



	/**
	 * 
	 */
	public void sendWin() {
		this.panel.win();
		
	}


	/**
	 * @param id
	 * @param bet
	 */
	public void sendAllin(int id, int bet) {
		this.panel.sendAllin(id, bet);
		
	}


	/**
	 * 
	 */
	public void sendGameEnd() {
		this.panel.gameEnd();
		
	}


	/**
	 * 
	 */
	public void exit() {
		this.master.menu();
		this.master.setVisible(true);
		this.master.setLocationRelativeTo(this);
		this.master.setSize(this.getWidth(), this.getHeight());
		this.dispose();
		
	}


	

}
