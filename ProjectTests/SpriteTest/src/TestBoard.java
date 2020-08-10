import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class TestBoard extends JFrame {

	private static final long serialVersionUID = 1L;
	private JButton newCard;
	private TestPanel panel;
	private Deck deck;
	private Card card1, card2;
	
	public TestBoard() {
		this.setSize(906, 520);
		this.deck = new Deck();
		this.deck.shuffle();
		
		this.newCard = new JButton("New Cards");
		this.card1 = this.deck.draw();
		this.card2 = this.deck.draw();
		this.panel = new TestPanel(this.card1, this.card2);
		
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
		
		
		this.add(newCard, BorderLayout.SOUTH);
		this.add(this.panel, BorderLayout.CENTER);
		
		
		
		
	}
	
	
	
	
	
	
}
