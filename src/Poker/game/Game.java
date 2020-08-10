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
        ArrayList<Player> queue = new ArrayList<Player>();
        boolean smallPayed = false;
        boolean bigPayed = false;
        for(int i=0;i<player.size();i++)
        {
            if(players.get(j).getMoney() > 0)
            {
                queue.add(players.get(j));
                if(!smallPayed)
                {
                    players.get(j).subMoney(small);
                    smallPayed = true;
                }else if(!bigPayed)
                {
                    players.get(j).subMoney(big);
                    bigPayed = true;
                }
            }
        }
        for(int i=0;i<2;i++)
        {
            for(int j=0;j<queue.size();j++)
            {
                queue.get(j).addToHand(deck.draw());
            }
        }
        for(int i=0;i<3;i++)
        {
            boardCards.add(deck.draw());
        }

        boolean check = false;
        int checkedPlayers = 0
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




        deck.reset();
    }
}