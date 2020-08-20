import java.util.ArrayList;
import java.util.Iterator;

public class Game
{
    private ArrayList<Player> players;
    private Deck deck;
    private ArrayList<Card> boardCards;
    private int small;
    private int big;
    private Server server;
    private int currentCheckPay = 0;
    public Game(int ismall, int ibig, ArrayList<Player> iplayers, Server iserver)
    {
        
    	players = iplayers;
        
        small = ismall;
        big = ibig;
        server = iserver;
        
        
        deck = new Deck();
        deck.shuffle();
        
        
        boardCards = new ArrayList<>();
        
//        for(int i=0; i<inumPlayers; i++)
//        {
//            players.add(new Player(initialIncome));
//        }
        
        
    }

    public int goAroundTable(ArrayList<PlayerTuple> queue, int pot)
    {
        boolean check = false;
        boolean initialChecked = false;
        boolean skip_big = false;
        while(!check)
        {	
        	int notallin = 0;
        	int i = 0;
        	if (currentCheckPay == big && skip_big == false) {
        		i = 1;
        		skip_big = true;
        	}
        	
        	for (i = 0 ;i<queue.size();i++) {
        		if (!queue.get(i).player.isAllin()) {
        			notallin += 1;
        		}
        	}
        	
        	if (notallin < 1) {
        		try {
					Thread.sleep(2500);
					System.out.println("sleeping.........\n\n\n\n\n");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
        		return pot; 
        	}
        	
            for(i = 0 ;i<queue.size();i++)
            {
            	Player p = queue.get(i).player;
            	String[] cmd;
            	if (p.isAllin()) {
            		try {
						Thread.sleep(1500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
            		queue.get(i).currMoney = currentCheckPay;
            		continue;
            	} else if (notallin == 1) {
            		if (queue.get(i).currMoney == currentCheckPay) continue;
            	}
            	
            	server.sendTurn(p.getid());
            	try {
            		
					cmd = server.nextCmd(p.getid());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					continue;
				}
            	
            	if (cmd == null) {
            		System.out.println("uhm no cmd from player");
            		continue;
            	}
            	
            	
                //initialChecked is for checking if it's an initialBet. After initial bet, we break at the last better
                if(initialChecked && queue.get(i).currMoney == currentCheckPay)
                {
                    return pot;
                }
                else {//Do the action buttons: bet, check, fold
                    if(cmd[0].equals("check"))//If player checks...
                    {	
                    	if (currentCheckPay - queue.get(i).currMoney >= p.getMoney()) {
                    		server.sendAllin(p.getid(), queue.get(i).player.getMoney());
                    		pot += queue.get(i).player.getMoney();
                    		queue.get(i).player.subMoney(queue.get(i).player.getMoney());
                    		queue.get(i).player.allin();
                        } else {
                        	queue.get(i).player.subMoney(currentCheckPay - queue.get(i).currMoney);
                        	System.out.println("removing " + (currentCheckPay - queue.get(i).currMoney) + " from player " + i + " money");
                        	pot += currentCheckPay - queue.get(i).currMoney;
                        }
                        
                        queue.get(i).currMoney = currentCheckPay; //player set to current pot amount
                        
                        server.sendMoney(p.getid(), p.getMoney());
                        server.sendPot(pot);
                        
                    }else if(cmd[0].equals("raise") ) //IF bet
                    {
                    	try {
	                        int bet = Integer.parseInt(cmd[1]); //<--------- CHANGE THIS
	                        
	                        if (currentCheckPay - queue.get(i).currMoney + bet >= p.getMoney()) {
	                        	bet = p.getMoney() - currentCheckPay + queue.get(i).currMoney;
	                        	server.sendAllin(p.getid(), bet);
	                        	queue.get(i).player.allin();
	                        } else {
	                        	server.sendRaise(p.getid(), bet);
	                        }
	                        
	                        currentCheckPay += bet;
	                        p.subMoney(currentCheckPay - queue.get(i).currMoney);
	                        pot += currentCheckPay - queue.get(i).currMoney;
	                        queue.get(i).currMoney = currentCheckPay;
	                        
	                        server.sendMoney(p.getid(), p.getMoney());
	                        server.sendPot(pot);
	                        
                    	} catch (NumberFormatException | IndexOutOfBoundsException e) {
                    		server.forceFold(p.getid());
                    		queue.remove(i);
                    		i--;
                            if(queue.size() == 1)
                            {
                            	//currentCheckPay = 0;
                                return pot;
                            }
                    	} 
                    } else if (cmd[0].equals("fold"))//Fold
                    {   
                        server.forceFold(p.getid());
                        queue.remove(i);
                        i--;
                        if(queue.size() == 1)
                        {
                        	//currentCheckPay = 0;
                            return pot;
                        }
                    } else {
                    	System.out.println("force-folding player " + p.getid() + " for giving a garbage command " + cmd[0]);
                    	server.forceFold(p.getid());
                    	queue.remove(i);
                        if(queue.size() == 1)
                        {
                        	//currentCheckPay = 0;
                            return pot;
                        }
                    }
                }
            }
            initialChecked = true;
            check = true;
            for(int j=0;j<queue.size();j++)
            {
                
                if(queue.get(j).currMoney != currentCheckPay)
                {
                    //System.out.print(queue.get(j).getCurrMoney() + " " + currentCheckPay+"\n");
                    //System.out.print(queue.get(j).getCurrMoney()+" didn't work..!!... " + currentCheckPay+"\n");
                    check = false;
                }
            }
        }
        // currentCheckPay = 0;
        
        //System.out.print("it's working\n");
        return pot;
    }

    public void playRound()
    {
        int pot = 0;
        ArrayList<PlayerTuple> queue = new ArrayList<PlayerTuple>();
        //Add playable players to queue
        for(int i=0;i<players.size();i++)
        {
            if(players.get(i).getMoney() > 0)
            {
            	players.get(i).notAllin();
                queue.add(new PlayerTuple(players.get(i),0));
            }
        }
        if(queue.size() == 1)
        {
            return; //1 player left
        }
        
        
        Player smallBl = queue.get(0).player;
        smallBl.subMoney(small); //Big Blind
        pot = small;
        queue.get(0).currMoney = small;
        server.sendBlind(smallBl.getid(), small);
        server.sendMoney(smallBl.getid(), smallBl.getMoney());
        server.sendPot(pot);
        
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        Player bigBl = queue.get(1).player;
        bigBl.subMoney(big); //Big Blind
        pot += big;
        queue.get(1).currMoney = big;
        server.sendBlind(bigBl.getid(), big);
        server.sendMoney(bigBl.getid(), bigBl.getMoney());
        server.sendPot(pot);
        
        currentCheckPay = big;

        //Deal cards to players in queue
        for(int i=0;i<2;i++)
        {
            for(int j=0;j<queue.size();j++)
            {
                queue.get(j).player.addToHand(deck.draw());
            }
        }
        
        server.sendCards(queue);
        
        
        
        //pre-flop
        pot = goAroundTable(queue, pot); //updated pot, queue is passed by reference
        if(queue.size() == 1)
        {
        	Player winner = queue.get(0).player;
            winner.addMoney(pot);
            server.sendWinnings(winner.getid(), pot);
            server.sendPot(0);
            return;
        }
        
        //System.out.print("Flopped\n");
        //flop
        for(int i=0;i<3;i++)
        {
            boardCards.add(deck.draw());
        }
        server.sendFlop(boardCards.get(0), boardCards.get(1), boardCards.get(2));
        
        pot = goAroundTable(queue, pot); //updated pot, queue is passed by reference
        if(queue.size() == 1)
        {
        	Player winner = queue.get(0).player;
            winner.addMoney(pot);
            server.sendWinnings(winner.getid(), pot);
            server.sendPot(0);
            return;
        }
        
        
        //Turn
        boardCards.add(deck.draw());
        server.sendTurn(boardCards.get(3));
        
        pot = goAroundTable(queue, pot); //updated pot, queue is passed by reference
        if(queue.size() == 1)
        {
        	Player winner = queue.get(0).player;
            winner.addMoney(pot);
            server.sendWinnings(winner.getid(), pot);
            server.sendPot(0);
            return;
        }
        
        
        //River
        boardCards.add(deck.draw());
        server.sendRiver(boardCards.get(4));
        
        pot = goAroundTable(queue, pot); //updated pot, queue is passed by reference
        if(queue.size() == 1)
        {
            Player winner = queue.get(0).player;
            winner.addMoney(pot);
            server.sendWinnings(winner.getid(), pot);
            server.sendPot(0);
            return;
            
        }else
        {
        	
        	
        	for (int i = 0; i < queue.size(); i++) {
        		Player p = queue.get(i).player;
        		server.exposeCards(p.getid(), p.firstCard(), p.secCard());
        	}
        	
        	try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        	
        	
            ArrayList<Combo> combos = new ArrayList<Combo>();
            for(int i=0;i<queue.size();i++)
            {
                ArrayList<Card> playerhand = new ArrayList<Card>();
                playerhand.add(queue.get(i).player.firstCard());
                //System.out.println("Adding card " + queue.get(i).player.firstCard().value() + " " + queue.get(i).player.firstCard().suit() + " to player combo id = " + i);
                playerhand.add(queue.get(i).player.secCard());
                //System.out.println("Adding card " + queue.get(i).player.secCard().value() + " " + queue.get(i).player.secCard().suit() + " to player combo id = " + i);

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
            Player winner = queue.get(itr).player;
            winner.addMoney(pot);
            server.sendWinnings(winner.getid(), pot);
            server.sendPot(0);
            
            Iterator<Player> player_itr = players.iterator(); 
            
            while (player_itr.hasNext()) {
            	Player p = player_itr.next();
            	if(p.getMoney() <= 0)
                {
                	player_itr.remove();
                	server.sendLose(p.getid());
                }
            }

            
            
            return;
            
        }


        
    }


    public void playGame() {
        while (players.size() > 1) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            playRound();
            //Remove first element, add to end (Rotation of players)
            players.add(players.remove(0));
            for(Player player : players)
            {
                player.clearHand();
            }
            currentCheckPay = 0;
            boardCards.clear();
            deck.resetDeck();
            deck.shuffle();
            server.sendStartHand();
            
        }
        server.sendWin(players.get(0).getid());
        server.sendGameEnd();
    }


}

