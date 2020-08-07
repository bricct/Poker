package poker.game;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Deck is a class representation of a standard poker deck.
 * 
 * It has standard deck operations such as drawing and shuffling.
 */
public class Deck {
    ArrayList<Card> deck;

    /**
     * Constructor for the deck class
     */
    public Deck()
    {
        resetDeck();
    }

    /**
     * Resets the card back to full from 2-A diamonds-spades
     */
    public void resetDeck()
    {
        deck = new ArrayList<Card>();
        for(int i=2; i<15;i++)
        {
            for(int j=1; j<5; j++)
            {
                deck.add(new Card(i,j));
            }
        }
    }

    /**
     * Draws a card and returns the card and removes it from
     * the deck
     * 
     * @return card that is drawn
     */
    public Card draw()
    {
        return deck.remove(0);
    }


    /**
     * Shuffles the deck using collections.shuffle()
     */
    public void shuffle()
    {
        Collections.shuffle(deck);
    }


}