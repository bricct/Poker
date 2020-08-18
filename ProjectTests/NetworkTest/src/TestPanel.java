import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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

public class TestPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	protected Card card1, card2;
	private Card[] table_cards;
	private int money, to_add, pot;
	private Timer timer;
	private int c_anim = 0;
	private int anim_select = 0;
	BufferedImage card1_reg, card2_reg, back_reg, deck_reg, volume_on, volume_off, musicOn, musicOff;
	Image icard1, icard2, iback, itable, ideck, ivolume, imusic;
	Image[] chip_spr, itable_cards;
	private int width, height, c_height, b_height, c_width, b_width, ch_size;
	private boolean[] card_set;
	private boolean folded, mchanging;

	private ArrayList<Player> players;
	//private int[] players;
	private boolean vol_toggle;
	private boolean vchanging;


	public TestPanel(Card card1, Card card2) {
		this.setSize(900, 480);
		this.card1 = null;
		this.card2 = null;
		this.money = 0;
		this.pot = 0;

		this.to_add = 0;

		this.players = new ArrayList<>();
		players.add(new Player(-1, TestMenu.name, 0));

		this.card_set = new boolean[5];
		for (int i = 0; i < 5; i++) {
			this.card_set[i] = false;
		}

		this.timer = new Timer(15, animator);
		this.timer.start();

		Dimension scr_dim = getSize();
		width = (int) scr_dim.getWidth() - 16;
		height = (int) scr_dim.getHeight() - 39;

		back_reg = Sprite.getBackSprite();

		deck_reg = Sprite.getDeckSprite();

		if (card1 == null || card2 == null) {
			card1_reg = back_reg;
			card2_reg = back_reg;
		} else {
			card1_reg = Sprite.getSprite(card1);
			card2_reg = Sprite.getSprite(card2);
		}

		itable = TestMenu.table;

		chip_spr = new Image[6];

		for (int i = 0; i < 6; i++) {
			chip_spr[i] = Sprite.getChipSprite(i);
		}

		volume_on = Sprite.getVolumeSprite(0);
		volume_off = Sprite.getVolumeSprite(1);

		this.table_cards = new Card[5];

		for (int i = 0; i < 5; i++) {
			this.table_cards[i] = null;
		}

		itable_cards = new Image[5];

		SoundEffects.init();
		SoundEffects.volume = SoundEffects.Volume.LOW;

		this.vol_toggle = true;
		this.vchanging = false;

		this.vchanging = false;

		musicOn = Sprite.getMusicOnSprite();
		musicOff = Sprite.getMusicOffSprite();

		this.addComponentListener(new ComponentAdapter() {
		    public void componentResized(ComponentEvent componentEvent) {

		    	updateSprites();
		    }
		});

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
			}
		});


	}


	public void updateSprites() {
		Dimension scr_dim = getSize();
		width = (int) scr_dim.getWidth();
		height = (int) scr_dim.getHeight();

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




	public void setCards(Card _card1, Card _card2) {

		if (_card1 == null || _card2 == null) {
			this.card1 = _card1;
			this.card2 = _card2;
		} else {
			this.card1 = new Card(_card1.value(), _card1.suit());
			this.card2 = new Card(_card2.value(), _card2.suit());
			this.folded = false;
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

	public void setMoney(int money) {
		this.money = money;
		this.repaint();
	}

	public void winHand(int pot) {
		this.money += pot;
		SoundEffects.CHIP.play();
		this.repaint();
	}


	public void addToPot(int to_add) {
		this.to_add = to_add;
		anim_select = 3;
		timer.restart();
		this.repaint();
	}

	public void flop(Card[] table_cards) {
		for (int i = 0; i < 5; i++) {
			this.card_set[i] = false;
		}
		setTableCards(table_cards);
		anim_select = 6;
		timer.restart();
		this.repaint();
	}

	public void turn(Card[] table_cards) {
		setTableCards(table_cards);
		anim_select = 9;
		timer.restart();
		this.repaint();
	}

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

	public void fold () {
		this.folded = true;
		this.repaint();
	}

	public void setPlayers(ArrayList<Player> players) {
		for (Player p: players) {
			this.players.add(p);
		}

		System.out.println("players was just set");
		for (Player p : players) {
			System.out.println(p.getName() + " " + p.getid());
		}
		this.repaint();
	}

	public void setPot(int pot) {
		this.pot = pot;
		SoundEffects.CHIP.play();
		this.repaint();
	}

	public void reset() {
		for (int i = 0; i < 5; i++) {
			this.card_set[i] = false;
		}
		try{
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		SoundEffects.SHUFFLE.play();

		//this.setCards(null, null);
		this.folded = true;
		System.out.println("resetting ui");
		this.table_cards = new Card[5];
		this.repaint();

	}

//	private void setTableCards(Card[] table_cards) {
//		this.table_cards = table_cards;
//		int i;
//		for (i = 0; i < 5; i ++) {
//			if (table_cards[i] == null) break;
//			itable_cards[i] = Sprite.getSprite(table_cards[i]).getScaledInstance(c_width, c_height, Image.SCALE_FAST);
//		}
//
//	}
//
//	/**
//	 * Sets the current players to the given listed players
//	 * @param players list of players to be set to
//	 */
//	public void setPlayers(ArrayList<Player> players) {
//		this.players = players;
//		this.repaint();
//	}

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
				pot += to_add;
				to_add = 0;
				SoundEffects.CHIP.play();
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

		g2d.drawImage(itable, 0, 0, this);

		if (this.card1 == null || this.card2 == null) {
			card1_reg = back_reg;
			card2_reg = back_reg;
		} else {
			card1_reg = Sprite.getSprite(this.card1);
			card2_reg = Sprite.getSprite(this.card2);
		}

		//player cards
		if (anim_select == 0) g2d.drawImage(icard1, (width/2) - c_width , height - (height/4) + ((20-c_anim) * (height/24)), this);
		else g2d.drawImage(icard1, (width/2) - c_width , height - (height/4), this);
		if (anim_select == 1) g2d.drawImage(icard2, (width/2) + 5, height - (height/4) + ((20-c_anim) * (height/24)), this);
		else if (anim_select > 1) g2d.drawImage(icard2, (width/2) + 5, height - (height/4), this);


		g2d.setColor(Color.white);
		g2d.setFont(new Font("UglyPoker", Font.TRUETYPE_FONT, height/100));



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

		g2d.drawString("Pot $ " + this.pot , width/2 - c_width, height/4 - 2 * ch_size);


		if (anim_select == 3) {
			int[] to_add = Chips.getChips(this.to_add);
			for (int i = 5; i >= 0; i--) {
				for (int j = 0; j < to_add[i]; j++) {
					g2d.drawImage(chip_spr[i], (int) Math.floor((width/2) + (((i % 3) - 1)  * (1.1 * ch_size)) - ch_size/2 + 2) , (int) Math.floor(height/4 - (j * (ch_size/10)) - ((i / 3) * (1.05 * ch_size)) + ((400-(c_anim*c_anim)) * (((2 * height/3) - height/4) - (ch_size/3 * 2))/400)), this);
				}
			}

		}


		g2d.setFont(new Font("UglyPoker", Font.TRUETYPE_FONT, height/100));



		//other players cards
		//p2
		if (players.size() > 1) {
			//if (still_in.contains(players.get(1))) {
			//if (players.get(1).firstCard() == null || players.get(1).secCard() == null) {
				g2d.drawImage(iback, (width/4) - b_width, height - (height/3), this);
				g2d.drawImage(iback, (width/4) + 5, height - (height/3), this);

				g2d.drawString(players.get(1).getName(),width/4 - b_width, height - height/3 + b_height + ch_size/2);
				g2d.drawString("Cash $" + players.get(1).getMoney() , width/4 - b_width, height - height/3 + c_height + ch_size/2);// + c_height + ch_size/2);


				//g2d.drawString()
			//}
			//}
		}
		//p3
		if (players.size() > 2) {
			g2d.drawImage(iback, 3 * (width/4) - b_width, height - (height/3), this);
			g2d.drawImage(iback, 3 * (width/4) + 5, height - (height/3), this);
			g2d.drawString(players.get(2).getName(),3 * (width/4) - b_width, height - height/3 + b_height + ch_size/2);
			g2d.drawString("Cash $" + players.get(2).getMoney() , 3 * (width/4) - b_width, height - height/3 + c_height + ch_size/2);// + c_height + ch_size/2);

		}

		//p4
		if (players.size() > 3) {
			g2d.drawImage(iback, (width/8) - b_width, height/2 - (b_height/2), this);
			g2d.drawImage(iback, (width/8) + 5, height/2 - (b_height/2), this);
			g2d.drawString(players.get(3).getName(),(width/8) - b_width, height/2 - 3*b_height/2 + ch_size/2);
			g2d.drawString("Cash $" + players.get(3).getMoney() , (width/8) - b_width, height/2 - 3*b_height/2 + (c_height - b_height) + ch_size/2);// + c_height + ch_size/2);

		}

		//p5
		if (players.size() > 4) {
			g2d.drawImage(iback, 7 * (width/8) - b_width, height/2 - (b_height/2), this);
			g2d.drawImage(iback, 7 * (width/8) + 5 , height/2 - (b_height/2), this);
			g2d.drawString(players.get(4).getName(), 7 * (width/8) - b_width, height/2 - 3*b_height/2 + ch_size/2);
			g2d.drawString("Cash $" + players.get(4).getMoney() ,  7 * (width/8) - b_width, height/2 - 3*b_height/2 + (c_height - b_height) + ch_size/2);// + c_height + ch_size/2);

		}




		g2d.drawImage(imusic, 2, 5, this);
		g2d.drawImage(ivolume, width - (2*ch_size) - 2, 5, this);





		Toolkit.getDefaultToolkit().sync();
	}





}
