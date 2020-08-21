
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.HashMap;

/**
 * Combo is a class that finds and holds the best combo
 * among a 7 card hand
 */
public class Combo implements Comparable<Combo>
{   
    ArrayList<Card> playHand;
    ArrayList<Card> fullHand;
    HashSet<Card> cards;
    HashMap<Integer, Integer> vals;
    ArrayList<Card> comboHand; 
    int bestCombo = 1; //1 = high card, 2 = pair, 3 = two pair, 4 = three of a kind, 5 = straight, 6 = flush, 7 = full house, 8 = four of a kind, 9 = straight flush, 10 = royal flush

    //Straight properties
    int minStraight = 0; //If 10, possible royal flush

    //Flush properties
    int fiveSuit = 0; //1 for diamonds, 2 for clubs, 3 for hearts, 4 for spades, 0 for nothing

    //Count properties
    int mostNumSame = 0;
    int biggestNumVal = 0;
    int bestDouble = 0;
    int secondBestDouble = 0;
    boolean containsDouble = false;
    boolean containsDoublePair = false;
    public Combo(ArrayList<Card> playerHand, ArrayList<Card> community )
    {
        vals = new HashMap<Integer,Integer>();
        fullHand = new ArrayList<Card>();
        playHand = new ArrayList<Card>();
        cards = new HashSet<>();
        for(int i=0; i<playerHand.size();i++)
        {
            fullHand.add(playerHand.get(i));
            if(!vals.containsKey(playerHand.get(i).value()))
            {
                vals.put(playerHand.get(i).value(), 1);
            }else
            {
                vals.put(playerHand.get(i).value(), vals.get(playerHand.get(i).value()) + 1);
            }
            cards.add(playerHand.get(i));
            playHand.add(playerHand.get(i));
        }
        
        Collections.sort(playerHand);

        for(int i=0;i<community.size();i++)
        {
            fullHand.add(community.get(i));
            if(!vals.containsKey(community.get(i).value()))
            {
                vals.put(community.get(i).value(), 1);
            }else
            {
                vals.put(community.get(i).value(), vals.get(community.get(i).value()) + 1);
            }
            cards.add(community.get(i));
        }
        Collections.sort(fullHand);

        //Init functions
        findFiveSuit();
        checkStraight();
        findMostPopulous();
        findBestCombo();

    }

