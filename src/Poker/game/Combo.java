package poker.game;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Combo is a class that finds and holds the best combo
 * among a 7 card hand
 */
public class Combo 
{   
    ArrayList<Card> comboHand;
    int fiveSuit = 0; //1 for diamonds, 2 for clubs, 3 for hearts, 4 for spades, 0 for nothing
    public Combo(ArrayList<Card> playerHand, ArrayList<Card> community )
    {
        for(int i=0; i<playerHand.size();i++)
        {
            comboHand.add(playerHand.get(i));
        }

        for(int i=0;i<community.size();i++)
        {
            comboHand.add(community.get(i));
        }
        Collections.sort(comboHand);
        findFiveSuit();
    }

    private void findFiveSuit()
    {
        int diamonds = 0;
        int clubs = 0;
        int hearts = 0;
        int spades = 0;
        for(int i=0;i<comboHand.size();i++)
        {
            if(comboHand.get(i).suit() == 1)
            {
                diamonds++;
            }else if(comboHand.get(i).suit() == 2)
            {
                clubs++;
            }else if(comboHand.get(i).suit() == 3)
            {
                hearts++;
            }else if(comboHand.get(i).suit() == 4)
            {
                spades++;
            }
        }
        if(diamonds == 5)
        {
            fiveSuit = 1;
        }else if(clubs == 5)
        {
            fiveSuit = 2;
        }else if(hearts == 5)
        {
            fiveSuit = 3;
        }else if(spades == 5)
        {
            fiveSuit = 4;
        }
    }
}