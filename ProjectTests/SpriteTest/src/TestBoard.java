import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TestBoard extends JFrame {

	private static final long serialVersionUID = 1L;
	private JButton newCard, bet, resetMoney;
	private TestPanel panel;
	private JPanel buttonPanel;
	private Deck deck;
	private Card card1, card2;
	private int money;
	
	public TestBoard() {
		this.setSize(906, 520);
		this.deck = new Deck();
		this.deck.shuffle();
		
		this.buttonPanel = new JPanel(new GridLayout(1, 3));
		
		this.newCard = new JButton("New Cards");
		this.bet = new JButton("Bet $37");
		this.resetMoney = new JButton("Reset Money");
		
		this.money = 1500;
		
		this.card1 = this.deck.draw();
		this.card2 = this.deck.draw();
		this.panel = new TestPanel(this.card1, this.card2, this.money);
		
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
				if (money - 37 >= 0) money -= 37;
				panel.setMoney(money);
			}
			
		});
		
		
		this.resetMoney.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				money = 2960;
				panel.setMoney(money);
			}
			
		});
		
		this.buttonPanel.add(newCard);
		this.buttonPanel.add(bet);
		this.buttonPanel.add(resetMoney);
		
		this.add(this.buttonPanel, BorderLayout.SOUTH);
		this.add(this.panel, BorderLayout.CENTER);
		
		
		
		
	}
	
	
	
	
	
	
}