    private void findFiveSuit()
    {
        int diamonds = 0;
        int clubs = 0;
        int hearts = 0;
        int spades = 0;
        for(int i=0;i<fullHand.size();i++)
        {
            if(fullHand.get(i).suit() == 1)
            {
                diamonds++;
            }else if(fullHand.get(i).suit() == 2)
            {
                clubs++;
            }else if(fullHand.get(i).suit() == 3)
            {
                hearts++;
            }else if(fullHand.get(i).suit() == 4)
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


    private void checkStraight()
    {
        //Highest straight is 10-A (Broadway)
        for(int i=2;i<11;i++)
        {
            int counter = 0;
            for(int j=i;j<i+5;j++)
            {
                if(vals.containsKey(j))
                {
                    counter++;
                }
                else
                {
                    break;
                }
            }
            boolean wheel = false;
            //If i is 2 and there's 2,3,4 but no 5 and there's Ace (Wheel)
            if(i == 2 && counter == 4 && vals.containsKey(14))
            {
                counter ++;
                wheel = true;
            }
            if(counter == 5)
            {
                if(!wheel)
                {
                    minStraight = i;
                }
                else
                {
                    minStraight = 14;
                }
            }
        }
    }

    private boolean checkStraightFlush(int start)
    {
        int cardIt = start;
        if(fiveSuit > 0)
        {
            int count = 0;
            if(minStraight == 14)
            {
                if(fullHand.contains(new Card(14, fiveSuit)))
                {
                    count++;
                    cardIt++;
                }
                start = 2;
                for(int i=start;i<start+4;i++)
                {
                    
                    if(fullHand.contains(new Card(cardIt, fiveSuit)))
                    {
                        count++;
                        cardIt++;
                    }
                }
            }else
            {
                for(int i=start;i<start+5;i++)
                {
                    
                    if(fullHand.contains(new Card(cardIt, fiveSuit)))
                    {
                        count++;
                        cardIt++;
                    }
                }
            }
            if(count == 5)
            {
                return true;
            }
        }
        return false;
    }

    private void findMostPopulous()
    {
        ArrayList<Integer> popVals = new ArrayList<Integer>(vals.keySet());
        Collections.sort(popVals);
        for(int i=popVals.size()-1;i > -1;i--)
        {
            //System.out.print("yea that worked: "+popVals.get(i)+ " " + vals.get(popVals.get(i)) + "\n");
            if(vals.get(popVals.get(i)) > mostNumSame)
            {
                if(mostNumSame == 2 && vals.get(popVals.get(i)) == 3)
                {
                    containsDouble = true;
                }
                mostNumSame = vals.get(popVals.get(i));
                biggestNumVal = popVals.get(i);
            }else if(vals.get(popVals.get(i)) <= mostNumSame)
            {
                if(vals.get(popVals.get(i)) == 2)
                {
                    secondBestDouble = popVals.get(i);
                    containsDoublePair = true;
                }else if(vals.get(popVals.get(i)) == 3)
                {
                    bestDouble = popVals.get(i);
                }
            }
        }
    }

    private void findBestCombo()
    {
        comboHand = new ArrayList<Card>();
        if(minStraight == 10 && checkStraightFlush(10))
        {
            bestCombo = 10;
            int start = 10;
            for(int i=0;i<fullHand.size();i++)
            {
                if(fullHand.get(i).value() == start && fullHand.get(i).suit() == fiveSuit)
                {
                    comboHand.add(fullHand.get(i));
                    start++;
                }
            }
        }else if(minStraight > 0 && checkStraightFlush(minStraight))
        {
            bestCombo = 9;
            int start = minStraight;
            if(start == 14)
            {
                for(int i=fullHand.size()-1;i>-1;i--)
                {
                    if(fullHand.get(i).value() == start && fullHand.get(i).suit() == fiveSuit)
                    {
                        comboHand.add(fullHand.get(i));
                        fullHand.remove(i);
                        break;
                    }
                }
                start = 2;
                for(int i=0;i<fullHand.size();i++)
                {
                    if(fullHand.get(i).value() == start && fullHand.get(i).suit() == fiveSuit)
                    {
                        comboHand.add(fullHand.get(i));
                        start++;
                    }
                }
            }else
            {
                for(int i=0;i<fullHand.size();i++)
                {
                    if(fullHand.get(i).value() == start && fullHand.get(i).suit() == fiveSuit)
                    {
                        comboHand.add(fullHand.get(i));
                        start++;
                    }
                }
            }
            if(comboHand.size() > 5)
            {
                for(int i=0;i<comboHand.size() - 5;i++)
                {
                    comboHand.remove(0);
                }
            }
        }else if(mostNumSame == 4)
        {
            bestCombo = 8;
            for(int i=0;i<fullHand.size();i++)
            {
                if(fullHand.get(i).value() == biggestNumVal)
                {
                    comboHand.add(fullHand.get(i));
                }
            }
        }else if(mostNumSame == 3 && containsDouble)
        {
            bestCombo = 7;
            for(int i=0;i<fullHand.size();i++)
            {
                if(fullHand.get(i).value() == biggestNumVal || fullHand.get(i).value() == bestDouble)
                {
                    comboHand.add(fullHand.get(i));
                }
            }
        }else if(fiveSuit == 5)
        {
            bestCombo = 6;
            int count = 0;
            for(int i=fullHand.size()-1;i>-1;i--)
            {
                if(fullHand.get(i).suit() == fiveSuit){
                    comboHand.add(fullHand.get(i));
                    count++;
                    //for cases when there are more suit cards
                    if(count > 5)
                    {
                        break;
                    }
                }
            }

        }else if(minStraight > 0)
        {
            bestCombo = 5;
            int start = minStraight;
            if(start == 14)
            {
                for(int i=fullHand.size()-1;i>-1;i--)
                {
                    if(fullHand.get(i).value() == 14)
                    {
                        comboHand.add(fullHand.get(i));
                        fullHand.remove(i);
                        break;
                    }
                   
                }
                start = 2;
                for(int i=0;i<fullHand.size();i++)
                {
                    if(fullHand.get(i).value() == start)
                    {
                        comboHand.add(fullHand.get(i));
                        start++;
                    }
                }
            }else
            {
                for(int i=0;i<fullHand.size();i++)
                {
                    if(fullHand.get(i).value() == start)
                    {
                        comboHand.add(fullHand.get(i));
                        start++;
                    }
                }
            }
            if(comboHand.size() > 5)
            {
                for(int i=0;i<comboHand.size() - 5;i++)
                {
                    comboHand.remove(0);
                }
            }
        }else if(mostNumSame == 3)
        {
            bestCombo = 4;
            for(int i=fullHand.size()-1;i>-1;i--)
            {
                if(fullHand.get(i).value() == mostNumSame){
                    comboHand.add(fullHand.get(i));
                }
            }
        }else if(containsDoublePair)
        {
            bestCombo = 3;
            for(int i=fullHand.size()-1;i>-1;i--)
            {
                if(fullHand.get(i).value() == bestDouble || fullHand.get(i).value() == secondBestDouble){
                    comboHand.add(fullHand.get(i));
                }
            }
        }else if(mostNumSame == 2 )
        {
            bestCombo = 2;
            for(int i=fullHand.size()-1;i>-1;i--)
            {
                if(fullHand.get(i).value() == bestDouble)
                {
                    comboHand.add(fullHand.get(i));
                }
            }
        }else
        {
            for(int i=fullHand.size()-1;i>-1;i--)
            {
                if(fullHand.get(i).value() == biggestNumVal)
                {
                    comboHand.add(fullHand.get(i));
                }
            }
            bestCombo = 1;
        }
        Collections.sort(comboHand);
    }

    /**
     * @return returns the best combo among the cards
     */
    public ArrayList<Card> getCombo()
    {
        ArrayList<Card> cards = new ArrayList<Card>();
        for(int i=0;i<comboHand.size();i++)
        {
            cards.add(new Card(comboHand.get(i).value(),comboHand.get(i).suit()));
        }
        return comboHand;
    }

    /**
     * @param other card to compare to
     * @return int for less than or greater than
     */
    @Override
    public int compareTo(Combo other)
    {
        // for(int i=0;i<other.comboHand.size();i++)
        // {
        //     System.out.print("hand:");
        //     System.out.print(other.comboHand.get(i).value());
        // }
        // System.out.print("\n");
        // System.out.print(this.comboHand.size() + " " + other.comboHand.size() + "\n");
        // System.out.print(this.bestCombo + " " + other.bestCombo + "\n");
        if(this.bestCombo > other.bestCombo)
        {
            return 1;
        }else if(this.bestCombo < other.bestCombo)
        {
            return -1;
        }else
        {
            //straight flush
            if(this.bestCombo == 9 || this.bestCombo == 5)
            {
                //Wheel
                if(this.comboHand.get(4).value() == 14 && this.comboHand.get(0).value() == 2 && other.comboHand.get(4).value() == 5)
                {
                    return -1;
                }else if(other.comboHand.get(4).value() == 14 && other.comboHand.get(0).value() == 2 && this.comboHand.get(4).value() > 5 && this.comboHand.get(4).value() != 14)
                {
                    return 1;
                }else //check from top down who has higher straight
                {
                    for(int i=this.comboHand.size()-1;i>-1;i--)
                    {
                        if(this.comboHand.get(i).compareTo(other.comboHand.get(i)) > 0)
                        {
                            return 1;
                        }else if(this.comboHand.get(i).compareTo(other.comboHand.get(i)) < 0)
                        {
                            return -1;
                        }
                    }
                }
                //Impossible to have two people with straight flush of different suit                
            }else if(this.bestCombo == 7)
            {
                if(this.biggestNumVal > other.biggestNumVal)
                {
                    return 1;
                }else if(this.biggestNumVal < other.biggestNumVal)
                {
                    return -1;
                }else
                {
                    if(this.bestDouble > other.bestDouble)
                    {
                        return 1;
                    }else if(this.bestDouble < other.bestDouble)
                    {
                        return -1;
                    }else
                    {
                        for(int i=this.comboHand.size()-1;i>-1;i--)
                        {
                            if(this.comboHand.get(i).compareTo(other.comboHand.get(i)) > 0)
                            {
                                return 1;
                            }else if(this.comboHand.get(i).compareTo(other.comboHand.get(i)) < 0)
                            {
                                return -1;
                            }
                        }
                    }
                }
            }else //royal flush, flush, 4ofk, 3ofk,2ofk,2pairk,1high all check for highest card down
            {
                for(int i=this.comboHand.size()-1;i>-1;i--)
                {
                    if(this.comboHand.get(i).compareTo(other.comboHand.get(i)) > 0)
                    {
                        return 1;
                    }else if(this.comboHand.get(i).compareTo(other.comboHand.get(i)) < 0)
                    {
                        return -1;
                    }
                }
                System.out.println(this.playHand == null);
                System.out.println(other.playHand == null);
                if(this.playHand.get(1).compareTo(other.playHand.get(1)) > 0)
                {
                    return 1;
                }else
                {
                    return -1;
                }
            }
            return 0;
        }
    }
}