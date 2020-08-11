package poker.game;

import java.util.ArrayList;

public class Game
{
    private int numPlayers;
    private ArrayList<Player> players;
    private Deck deck;
    private ArrayList<Card> boardCards;
    private int small;
    private int big;
    public Game(int inumPlayers, int initialIncome, int ismall, int ibig)
    {
        numPlayers = inumPlayers;
        deck = new Deck();
        small = ismall;
        big = ibig;
        for(int i=0; i<inumPlayers; i++)
        {
            players.add(new Player(initialIncome));
        }
    }

    private class PlayerTuple
    {
        Player player;
        int currMoney;
        public PlayerTuple(Player iplayer, int icurrMoney)
        {
            player = iplayer;
            currMoney = icurrMoney;
        }
    }

    public int goAroundTable(ArrayList<PlayerTuple> queue, int pot)
    {
        boolean check = false;
        int currentCheckPay = 0;
        boolean initialChecked = false;
        while(!check)
        {
            for(int i=0;i<queue.size();i++)
            {
                
                //initialChecked is for checking if it's an initialBet. After initial bet, we break at the last better
                if(initialChecked && queue.get(i).currMoney == currentCheckPay)
                {
                    return pot;
                }else{//Do the action buttons: bet, check, fold
                    if(1==1)//If player checks...
                    {
                        queue.get(i).player.subMoney(currentCheckPay - queue.get(i).currMoney);
                        pot += currentCheckPay - queue.get(i).currMoney;
                        queue.get(i).currMoney = currentCheckPay; //player set to current pot amount
                    }else if(1 ==1 ) //IF bet
                    {
                        int bet = 0; //<--------- CHANGE THIS
                        currentCheckPay += bet;
                        queue.get(i).player.subMoney(currentCheckPay - queue.get(i).currMoney);
                        pot += currentCheckPay - queue.get(i).currMoney;
                        queue.get(i).currMoney = currentCheckPay;
                    }else //Fold
                    {
                        queue.remove(i);
                        if(queue.size() == 1)
                        {
                            return pot;
                        }
                    }
                }
            }
            initialChecked = true;
            check = true;
            for(int i=0;i<queue.size();i++)
            {
                if(queue.get(i).currMoney != currentCheckPay);
                {
                    check = false;
                }
            }
        }
        return pot;
    }

    public void playRound()
    {
        int pot = 0;
        ArrayList<PlayerTuple> queue = new ArrayList<PlayerTuple>();
        boolean smallBet = false;
        boolean bigBet = false;
        //Add playable players to queue
        for(int i=0;i<players.size();i++)
        {
            if(players.get(i).getMoney() > 0)
            {
                queue.add(new PlayerTuple(players.get(i),0));
            }
        }
        if(queue.size() == 1)
        {
            return; //1 player left
        }

        queue.get(0).player.subMoney(small); //Big Blind    
        queue.get(1).player.subMoney(big); //Small blind          

        //Deal cards to players in queue
        for(int i=0;i<2;i++)
        {
            for(int j=0;j<queue.size();j++)
            {
                queue.get(j).player.addToHand(deck.draw());
            }
        }

        //pre-flop
        queue.get(0).currMoney = -1 * small; //small still has to pay
        pot = goAroundTable(queue, pot); //updated pot, queue is passed by reference
        if(queue.size() == 1)
        {
            queue.get(0).player.addMoney(pot);
        }
        //flop
        for(int i=0;i<3;i++)
        {
            boardCards.add(deck.draw());
        }
        pot = goAroundTable(queue, pot); //updated pot, queue is passed by reference
        if(queue.size() == 1)
        {
            queue.get(0).player.addMoney(pot);
        }
        //Turn
        boardCards.add(deck.draw());
        pot = goAroundTable(queue, pot); //updated pot, queue is passed by reference
        if(queue.size() == 1)
        {
            queue.get(0).player.addMoney(pot);
        }
        //River
        boardCards.add(deck.draw());
        pot = goAroundTable(queue, pot); //updated pot, queue is passed by reference
        if(queue.size() == 1)
        {
            queue.get(0).player.addMoney(pot);
        }else
        {
            ArrayList<Combo> combos = new ArrayList<Combo>();
            for(int i=0;i<queue.size();i++)
            {
                ArrayList<Card> playerhand = new ArrayList<Card>();
                playerhand.add(queue.get(i).player.firstCard());
                playerhand.add(queue.get(i).player.secCard());
                combos.add(new Combo(playerhand, boardCards));
            }
            //You can use this for displaying in the UI
            Combo bestCombo = combos.get(0);
            int itr = 0;
            for(int i=0;i<queue.size();i++)
            {
                if(combos.get(i).compareTo(bestCombo)==1)
                {
                    itr = i;
                    bestCombo = combos.get(i);
                }
            }
            //WINNER! //you can get hand cards straight from player
            queue.get(itr).player.addMoney(pot);
        }


        //Remove first element, add to end (Rotation of players)
        players.add(players.remove(0));
        for(Player player : players)
        {
            player.clearHand();
        }
        boardCards.clear();
        deck.resetDeck();
    }
}