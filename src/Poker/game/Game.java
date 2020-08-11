package poker.game;

public class Game()
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
        turnIterator = 0;
        deck = new Deck();
        small = ismall;
        big = ibig;
        for(int i=0; i<inumPlayers, i++)
        {
            players.add(new Player(initialIncome));
        }
    }

    public void playRound()
    {
        int pot = 0;
        ArrayList<Player> queue = new ArrayList<Player>();
        boolean smallBet = false;
        boolean bigBet = false;
        //Add playable players to queue
        for(int i=0;i<players.size();i++)
        {
            if(players.get(j).getMoney() > 0)
            {
                queue.add(players.get(j));
            }
        }
        if(queue.size() == 1)
        {
            return; //1 player left
        }

        players.get(0).subMoney(small); //Big Blind    
        players.get(1).subMoney(big); //Small blind          

        //Deal cards to players in queue
        for(int i=0;i<2;i++)
        {
            for(int j=0;j<queue.size();j++)
            {
                queue.get(j).addToHand(deck.draw());
            }
        }

        boolean check = false;
        int checkedPlayers = 0;
        while(!check)
        {
            for(int i=0;i<queue.size();i++)
            {
                //Do the action buttons: bet, check, fold
                //If player checks...
                if(true)
                {
                    checkedPlayers++;
                }else if(true) //IF bet
                {

                }else //Fold
                {
                    queue.remove(queue.get(i));
                }
            }
            if(queue.size() == checkedPlayers)
            {
                check = true;
            }
        }

        for(int i=0;i<3;i++)
        {
            boardCards.add(deck.draw());
        }

        


        //Remove first element, add to end
        players.add(players.remove(0));
        deck.reset();
    }
}