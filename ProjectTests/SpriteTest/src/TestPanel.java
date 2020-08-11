import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class TestPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private Card card1, card2;
	private Card[] table_cards;
	private BufferedImage table;
	private int money, to_add, pot;
	private Timer timer;
	private int c_anim = 0;
	private int anim_select = 0;
	BufferedImage card1_reg, card2_reg, back_reg, deck_reg;
	Image icard1, icard2, iback, table1, ideck;
	Image[] chip_spr, itable_cards;
	private int width, height, c_height, b_height, c_width, b_width, ch_size;
	
	
	
	public TestPanel(Card card1, Card card2, int money) {
		this.setSize(900, 480);
		this.card1 = card1;
		this.card2 = card2;
		this.money = money;
		this.pot = 1256;
		this.to_add = 0;
		this.timer = new Timer(10, animator);
		this.timer.start();
		
		Dimension scr_dim = getSize();
		width = (int) scr_dim.getWidth();
		height = (int) scr_dim.getHeight();
		
		back_reg = Sprite.getBackSprite();
		
		deck_reg = Sprite.getDeckSprite();
		
		if (card1 == null || card2 == null) {
			card1_reg = back_reg;
			card2_reg = back_reg;
		} else {
			card1_reg = Sprite.getSprite(card1);
			card2_reg = Sprite.getSprite(card2);
		}
		
		table1 = table;
		
		chip_spr = new Image[6];
		
		for (int i = 0; i < 6; i++) {
			chip_spr[i] = Sprite.getChipSprite(i);
		}
		
		
		try {
			this.table = ImageIO.read(new File("table.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.table_cards = new Card[5];
		
		for (int i = 0; i < 5; i++) {
			this.table_cards[i] = null;
		}
		
		itable_cards = new Image[5];
		
		
		
		this.addComponentListener(new ComponentAdapter() {
		    public void componentResized(ComponentEvent componentEvent) {
		    	
		    	Dimension scr_dim = getSize();
				width = (int) scr_dim.getWidth();
				height = (int) scr_dim.getHeight();
		    	
				table1 = table.getScaledInstance(width, height, Image.SCALE_FAST);
				
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
				
				chip_spr = new Image[6];
				
				for (int i = 0; i < 6; i++) {
					chip_spr[i] = Sprite.getChipSprite(i).getScaledInstance(ch_size, ch_size, Image.SCALE_FAST);
				}
				
		    }
		});
		
		
	}
	
	public void setCards(Card card1, Card card2) {
		this.card1 = card1;
		this.card2 = card2;
		
		card1_reg = Sprite.getSprite(card1);
		card2_reg = Sprite.getSprite(card2);
		
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
	
	public void addToPot(int to_add) {
		this.to_add = to_add;
		anim_select = 3;
		timer.restart();
		this.repaint();
	}
	
	public void setTableCards(Card[] table_cards) {
		this.table_cards = table_cards;
		int i;
		for (i = 0; i < 5; i ++) {
			if (table_cards[i] == null) break;
			itable_cards[i] = Sprite.getSprite(table_cards[i]).getScaledInstance(c_width, c_height, Image.SCALE_FAST);
		}
		
		if (i == 3) {
			anim_select = 8;
			timer.restart();
		} else if (i == 4) {
			anim_select = 9;
			timer.restart();
		} else if (i == 5) {
			//System.out.println("hello");
			anim_select = 10;
			timer.restart();
		}
		
		
		
		this.repaint();
		
	}
	
	
	private ActionListener animator = new ActionListener() {
		
		public void actionPerformed(ActionEvent e) {
			c_anim++;
			if (c_anim == 20) {
				c_anim = 0;
				
				if (anim_select < 9 && anim_select > 5) {
					anim_select--;
				} else {
					anim_select++;
				}
				
				if (anim_select == 6) anim_select = 11;
				
			}
			if (anim_select == 2) {
				timer.stop();
			}
			if (anim_select == 4) {
				pot += to_add;
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
		
		g2d.drawImage(table1, 0, 0, this);
		
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
		
		
		
		
		//draw table cards
		for (int i = 0; i < 5; i++) {
			if (this.table_cards[i] != null) {
				if (anim_select == i+6) g2d.drawImage(itable_cards[i], (int) Math.floor((width/2) + (2 * ((((double)c_anim-20)/(20.0/((double)i+1.0)) -2)+i) * (c_width/2 + 5))), height/3, this);
				else g2d.drawImage(itable_cards[i], (width/2) + (2 * (i-2) * (c_width/2 + 5) ) , height/3, this);
			}
		}
		
		//draw deck 5 times to make it look 3d
		for (int i = 0; i < 5; i++) {
			g2d.drawImage(ideck, (int) Math.floor((width/2) + ( (-6-((double)i*5.0/100.0)) * (c_width/2 + 5))) ,(int) Math.floor((1.0-((double)i/100.0)) * height/3), this);
		}
		
		
		
//		for (int i = 1; i < 5; i++) {
//			if (this.table_cards[i] == null) break;
//			g2d.drawImage(itable_cards[i], (width/2) + (2 * (i-2) * (c_width/2 + 5) ) , height/3, this);
//			
//			
//		}
			
		//chip stacking algorithm
		int[] chips = Chips.getChips(this.money);
		for (int i = 5; i >= 0; i--) {
			for (int j = 0; j < chips[i]; j++) {
				g2d.drawImage(chip_spr[i], (int) Math.floor((width/2) + (((i % 3) - 1)  * (1.1 * ch_size)) - ch_size/2 + 2) , (int) Math.floor(height - (height/3) - (j * (ch_size/10)) - ((i / 3) * (1.05 * ch_size))), this);
			}
		}
		
		
		
		//draw pot chips
		
		int[] pot = Chips.getChips(this.pot);
		for (int i = 5; i >= 0; i--) {
			for (int j = 0; j < pot[i]; j++) {
				g2d.drawImage(chip_spr[i], (int) Math.floor((width/2) + (((i % 3) - 1)  * (1.1 * ch_size)) - ch_size/2 + 2) , (int) Math.floor(height/4 - (j * (ch_size/10)) - ((i / 3) * (1.05 * ch_size))), this);
			}
		}
		
		if (anim_select == 3) {
			int[] to_add = Chips.getChips(this.to_add);
			for (int i = 5; i >= 0; i--) {
				for (int j = 0; j < to_add[i]; j++) {
					g2d.drawImage(chip_spr[i], (int) Math.floor((width/2) + (((i % 3) - 1)  * (1.1 * ch_size)) - ch_size/2 + 2) , (int) Math.floor(height/4 - (j * (ch_size/10)) - ((i / 3) * (1.05 * ch_size)) + ((400-(c_anim*c_anim)) * (((2 * height/3) - height/4))/400)), this);
				}
			}

		}
		
		
		
		//other players cards
		//p2
		g2d.drawImage(iback, (width/4) - b_width, height - (height/3), this);
		g2d.drawImage(iback, (width/4) + 5, height - (height/3), this);
		
		//p3
		g2d.drawImage(iback, 3 * (width/4) - b_width, height - (height/3), this);
		g2d.drawImage(iback, 3 * (width/4) + 5, height - (height/3), this);
		
		//p4
		g2d.drawImage(iback, (width/8) - b_width, height/2 - (b_height/2), this);
		g2d.drawImage(iback, (width/8) + 5, height/2 - (b_height/2), this);
		
		//p5
		g2d.drawImage(iback, 7 * (width/8) - b_width, height/2 - (b_height/2), this);
		g2d.drawImage(iback, 7 * (width/8) + 5 , height/2 - (b_height/2), this);
		
		
		
		
		
		
		
		
		Toolkit.getDefaultToolkit().sync();
	}
	
	
	
}
