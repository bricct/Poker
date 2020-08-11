import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class TestPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private Card card1, card2;
	private Card[] table_cards;
	private BufferedImage table;
	private int money;
	private int pot;
	public TestPanel(Card card1, Card card2, int money) {
		this.setSize(900, 480);
		this.card1 = card1;
		this.card2 = card2;
		this.money = money;
		this.pot = 1256;
		try {
			this.table = ImageIO.read(new File("table.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.table_cards = new Card[5];
		
		for (int i = 0; i < 5; i++) {
			this.table_cards[i] = null;
		}
		
		
		
		
	}
	
	public void setCards(Card card1, Card card2) {
		this.card1 = card1;
		this.card2 = card2;
		this.repaint();
	}
	
	public void setMoney(int money) {
		this.money = money;
		this.repaint();
	}
	
	public void addToPot(int money) {
		this.pot += money;
		this.repaint();
	}
	
	public void setTableCards(Card[] table_cards) {
		this.table_cards = table_cards;
	}
	
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		
		
		Graphics2D g2d = (Graphics2D) g;
	
		Dimension scr_dim = this.getSize();
		int width = (int) scr_dim.getWidth();
		int height = (int) scr_dim.getHeight();
		
		Image table1 = this.table.getScaledInstance(width, height, Image.SCALE_FAST);
		
		g2d.drawImage(table1, 0, 0, this);
		
		BufferedImage card1_reg, card2_reg;
		BufferedImage back_reg = Sprite.getBackSprite();
		if (this.card1 == null || this.card2 == null) {
			card1_reg = back_reg;
			card2_reg = back_reg;
		} else {
			card1_reg = Sprite.getSprite(this.card1);
			card2_reg = Sprite.getSprite(this.card2);
		}
		
		int c_width = (int) Math.floor((double)width/13.0 );
		int c_height = (int) Math.floor((double)height/5.0 );
		
		int b_width = (int) Math.floor(c_width * 0.7);
		int b_height = (int) Math.floor(c_height * 0.7);
		
		int ch_size = (int) Math.floor((double)width/30.0 );
		
		Image[] chip_spr = new Image[6];
		
		for (int i = 0; i < 6; i++) {
			chip_spr[i] = Sprite.getChipSprite(i).getScaledInstance(ch_size, ch_size, Image.SCALE_FAST);
		}
		
		Image card1 = card1_reg.getScaledInstance(c_width, c_height, Image.SCALE_FAST);
		Image card2 = card2_reg.getScaledInstance(c_width, c_height, Image.SCALE_FAST);
		Image back = back_reg.getScaledInstance(b_width, b_height, Image.SCALE_FAST);
		
		
		

		//player cards
		g2d.drawImage(card1, (width/2) - c_width , height - (height/4), this);
		g2d.drawImage(card2, (width/2) + 5, height - (height/4), this);
		
		
		
		
		
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
		
		
		
		//other players cards
		//p2
		g2d.drawImage(back, (width/4) - b_width, height - (height/3), this);
		g2d.drawImage(back, (width/4) + 5, height - (height/3), this);
		
		//p3
		g2d.drawImage(back, 3 * (width/4) - b_width, height - (height/3), this);
		g2d.drawImage(back, 3 * (width/4) + 5, height - (height/3), this);
		
		//p4
		g2d.drawImage(back, (width/8) - b_width, height/2 - (b_height/2), this);
		g2d.drawImage(back, (width/8) + 5, height/2 - (b_height/2), this);
		
		//p5
		g2d.drawImage(back, 7 * (width/8) - b_width, height/2 - (b_height/2), this);
		g2d.drawImage(back, 7 * (width/8) + 5 , height/2 - (b_height/2), this);
		
		
		//draw table cards
		for (int i = 0; i < 5; i++) {
			if (this.table_cards[i] == null) break;
			g2d.drawImage(Sprite.getSprite(this.table_cards[i]).getScaledInstance(c_width, c_height, Image.SCALE_FAST), (width/2) + (2 * (i-2) * (c_width/2 + 5) - c_width/2) , height/3, this);
			
			
		}
		
		
		
		
		
		Toolkit.getDefaultToolkit().sync();
	}
	
	
	
}
