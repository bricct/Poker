package poker.game;

/**
 * Card is a class representation of a standard poker card
 * It is defined by it's value (2-A) and its suit (1-4)
 * 
 */
public class Card implements Comparable<Card>
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

    /**
     * hash function for Card class using its string id
     * @return hashvalue of Card class
     */
    @Override
    public int hashCode()
    {
        return value * 100 + suit;
    }

    /**
     * Comparator function for two cards
     *
     * @param other the other card
     * @return return true if this card is stronger than other card
     */
    public boolean stronger(Card other)
    {
        if(this.value == other.value)
        {
            if(this.suit > other.suit)
            {
                return true;
            }else{
                return false;
            }
        }else
        {
            if(this.value > other.value)
            {
                return true;
            }else
            {
                return false;
            }
        }
    }

    /**
     * @param other card to compare to
     * @return int for less than or greater than
     */
    @Override
    public int compareTo(Card other)
    {
        if(this.value == other.value)
        {
            if(this.suit > other.suit)
            {
                return 1;
            }else if(this.suit < other.suit){
                return -1;
            }else
            {
                return 0;
            }
        }else
        {
            if(this.value > other.value)
            {
                return 1;
            }else
            {
                return -1;
            }
        }
    }

    @Override
    public boolean equals(Object o) { 
        if (o == this) { 
            return true; 
        } 
  
        if (!(o instanceof Card)) { 
            return false; 
        } 
          
        // typecast o to Complex so that we can compare data members  
        Card c = (Card) o; 
          
        if(this.value == c.value && this.suit == c.suit)
        {
            return true;
        }else
        {
            return false;
        }
    }
    
}