
import java.util.ArrayList;

/**
 * Player is a class representation for a poker player
 * 
 * The class player composes of its hand and the amount of money they have
 */
public class Player {
    private ArrayList<Card> hand;
    private int money;
    private final int id;
    private String name;

    /**
     * Constructor class for setting up the player and determining how much
     * money they start with.
     */
    public Player(int iid, String iname, int imoney)
    {
        if(imoney < 0)
        {
            throw new IllegalArgumentException("Money can't be negative");
        }
        money = imoney;
        id = iid;
        name = iname;
        hand = new ArrayList<>();
    }

    /**
     * Adds a card to the hand if the player has 0 or 1 cards, and returns true
     * 
     * @param card card to be added
     * @return returns false if card was not added, true if it was
     */
    public boolean addToHand(Card card)
    {
        if(hand.size() < 2)
        {
            hand.add(card);
            return true;
        }else{
            return false;
        }
    }

    /**
     * Clears the player's hand
     */
    public void clearHand()
    {
        hand.clear();
    }
    
    /**
     * Adds money to the player's hand
     */
    public void addMoney(int imoney)
    {
        money += imoney;
    }

    /**
     * Subtracts money from player's hand
     */
    public boolean subMoney(int imoney)
    {
        if(money - imoney >= 0)
        {
            money -= imoney;
            return true;
        }
        return false;
    }

    /**
     * Getter function for money in hand
     * @return amount of money in hand
     */
    public int getMoney()
    {
        return money;
    }
    
    
    public int getid() {
    	return id;
    }
    
    public String getName() {
    	return name;
    }
    
    
    /**
     * Gets first card of the hand
     * @return first card
     */
    public Card firstCard()
    {
        return hand.get(0);
    }

    /**
     * Gets second card of the hand
     * @return second card
     */
    public Card secCard()
    {
        return hand.get(1);
    }
    
    
    
    
    
}