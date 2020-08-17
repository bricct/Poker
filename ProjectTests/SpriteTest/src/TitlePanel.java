import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * This is the TitlePanel class 
 * for showing the introductory Title screen
 * to the game.
 */
public class TitlePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private BufferedImage title, table;
	private Image ititle, itable;
	private int width, height;
	
	/**
	 * Constructor function for initializing the title panel.
	 */
	public TitlePanel() {
		this.setSize(900, 480);
		
		Dimension scr_dim = getSize();
		width = (int) scr_dim.getWidth();
		height = (int) scr_dim.getHeight();
		
		try {
			title = ImageIO.read(new File("title.png"));
			table = ImageIO.read(new File("tableGreen.png"));
		} catch (IOException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		itable = table.getScaledInstance(width, height, Image.SCALE_FAST);
		ititle = title.getScaledInstance(width/4 * 3, height/5 * 4, Image.SCALE_FAST);
		
		this.addComponentListener(new ComponentAdapter() {
		    public void componentResized(ComponentEvent componentEvent) {
		    	
		    	Dimension scr_dim = getSize();
				width = (int) scr_dim.getWidth();
				height = (int) scr_dim.getHeight();
		    	
				itable = table.getScaledInstance(width, height, Image.SCALE_FAST);
				ititle = title.getScaledInstance(width/4 * 3, height/5 * 4, Image.SCALE_FAST);
				
				
		    }
		});
	
		
		
		
		
	}
	
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		
		g2d.drawImage(itable, 0, 0, this);
		g2d.drawImage(ititle, width/8, height/10, this);
		
	}


}
