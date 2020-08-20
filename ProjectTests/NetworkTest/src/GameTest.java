import java.util.ArrayList;
import java.util.Iterator;

public class GameTest
{
    public static void main(String[] args)
    {
        ArrayList<Card> hand1 = new ArrayList<Card>();
        hand1.add(new Card(6, 4));
        hand1.add(new Card(4, 2));
        ArrayList<Card> hand2 = new ArrayList<Card>();
        hand2.add(new Card(14, 1));
        hand2.add(new Card(4, 1));
        ArrayList<Card> boardCards = new ArrayList<Card>();
        boardCards.add(new Card(2, 4));
        boardCards.add(new Card(2, 2));
        boardCards.add(new Card(3, 1));
        boardCards.add(new Card(5, 1));
        boardCards.add(new Card(9, 3));

        Combo combo1 = new Combo(hand1, boardCards);
        Combo combo2 = new Combo(hand2, boardCards);
        if(combo1.compareTo(combo2) == 1 || combo1.compareTo(combo2) == 0)
        {
            System.out.print("not working\n");
        }else if(combo1.compareTo(combo2) == -1)
        {
            System.out.print("working\n");
        }

    }

}

