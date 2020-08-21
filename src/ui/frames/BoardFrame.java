package ui.frames;

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

import game.Card;
import game.Player;
import netwrk.User;
import ui.Sprite;
import ui.panels.BoardPanel;

/**
 * @author Trey Briccetti
 * @version v1.0
 *
 */
public class BoardFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JButton fold, call, raise;
	private BoardPanel panel;
	private JPanel buttonPanel;
	private Card[] table_cards;
	private MenuFrame master;
	private JSpinner raise_spinner;
	/** Creates a Frame that holds The game display UI
	 * @param master The Main Menu
	 * @param user The User
	 * @param id Unique identifier of the user used in networking
	 */
	public BoardFrame(MenuFrame master, User user, int id) {

		this.master = master;
		this.buttonPanel = new JPanel(new GridLayout(1, 4));

		this.fold = new JButton("Fold");
		this.call = new JButton("Call");
		this.raise = new JButton("Raise");
		this.raise_spinner = new JSpinner(new SpinnerNumberModel(1, 1, 1500, 1));

		System.out.println("reinit");
		this.panel = new BoardPanel(this, null, null, id);

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





	/** Tells the panel to fold the current hand
	 *
	 */
	public void fold() {
		System.out.println("folding");
		panel.repaint();
		panel.setCards(null, null);
	}



	/** Sets the cards of the player to specific cards in the display
	 * @param card1 The first card to be set
	 * @param card2 The second card to be set
	 */
	public void setCards(Card card1, Card card2) {
		panel.setCards(card1, card2);
	}

	/** Sets the money of a player in the display panel
	 * @param id The unique identifier of the user in question
	 * @param money The amount of money to be set
	 */
	public void setMoney(int id, int money) {
		this.panel.setMoney(id, money);
	}


	/** Sets the players in the display
	 * @param players An array list containing the players
	 */
	public void setPlayers(ArrayList<Player> players) {
		this.panel.setPlayers(players);
	}


	/** Sets the pot in the display
	 * @param pot The amount of money to be set to the pot
	 */
	public void setPot(int pot) {
		this.panel.setPot(pot);
	}


	/** Forwards the three cards in the flop to the display
	 * @param card1 The first card in the flop
	 * @param card2 The second card in the flop
	 * @param card3 The third card in the flop
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


	/** Forwards the turn card to the display
	 * @param card The turn card
	 */
	public void setTurn(Card card) {

		table_cards[3] = card;
		table_cards[4] = null;

		panel.turn(table_cards);
	}

	/** Forwards the river card to the display
	 * @param card The river card
	 */
	public void setRiver(Card card) {

		table_cards[4] = card;

		panel.river(table_cards);
	}

	/** Sends winnings of a player to the display
	 * @param id The networking id of the player in question
	 * @param pot The amount of money won by the player in question
	 */
	public void setWinnings(int id, int pot) {
		this.panel.winHand(id, pot);
	}

	/** Updates the display to show whose turn it is
	 * @param id
	 */
	public void sendTurn(int id) {
		this.panel.turn(id);
	}

	/** Resets the panel ui for the next hand
	 *
	 */
	public void resetHand() {
		table_cards = new Card[5];
		this.panel.reset();
	}


	/** Sends a bet to the display
	 * @param bet The amount of money being bet
	 */
	public void sendBet(int bet) {
		this.panel.addToPot(bet);
	}


	/** Tells the display to show the you lose screen
	 *
	 */
	public void sendLoss() {
		this.panel.lose();

	}





	/** Tells the display to show that a user folded
	 * @param id The networking id of the player in question
	 */
	public void sendFold(int id) {
		this.panel.fold(id);

	}


	/** Tells the display that a user has disconnected
	 * @param id The id of the player who disconnected
	 */
	public void sendDisconnect(int id) {
		this.panel.dc(id);

	}


	/** Tells the display to show that a user checked
	 * @param id The networking id of the player in question
	 */
	public void sendCheck(int id) {
		this.panel.check(id);

	}


	/** Tells the display to show that a user raised
	 * @param id The networking id of the player in question
	 * @param bet The amount of money raised by the player in question
	 */
	public void sendRaise(int id, int bet) {
		this.panel.raise(id, bet);

	}

	/** Reveals the cards of another player in the game
	 * @param id The player in question
 	 * @param value1 The value of the first card
	 * @param suit1 The suit of the first card
	 * @param value2 The value of the second card
	 * @param suit2 The suit of the second card
	 */
	public void sendPlayerCards(int id, int value1, int suit1, int value2, int suit2) {
		this.panel.setPlayerCards(id, new Card(value1, suit1), new Card(value2, suit2));

	}



	/** Tells the display to show the you win screen
	 *
	 */
	public void sendWin() {
		this.panel.win();

	}


	/** Tells the display to show that a player has gone all in
	 * @param id The networking id of the player in question
	 * @param bet The amount of money the player has gone all in with
	 */
	public void sendAllin(int id, int bet) {
		this.panel.sendAllin(id, bet);

	}


	/** Tells the display to show the leave game option
	 *
	 */
	public void sendGameEnd() {
		this.panel.gameEnd();

	}


	/** Exits the game frame and returns to the main menu
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
