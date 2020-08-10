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
	private BufferedImage table;
	public TestPanel(Card card1, Card card2) {
		this.setSize(900, 480);
		this.card1 = card1;
		this.card2 = card2;
		try {
			this.table = ImageIO.read(new File("table.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void setCards(Card card1, Card card2) {
		this.card1 = card1;
		this.card2 = card2;
		this.repaint();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		
		
		Graphics2D g2d = (Graphics2D) g;
	
		Dimension scr_dim = this.getSize();
		int width = (int) scr_dim.getWidth();
		int height = (int) scr_dim.getHeight();
		
		Image table1 = this.table.getScaledInstance(width, height, Image.SCALE_FAST);
		
		g2d.drawImage(table1, 0, 0, this);
		BufferedImage card1_reg = Sprite.getSprite(this.card1);
		BufferedImage card2_reg = Sprite.getSprite(this.card2);
		BufferedImage back_reg = Sprite.getBackSprite();
		
		
		
		Image card1 = card1_reg.getScaledInstance(width/13, height/5, Image.SCALE_FAST);
		Image card2 = card2_reg.getScaledInstance(width/13, height/5, Image.SCALE_FAST);
		Image back = back_reg.getScaledInstance(width/13, height/5, Image.SCALE_FAST);
		
		
		int c_width = width/13;
		int c_height = height/5;

		//player cards
		g2d.drawImage(card1, (width/2) - c_width , height - (height/4), this);
		g2d.drawImage(card2, (width/2) + 5, height - (height/4), this);
		
		
		
		//other players cards
		//p2
		g2d.drawImage(back, (width/4) - c_width, height - (height/3), this);
		g2d.drawImage(back, (width/4) + 5, height - (height/3), this);
		
		//p3
		g2d.drawImage(back, 3 * (width/4) - c_width, height - (height/3), this);
		g2d.drawImage(back, 3 * (width/4) + 5, height - (height/3), this);
		
		//p4
		g2d.drawImage(back, (width/8) - c_width, height/2 - (c_height/2), this);
		g2d.drawImage(back, (width/8) + 5, height/2 - (c_height/2), this);
		
		//p5
		g2d.drawImage(back, 7 * (width/8) - c_width, height/2 - (c_height/2), this);
		g2d.drawImage(back, 7 * (width/8) + 5 , height/2 - (c_height/2), this);
		
		
		Toolkit.getDefaultToolkit().sync();
	}
	
	
	
}
