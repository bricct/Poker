package poker.game;

/**
 * Card is a class representation of a standard poker card
 * It is defined by it's value (2-A) and its suit (1-4)
 * 
 */
public class Card 
{
    private int value; //2-14
    private int suit; //1-4, diamond lowest spade highest

    /**
     * Constructor for a card
     * @param ivalue value from 2-14 to represent 2 to A
     * @param isuit value of suit from 1-4 for diamond, club, heart, spade
     */
    public Card(int ivalue, int isuit)
    {
        if( 2 <= ivalue && ivalue <= 14 && 2 <= isuit && isuit <= 4)
        {
            value = ivalue;
            suit = isuit;
        }else
        {
            throw new IllegalArgumentException("Suit or value argument not within range");
        }
        
    }

    /**
     * Returns value of the card
     */
    public int value()
    {
        return value;
    }

    /**
     * Returns suit of the card
     */
    public int suit()
    {
        return suit;
    }
}