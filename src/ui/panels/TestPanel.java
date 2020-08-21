import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * @author Trey Briccetti
 * @version v1.0
 */

/**
 * @author bricc
 *
 */
public class TestPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	protected Card card1, card2;
	private Card[] table_cards;
	private int money, to_add, pot;
	private Timer timer;
	private int c_anim = 0;
	private int anim_select = 0;
	BufferedImage card1_reg, card2_reg, back_reg, deck_reg, volume_on, volume_off, musicOn, musicOff, youWin, youLose;
	Image icard1, icard2, iback, itable, ideck, ivolume, imusic;
	Image[] chip_spr, itable_cards, iplayer_cards;
	private int width, height, c_height, b_height, c_width, b_width, ch_size;
	private boolean[] card_set;
	private boolean mchanging, win, lose, vol_toggle, vchanging, gameOver;
	private ArrayList<Player> players;
	private ArrayList<String> player_moves;
	private String turn_win;
	private Font font;


	/** Constructs a Panel for Displaying the Game
	 * @param master The frame which holds this panel
	 * @param card1 The first of the player's card
	 * @param card2 The second of the player's cards
	 * @param id Client id used in networking
	 */
	public TestPanel(TestBoard master, Card card1, Card card2, int id) {
		
		//set some initial values for the display
		this.setSize(900, 480);
		this.card1 = null;
		this.card2 = null;
		this.money = 0;
		this.pot = 0;
		this.to_add = 0;
		
		//create list of players and add self
		this.players = new ArrayList<>();
		players.add(new Player(id, TestMenu.name, 0));
		
		//initialize some display strings;
		this.player_moves = new ArrayList<>();
		player_moves.add("");
		this.turn_win = "";
		
		//initialize array that holds the flop turn and river boolean values (dealt or not dealt)
		this.card_set = new boolean[5];
		for (int i = 0; i < 5; i++) {
			this.card_set[i] = false;
		}

		//initialize animation variables
		this.timer = new Timer(15, animator);
		this.timer.start();

		
		//Initialize all necessary sprites and the custom font
		Dimension scr_dim = getSize();
		width = (int) scr_dim.getWidth() - 16;
		height = (int) scr_dim.getHeight() - 39;

		font = new Font("UglyPoker", Font.TRUETYPE_FONT, (height/150 + width/450));
		
		back_reg = Sprite.getBackSprite();
		deck_reg = Sprite.getDeckSprite();
		volume_on = Sprite.getVolumeSprite(0);
		volume_off = Sprite.getVolumeSprite(1);
		musicOn = Sprite.getMusicOnSprite();
		musicOff = Sprite.getMusicOffSprite();
		youWin = Sprite.getWin();
		youLose = Sprite.getLose();

		if (card1 == null || card2 == null) {
			card1_reg = back_reg;
			card2_reg = back_reg;
		} else {
			card1_reg = Sprite.getSprite(card1);
			card2_reg = Sprite.getSprite(card2);
		}

		itable = TestMenu.table;
		
		chip_spr = new Image[6];
		itable_cards = new Image[5];
		iplayer_cards = new Image[8];

		
		for (int i = 0; i < 6; i++) {
			chip_spr[i] = Sprite.getChipSprite(i);
		}
		
		
		
		//initialize table card array for holding cards for flop turn and river
		this.table_cards = new Card[5];

		for (int i = 0; i < 5; i++) {
			this.table_cards[i] = null;
		}

		
		
		//initialize sound controller audio clips and volume
		SoundEffects.init();
		SoundEffects.volume = SoundEffects.Volume.LOW;
		
		
		//initialize some volume and game state booleans
		this.vol_toggle = true;
		this.vchanging = false;
		
		this.win = false;
		this.lose = false;
		this.gameOver = false;

		
		
		//this resizes all images on the screen every time that the screen is resized
		this.addComponentListener(new ComponentAdapter() {
		    public void componentResized(ComponentEvent componentEvent) {

		    	updateSprites();
		    }
		});
		
		
		//mouse listener to pick up mouse clicks on certain buttons
		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				int x=e.getX();
				int y=e.getY();
				if ( y < ch_size*2 + 5 && y > 5) {
					if (x >= (width - (ch_size*2)) && x < width - 2) vchanging = true;
					else if (x > 2 && x < ch_size*2 + 2) {
						mchanging = true;
						System.out.println("mchanging on click");
					}
					else{
						mchanging = false;
						vchanging = false;
					}
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (vchanging || mchanging) {
					int x=e.getX();
					int y=e.getY();

					System.out.println(x + " " + y);

					if (y < ch_size*2 + 5 && y > 5) {
						System.out.println("mchanging " + mchanging);
						if (vchanging) {
							if (x >= (width - (ch_size*2)) - 2 && x < width - 2) {
								if (vol_toggle) {
									ivolume = volume_off.getScaledInstance(ch_size*2, ch_size*2, Image.SCALE_FAST);
									SoundEffects.volume = SoundEffects.Volume.MUTE;
								} else {
									ivolume = volume_on.getScaledInstance(ch_size*2, ch_size*2, Image.SCALE_FAST);
									SoundEffects.volume = SoundEffects.Volume.LOW;
								}
								vol_toggle = !vol_toggle;
								repaint();
							}
						} else if (mchanging) {

							System.out.println("mchanging on release");
							if (x > 2 && x < ch_size*2 + 2) {
								if (TestMenu.mus_toggle) {
									imusic = musicOff.getScaledInstance(ch_size*2, ch_size*2, Image.SCALE_FAST);
									if (TestMenu.mode)
										MusicController.THEME.stop();
									else {
										MusicController.SECRET.stop();
									}
								} else {
									imusic = musicOn.getScaledInstance(ch_size*2, ch_size*2, Image.SCALE_FAST);
									if (TestMenu.mode)
										MusicController.THEME.play();
									else {
										MusicController.SECRET.play();
									}
								}
								TestMenu.mus_toggle = !TestMenu.mus_toggle;
								repaint();

							}
						}

					}

					vchanging = false;
					mchanging = false;


				}
				if (gameOver) {
					if (e.getX() < 5 * width/8 && e.getX() > 3 * width/8 && e.getY() > 3 * height/4) {
						master.exit();
					}
				}
			}
		});


	}

	
	//updates all sprites according to size dimensions of screen
	private void updateSprites() {
		Dimension scr_dim = getSize();
		width = (int) scr_dim.getWidth();
		height = (int) scr_dim.getHeight();
		
		font = new Font("UglyPoker", Font.TRUETYPE_FONT, (height/150 + width/450));

		itable = TestMenu.table.getScaledInstance(width, height, Image.SCALE_FAST);

		c_width = (int) Math.floor((double)width/13.0 );
		c_height = (int) Math.floor((double)height/5.0 );
		
		

		b_width = (int) Math.floor(c_width * 0.7);
		b_height = (int) Math.floor(c_height * 0.7);

		ch_size = (int) Math.floor((double)width/30.0 );

		if (card1 == null || card2 == null) {
			card1_reg = back_reg;
			card2_reg = back_reg;
		} else {
			card1_reg = Sprite.getSprite(card1);
			card2_reg = Sprite.getSprite(card2);
		}

		for (int i = 0; i < 5; i ++) {
			if (table_cards[i] == null) break;
			itable_cards[i] = Sprite.getSprite(table_cards[i]).getScaledInstance(c_width, c_height, Image.SCALE_FAST);
		}
		
		for (int i = 0; i < 8; i ++) {
			if (i/2 + 1 > players.size() - 1) break;
			Player p = players.get(i/2 + 1);
			if (p.firstCard() != null) iplayer_cards[i++] = Sprite.getSprite(p.firstCard()).getScaledInstance(b_width, b_height, Image.SCALE_FAST);
			if (p.secCard() != null) iplayer_cards[i] = Sprite.getSprite(p.secCard()).getScaledInstance(b_width, b_height, Image.SCALE_FAST);
		}
			
		
		icard1 = card1_reg.getScaledInstance(c_width, c_height, Image.SCALE_FAST);
		icard2 = card2_reg.getScaledInstance(c_width, c_height, Image.SCALE_FAST);
		iback = back_reg.getScaledInstance(b_width, b_height, Image.SCALE_FAST);
		ideck = deck_reg.getScaledInstance(c_width, c_height, Image.SCALE_FAST);

		if (vol_toggle)
			ivolume = volume_on.getScaledInstance(ch_size*2, ch_size*2, Image.SCALE_FAST);
		else
			ivolume = volume_off.getScaledInstance(ch_size*2, ch_size*2, Image.SCALE_FAST);

		if (TestMenu.mus_toggle)
			imusic = musicOn.getScaledInstance(ch_size*2, ch_size*2, Image.SCALE_FAST);
		else
			imusic = musicOff.getScaledInstance(ch_size*2, ch_size*2, Image.SCALE_FAST);

		chip_spr = new Image[6];

		for (int i = 0; i < 6; i++) {
			chip_spr[i] = Sprite.getChipSprite(i).getScaledInstance(ch_size, ch_size, Image.SCALE_FAST);
		}
		this.repaint();
	}




	/** Sets the players cards to those passed
	 * @param _card1 The first of the players cards
	 * @param _card2 The second of the players cards
	 */
	public void setCards(Card _card1, Card _card2) {

		if (_card1 == null || _card2 == null) {
			this.card1 = _card1;
			this.card2 = _card2;
		} else {
			this.card1 = new Card(_card1.value(), _card1.suit());
			this.card2 = new Card(_card2.value(), _card2.suit());
			//this.folded = false;
		}


		if (card1 == null || card2 == null) {
			card1_reg = back_reg;
			card2_reg = back_reg;
		} else {
			card1_reg = Sprite.getSprite(card1);
			card2_reg = Sprite.getSprite(card2);
		}

		icard1 = card1_reg.getScaledInstance(c_width, c_height, Image.SCALE_FAST);
		icard2 = card2_reg.getScaledInstance(c_width, c_height, Image.SCALE_FAST);

		timer.restart();
		this.c_anim = 0;
		this.anim_select = 0;
		this.repaint();

	}

	/** Sets the money of a player in the game
	 * @param id Networking unique identifier of the player in question
	 * @param money The amount of money that the player should be assigned
	 */
	public void setMoney(int id, int money) {
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getid() == id) {
				if (i == 0) this.money = money;
				players.get(i).setMoney(money);
			}
		}
		this.repaint();
	}

	/** Displays the winner of the hand and the amount that they won
	 * @param id Networking unique identifier of the player in question
	 * @param pot The amount of money won by the player during this hand
	 */
	public void winHand(int id, int pot) {
		resetTurnWin();
		if (id == players.get(0).getid()) {
			this.money += pot;
			turn_win = "You won $" + pot;
		} else {
			for (Player p: players) {
				if (p.getid() == id) {
					turn_win = p.getName() + " won $" + pot;
				}
			}
		}
		
		
		SoundEffects.CHIP.play();
		this.repaint();
	}


	private void resetTurnWin() {
		turn_win = "";
		repaint();
		
	}
	
	/** Displays whose turn it is
	 * @param id Networking unique identifier of the player in question
	 */
	public void turn(int id) {
		
		resetTurnWin();
		if (id == players.get(0).getid()) {
			turn_win = "Your turn";
		} else {
			for (Player p: players) {
				if (p.getid() == id) {
					turn_win = p.getName() + "s turn";
				}
			}
		}
		
		this.repaint();
	}
	

	/** Adds an amount of money to the pot
	 * @param to_add the amount of money to add
	 */
	public void addToPot(int to_add) {
		this.to_add = to_add;
		anim_select = 3;
		timer.restart();
		this.repaint();
	}

	/** Updates and animates the table cards to have the first three dealt by the server
	 * @param table_cards The table card array
	 */
	public void flop(Card[] table_cards) {
		for (int i = 0; i < 5; i++) {
			this.card_set[i] = false;
		}
		setTableCards(table_cards);
		anim_select = 6;
		timer.restart();
		this.repaint();
	}

	/** Updates and animates the table cards to have the fourth card dealt by the server
	 * @param table_cards table_cards The table card array
	 */
	public void turn(Card[] table_cards) {
		setTableCards(table_cards);
		anim_select = 9;
		timer.restart();
		this.repaint();
	}

	/** Updates and animates the table cards to have the fifth card dealt by the server
	 * @param table_cards table_cards The table card array
	 */
	public void river(Card[] table_cards) {
		setTableCards(table_cards);
		anim_select = 10;
		timer.restart();
		this.repaint();
	}


	private void setTableCards(Card[] table_cards) {
		this.table_cards = table_cards;
		int i;
		for (i = 0; i < 5; i ++) {
			if (table_cards[i] == null) break;
			itable_cards[i] = Sprite.getSprite(table_cards[i]).getScaledInstance(c_width, c_height, Image.SCALE_FAST);
		}

	}


	/**	Sets the other players in the game display
	 * @param players An array list containing the various players in the game
	 */
	public void setPlayers(ArrayList<Player> players) {
		for (Player p: players) {
			this.players.add(p);
			this.player_moves.add("");
		}
		this.repaint();
	}

	
	/** Update the pot display in the middle of the top of the panel
	 * @param pot The amount of money in the pot
	 */
	public void setPot(int pot) {
		this.pot = pot;
		SoundEffects.CHIP.play();
		this.repaint();
	}

	
	/** Resets the ui to the beginning of a new hand
	 * 
	 */
	public void reset() {
		resetPlayerMoves();
		for (int i = 1; i < players.size(); i++) {
			players.get(i).clearHand();
		}
		for (int i = 0; i < 5; i++) {
			this.card_set[i] = false;
		}
		try{
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		SoundEffects.SHUFFLE.play();
		//System.out.println("resetting ui");
		this.table_cards = new Card[5];
		this.repaint();

	}
	
	//erase other players previous move info when a new turn happens
	private void resetPlayerMoves() {
		for (int i = 0; i < player_moves.size(); i++) {
			player_moves.set(i,"");
		}
		
	}
	
	
	/** Draw message saying if another play is all-in
	 * @param id Networking unique identifier of the player in question
	 * @param bet the amount of money the player has bet to go all-in
	 */
	public void sendAllin(int id, int bet) {
		resetPlayerMoves();
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getid() == id) {
				player_moves.set(i,"All In " + bet);
			}
		}
		
	}


	

	/**Display winning screen briefly and then allow to disconnect
	 * 
	 */
	public void win() {
		win = true;
		this.gameOver = true;
		repaint();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		win = false;
	}

	
	/** Display if another player raised
	 * @param id Networking unique identifier of the player in question
	 * @param bet the maount of money the player has just bet
	 */
	public void raise(int id, int bet) {
		resetPlayerMoves();
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getid() == id) {
				player_moves.set(i,"Raise " + bet);
			}
		}
		
	}

	/** Display losing screen briefly and then allow to disconnect
	 * 
	 */
	public void lose() {
		lose = true;
		this.gameOver = true;
		repaint();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		lose = false;
		
		
	}

	/**Display if another player folded
	 * @param id Networking unique identifier of the player in question
	 */
	public void fold(int id) {
		resetPlayerMoves();
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getid() == id) {
				player_moves.set(i,"Fold");
			}
		}
		
	}
	
	/**reveal another players cards (at end of hand if it comes down to it)
	 * @param id Networking unique identifier of the player in question
	 * @param card1 The first card of the player in questions hand
	 * @param card2 The second card of the player in questions hand
	 */
	public void setPlayerCards(int id, Card card1, Card card2) {
		for (Player p: players) {
			if (p.getid() == id) {
				p.addToHand(card1);
				p.addToHand(card2);
			}
		}
		updateSprites();
		repaint();
		
	}
	

	/** Doesn't do anything just yet
	 * @param id Networking unique identifier of the player in question
	 */
	public void dc(int id) {
	}

	
	
	/** Displays if another person has checked
	 * @param id Networking unique identifier of the player in question
	 */
	public void check(int id) {
		resetPlayerMoves();
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getid() == id) {
				player_moves.set(i,"Check");
			}
		}
		
	}


	/** Displays a button allowing the player to easily disconnect and go back to the menu
	 * 
	 */
	public void gameEnd() {
		this.gameOver = true;
		System.out.println("Game over true");
		repaint();
	}


	
	//single action listener responsible for controlling the various animations of the display
	private ActionListener animator = new ActionListener() {

		public void actionPerformed(ActionEvent e) {
			c_anim++;
			if (c_anim == 20) {
				c_anim = 0;


				anim_select++;
				if (anim_select == 1 || anim_select == 2) SoundEffects.DRAW.play();

				if (anim_select > 6 && anim_select < 12) {
					card_set[anim_select-7] = true;
					SoundEffects.DRAW.play();
				}


				if (anim_select == 9 || anim_select == 10)  {

					anim_select = 11;
				}

			}
			if (anim_select == 2) {
				timer.stop();
			}
			if (anim_select == 4) {
				to_add = 0;
				timer.stop();
			}
			if (anim_select == 11) {
				timer.stop();
			}

			repaint();
			revalidate();
		}


	};
	




	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		//draw background image
		g2d.drawImage(itable, 0, 0, this);

		
		//if cards are null draw backs of cards otherwise draw the cards
		if (this.card1 == null || this.card2 == null) {
			card1_reg = back_reg;
			card2_reg = back_reg;
		} else {
			card1_reg = Sprite.getSprite(this.card1);
			card2_reg = Sprite.getSprite(this.card2);
		}

		
		
		//Animate player cards
		if (anim_select == 0) g2d.drawImage(icard1, (width/2) - c_width , height - (height/4) + ((20-c_anim) * (height/24)), this);
		else g2d.drawImage(icard1, (width/2) - c_width , height - (height/4), this);
		if (anim_select == 1) g2d.drawImage(icard2, (width/2) + 5, height - (height/4) + ((20-c_anim) * (height/24)), this);
		else if (anim_select > 1) g2d.drawImage(icard2, (width/2) + 5, height - (height/4), this);

		
		//set font & color for drawing some info strings
		g2d.setColor(Color.white);
		g2d.setFont(this.font);
		
		FontMetrics metrics = g2d.getFontMetrics(font);


		//g2d.drawString("You",(int) Math.floor( width/2 + 1.2 * c_width), 6* height/8);
		g2d.drawString("Cash $" + this.money , width/2 - c_width, height - (height/4) - (2 * ch_size/3));

		

		//draw table cards
		for (int i = 0; i < 5; i++) {
			if (this.table_cards[i] != null) {
				if (anim_select == i+6) g2d.drawImage(itable_cards[i], (int) Math.floor((width/2) + (2 * ((((double)c_anim-20)/(20.0/((double)i+1.0)) -2)+i) * (c_width/2 + 5))), height/3, this);
				else if (card_set[i]) g2d.drawImage(itable_cards[i], (width/2) + (2 * (i-2) * (c_width/2 + 5) ) , height/3, this);
			}
		}


		//draw deck 5 times to make it look 3d
		for (int i = 0; i < 5; i++) {
			g2d.drawImage(ideck, (int) Math.floor((width/2) + ( (-6-((double)i*5.0/100.0)) * (c_width/2 + 5))) ,(int) Math.floor((1.0-((double)i/100.0)) * height/3), this);
		}


		//chip stacking algorithm
		int[] chips = Chips.getChips(this.money);
		for (int i = 5; i >= 0; i--) {
			for (int j = 0; j < chips[i]; j++) {
				g2d.drawImage(chip_spr[i], (int) Math.floor((width/2) + (((i % 3) - 1)  * (1.1 * ch_size)) - ch_size/2 + 2) , (int) Math.floor(height - (height/3) - (j * (ch_size/10)) - ((i / 3) * (1.05 * ch_size)) - (ch_size/3 * 2)), this);
			}
		}




		//draw pot chips
		int[] pot = Chips.getChips(this.pot);
		for (int i = 5; i >= 0; i--) {
			for (int j = 0; j < pot[i]; j++) {
				g2d.drawImage(chip_spr[i], (int) Math.floor((width/2) + (((i % 3) - 1)  * (1.1 * ch_size)) - ch_size/2 + 2) , (int) Math.floor(height/4 - (j * (ch_size/10)) - ((i / 3) * (1.05 * ch_size))), this);
			}
		}

		//Draw top bar info strings (name, turn, pot)
		g2d.drawString("Pot $ " + this.pot , width/2 - c_width, height/4 - 2 * ch_size);
		g2d.drawString("Your name is " + players.get(0).getName(), width/2 + width/4 - metrics.stringWidth("Your name is " + players.get(0).getName())/2, height/4 - 2 * ch_size + metrics.getHeight());
		g2d.drawString(turn_win , width/7, height/4 - 2 * ch_size + metrics.getHeight());

		
		//animate chips sliding across table on a raise
		if (anim_select == 3) {
			int[] to_add = Chips.getChips(this.to_add);
			for (int i = 5; i >= 0; i--) {
				for (int j = 0; j < to_add[i]; j++) {
					g2d.drawImage(chip_spr[i], (int) Math.floor((width/2) + (((i % 3) - 1)  * (1.1 * ch_size)) - ch_size/2 + 2) , (int) Math.floor(height/4 - (j * (ch_size/10)) - ((i / 3) * (1.05 * ch_size)) + ((400-(c_anim*c_anim)) * (((2 * height/3) - height/4) - (ch_size/3 * 2))/400)), this);
				}
			}

		}


		


		//draw other player's cards, names, and cash
		//p2
		if (players.size() > 1) {
			if (players.get(1).firstCard() == null || players.get(1).secCard() == null) {
				g2d.drawImage(iback, (width/4) - b_width, height - (height/3), this);
				g2d.drawImage(iback, (width/4) + 5, height - (height/3), this);
			} else {
				g2d.drawImage(iplayer_cards[0], (width/4) - b_width, height - (height/3), this);
				g2d.drawImage(iplayer_cards[1], (width/4) + 5, height - (height/3), this);
			}
				g2d.drawString(players.get(1).getName(),width/4 - b_width, height - height/3 + b_height + ch_size/2);
				g2d.drawString("Cash $" + players.get(1).getMoney() , width/4 - b_width, height - height/3 + c_height + ch_size/2);// + c_height + ch_size/2);

				g2d.drawString(player_moves.get(1), width/4 - b_width, height - height/3 - metrics.getHeight());
		}
		//p3
		if (players.size() > 2) {
			if (players.get(1).firstCard() == null || players.get(1).secCard() == null) {
				g2d.drawImage(iback, 3 * (width/4) - b_width, height - (height/3), this);
				g2d.drawImage(iback, 3 * (width/4) + 5, height - (height/3), this);
			} else {
				g2d.drawImage(iplayer_cards[2], 3 * (width/4) - b_width, height - (height/3), this);
				g2d.drawImage(iplayer_cards[3], 3 * (width/4) + 5, height - (height/3), this);
			}
			g2d.drawString(players.get(2).getName(),3 * (width/4) - b_width, height - height/3 + b_height + ch_size/2);
			g2d.drawString("Cash $" + players.get(2).getMoney() , 3 * (width/4) - b_width, height - height/3 + c_height + ch_size/2);// + c_height + ch_size/2);
			g2d.drawString(player_moves.get(2), 3 * (width/4) - b_width, height - height/3 - metrics.getHeight());

		}

		//p4
		if (players.size() > 3) {
			if (players.get(1).firstCard() == null || players.get(1).secCard() == null) {
		
				g2d.drawImage(iback, (width/8) - b_width, height/2 - (b_height/2), this);
				g2d.drawImage(iback, (width/8) + 5, height/2 - (b_height/2), this);
			} else {
				g2d.drawImage(iplayer_cards[4], (width/8) - b_width, height/2 - (b_height/2), this);
				g2d.drawImage(iplayer_cards[5], (width/8) + 5, height/2 - (b_height/2), this);
			}
			g2d.drawString(players.get(3).getName(),(width/8) - b_width, height/2 - 3*b_height/2 + ch_size/2);
			g2d.drawString("Cash $" + players.get(3).getMoney() , (width/8) - b_width, height/2 - 3*b_height/2 + (c_height - b_height) + ch_size/2);// + c_height + ch_size/2);
			g2d.drawString(player_moves.get(3), (width/8) - b_width, height/2  + ch_size/2 + metrics.getHeight());

		}

		//p5
		if (players.size() > 4) {
			if (players.get(1).firstCard() == null || players.get(1).secCard() == null) {
		
				g2d.drawImage(iback, 7 * (width/8) - b_width, height/2 - (b_height/2), this);
				g2d.drawImage(iback, 7 * (width/8) + 5 , height/2 - (b_height/2), this);
			} else {
				g2d.drawImage(iplayer_cards[6], 7 * (width/8) - b_width, height/2 - (b_height/2), this);
				g2d.drawImage(iplayer_cards[7], 7 * (width/8) + 5 , height/2 - (b_height/2), this);
			}
			g2d.drawString(players.get(4).getName(), 7 * (width/8) - b_width, height/2 - 3*b_height/2 + ch_size/2);
			g2d.drawString("Cash $" + players.get(4).getMoney() ,  7 * (width/8) - b_width, height/2 - 3*b_height/2 + (c_height - b_height) + ch_size/2);// + c_height + ch_size/2);
			g2d.drawString(player_moves.get(4), 7 * (width/8) - b_width, height/2 + ch_size/2 +  metrics.getHeight());

		}



		//draw music and volume icons in top corners
		g2d.drawImage(imusic, 2, 5, this);
		g2d.drawImage(ivolume, width - (2*ch_size) - 2, 5, this);
		
		
		//display winning or losing screens/disconnect buttons when warranted
		if (this.win) {
			g2d.drawImage(youWin.getScaledInstance(width,  height, Image.SCALE_FAST), 0, 0, this);
			g2d.setColor(Color.yellow);
			g2d.setFont(new Font("UglyPoker", Font.TRUETYPE_FONT, width/100));
			FontMetrics winMetrics = g2d.getFontMetrics(font);
			
			g2d.drawString("You Win", width/2 - winMetrics.stringWidth("You Win"), height - height/4 - 5 * winMetrics.getHeight()/2);
			
			g2d.setColor(Color.white);
			g2d.setFont(this.font);
			
		} else if (this.lose) {
			g2d.drawImage(youLose.getScaledInstance(width,  height, Image.SCALE_FAST), 0, 0, this);
			g2d.setColor(Color.red);
			g2d.setFont(new Font("UglyPoker", Font.TRUETYPE_FONT, width/100));
			FontMetrics winMetrics = g2d.getFontMetrics(font);
			
			g2d.drawString("You Lose", width/2 - winMetrics.stringWidth("You Lose"), height - height/4 - 5 * winMetrics.getHeight()/2);
			
			g2d.setColor(Color.white);
			g2d.setFont(this.font);
			
		}
		
		if (gameOver) {
			g2d.drawImage(Sprite.getButtonSprite().getScaledInstance(width/4, height/4, Image.SCALE_FAST), 3 * width/8, 3 * height/4, this);
			g2d.setColor(Color.LIGHT_GRAY);
			g2d.setFont(new Font("UglyPoker", Font.TRUETYPE_FONT, width/150));
			FontMetrics exitMetrics = g2d.getFontMetrics(font);
			
			g2d.drawString("Leave Game", width/2 - exitMetrics.stringWidth("Leave Game")/2, height - height/8 - exitMetrics.getHeight()/2);
			
			g2d.setFont(this.font);
		}
		
		

		//idk what this does but someone on the internet said its important
		Toolkit.getDefaultToolkit().sync();
	}
	


}
