import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TestBoard extends JFrame {

	private static final long serialVersionUID = 1L;
	private JButton newCard, bet, resetMoney, flop,  turn, river;
	private TestPanel panel;
	private JPanel buttonPanel;
	private Deck deck;
	private Card card1, card2;
	private Card[] table_cards;
	private int money;
	
	
	public TestBoard(boolean mus_toggle, boolean mode) {
		
		this.deck = new Deck();
		this.deck.shuffle();
		
		this.buttonPanel = new JPanel(new GridLayout(1, 6));
		
		this.newCard = new JButton("New Cards");
		this.bet = new JButton("Bet $37");
		this.resetMoney = new JButton("Reset Money");
		this.flop = new JButton("Flop");
		this.turn = new JButton("Turn");
		this.river = new JButton("River");
		
		this.money = 1500;
		
		this.table_cards = new Card[5];
		
		for (int i = 0; i < 5; i++) {
			this.table_cards[i] = null;
		}
		
		
		this.card1 = this.deck.draw();
		this.card2 = this.deck.draw();
		this.panel = new TestPanel(this.card1, this.card2, this.money, mus_toggle, mode);
		
		this.setIconImage(Sprite.getIconSprite());
		
		
		//System.out.println("New Card Suit: " + card.suit() + " Value: " + card.value());
		
		this.newCard.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				try {
					card1 = deck.draw();
					card2 = deck.draw();
					
				} catch (IndexOutOfBoundsException ee) {
					deck = new Deck();
					deck.shuffle();
					card1 = deck.draw();
					card2 = deck.draw();
				}

				//System.out.println("New Card Suit: " + card.suit() + " Value: " + card.value());
				panel.setCards(card1, card2);
			}
			
		});
		
		
		this.bet.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if (money - 37 >= 0) { 
					money -= 37;
					panel.addToPot(37);
				}
				panel.setMoney(money);
			}
			
		});
		
		
		this.resetMoney.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				money = 2960;
				panel.setMoney(money);
			}
			
		});
		
		this.flop.addActionListener(new ActionListener() {
					
			public void actionPerformed(ActionEvent e) {
			
				try {
					table_cards[0] = deck.draw();
					table_cards[1] = deck.draw();
					table_cards[2] = deck.draw();
				} catch (IndexOutOfBoundsException ee) {
					deck = new Deck();
					deck.shuffle();
					table_cards[0] = deck.draw();
					table_cards[1] = deck.draw();
					table_cards[2] = deck.draw();
				}
				panel.flop(table_cards);
			}
			
		});
		
		
		this.turn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				try {
					table_cards[3] = deck.draw();

				} catch (IndexOutOfBoundsException ee) {
					deck = new Deck();
					deck.shuffle();
					table_cards[3] = deck.draw();

				}
				panel.turn(table_cards);
			}
			
		});
		
		
		this.river.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				try {
					table_cards[4] = deck.draw();
				} catch (IndexOutOfBoundsException ee) {
					deck = new Deck();
					deck.shuffle();
					table_cards[4] = deck.draw();
				}
				panel.river(table_cards);
			}
			
		});
		
		
		
		
		this.buttonPanel.add(newCard);
		this.buttonPanel.add(bet);
		this.buttonPanel.add(resetMoney);
		this.buttonPanel.add(flop);
		this.buttonPanel.add(turn);
		this.buttonPanel.add(river);
		
		Player you  = new Player(0, "You", 69);
		Player player2 = new Player(1, "Carl", 1500);
		
		
		ArrayList<Player> players = new ArrayList<>();
		players.add(you);
		players.add(player2);
		
		panel.setPlayers(players);
		
		
		
		
		this.add(this.buttonPanel, BorderLayout.SOUTH);
		this.add(this.panel, BorderLayout.CENTER);
		
		
		
		
	}
	
	
	
	
	
	
}
