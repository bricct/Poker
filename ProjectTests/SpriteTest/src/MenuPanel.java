import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class MenuPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private BufferedImage menuButton, musicOn, musicOff, options, close, table;
	private Image imenuButton, imusic, ioptions, iclose, itable;
	private int width, height, c_height, c_width;
	private boolean mchanging, mus_toggle, joining, hosting, optioning, mode;
	private TestMenu master;
	private Font font;
	
	
	public MenuPanel(TestMenu master, boolean mode) {
		this.master = master;
		this.setSize(900, 480);
		this.mode = mode;
		Dimension scr_dim = getSize();
		width = (int) scr_dim.getWidth();
		height = (int) scr_dim.getHeight();
		
		this.font = new Font("UglyPoker", Font.TRUETYPE_FONT, height/75);
		
		
		MusicController.init();
		MusicController.volume = MusicController.Volume.LOW;
		
		if (this.mode)
			MusicController.THEME.play();
		else {
			MusicController.SECRET.play();
		}
		
		try {
			//title = ImageIO.read(new File("title.png"));
			table = ImageIO.read(new File("table.png"));
		} catch (IOException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		menuButton = Sprite.getButtonSprite();
		musicOn = Sprite.getMusicOnSprite();
		musicOff = Sprite.getMusicOffSprite();
		options = Sprite.getOptionsSprite();
		close = Sprite.getCloseSprite();
		
		mchanging = false;
		mus_toggle = true;
		
		
		
		c_height = height/5;
		c_width = width/10 * 3; 
		itable = table.getScaledInstance(width, height, Image.SCALE_FAST);
		
		imenuButton = menuButton.getScaledInstance(c_width, c_height, Image.SCALE_FAST);
		imusic = musicOn.getScaledInstance(c_height, c_height, Image.SCALE_FAST);
		//imusicOff = musicOff.getScaledInstance(c_height, c_height, Image.SCALE_FAST);
		ioptions = options.getScaledInstance(c_height, c_height, Image.SCALE_FAST);
		iclose = close.getScaledInstance(c_height, c_height, Image.SCALE_FAST);
		
		
		//ititle = title.getScaledInstance(width/4 * 3, height/5 * 4, Image.SCALE_FAST);
		//imenuButton = 
		
		
		
		
		
		
		
		this.addComponentListener(new ComponentAdapter() {
		    public void componentResized(ComponentEvent componentEvent) {
		    	
		    	Dimension scr_dim = getSize();
				width = (int) scr_dim.getWidth();
				height = (int) scr_dim.getHeight();
				
				c_height = height/5;
				c_width = width/10 * 3; 
		    	
				font = new Font("UglyPoker", Font.TRUETYPE_FONT, height/75);
				
				
				itable = table.getScaledInstance(width, height, Image.SCALE_FAST);
				//ititle = title.getScaledInstance(width/4 * 3, height/5 * 4, Image.SCALE_FAST);
				
				imenuButton = menuButton.getScaledInstance(c_width, c_height, Image.SCALE_FAST);
				imusic = imusic.getScaledInstance(c_height, c_height, Image.SCALE_FAST);
				//imusicOff = musicOff.getScaledInstance(c_height, c_height, Image.SCALE_FAST);
				ioptions = options.getScaledInstance(c_height, c_height, Image.SCALE_FAST);
				iclose = close.getScaledInstance(c_height, c_height, Image.SCALE_FAST);
				
		    }
		});
		
		
		
		this.addMouseListener(new MouseAdapter() {

//			g2d.drawImage(itable, 0, 0, this);
//			g2d.drawImage(imenuButton, width/2 - c_width/2, height/7, this);
//			g2d.drawImage(imenuButton, width/2 - c_width/2, height/7 + (c_height/3 * 4), this);
//			g2d.drawImage(imusic, width/2 - c_width/2, height/7 + (c_height/3 * 8), this);
//			g2d.drawImage(ioptions, width/2 - c_width/2 + (c_width - c_height), height/7 + (c_height/3 * 8), this);
			
			
            @Override
            public void mousePressed(MouseEvent e) {
            	int x=e.getX();
                int y=e.getY();
                
                
                
                if (x >= width/2 - c_width/2  && x < width/2 - c_width/2 + c_height && y < height/7 + (c_height/3 * 8) + c_height && y > height/7 + (c_height/3 * 8)) {
                	mchanging = true;
                } else {
                	mchanging = false;
                }
                
                
                if (x >= width/2 - c_width/2  && x < width/2 + c_width/2) {
                		if (y < height/7 + c_height && y > height/7) {
                			hosting = true;
                		} else if (y < height/7 + (c_height/3 * 4) + c_height && y > height/7 + (c_height/3 * 4)) {
                			joining = true;
                		} else {
                        	hosting = false; joining = false;
                        }
                } else {
                	hosting = false;
                }
                
                
                
                
                
                
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            	int x=e.getX();
                int y=e.getY();
            	if (mchanging) {
	            	
	            	
	                if (x >= width/2 - c_width/2  && x < width/2 - c_width/2 + c_height && y < height/7 + (c_height/3 * 8) + c_height && y > height/7 + (c_height/3 * 8)) {
	                	hosting = false; joining = false; mchanging = false;
	                	if (mus_toggle) {
	                		imusic = musicOff.getScaledInstance(c_height, c_height, Image.SCALE_FAST);
	                		if (mode)
	                			MusicController.THEME.stop();
	                		else {
	                			MusicController.SECRET.stop();
	                		}
	                	} else {
	                		imusic = musicOn.getScaledInstance(c_height, c_height, Image.SCALE_FAST);
	                		if (mode)
	                			MusicController.THEME.play();
	                		else {
	                			MusicController.SECRET.play();
	                		}
	                	}
	            		mus_toggle = !mus_toggle;
	            		repaint();
	                } else {
	                	mchanging = false;
	                }
	                

            	} else if (joining || hosting) {
            		hosting = false; joining = false;
            		if (x >= width/2 - c_width/2  && x < width/2 + c_width/2) {
            			if (y < height/7 + c_height && y > height/7) {
                			master.game();
                		} else if (y < height/7 + (c_height/3 * 4) + c_height && y > height/7 + (c_height/3 * 4)) {
                			master.game();
                		}
            		}
            		
            		
            	}
            }
        });
		
		
		
		
	}
	
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		
		
		
		
		
		
		//g2d.drawString("You",(int) Math.floor( width/2 + 1.2 * c_width), 6* height/8);
		
		
		g2d.drawImage(itable, 0, 0, this);
		g2d.drawImage(imenuButton, width/2 - c_width/2, height/7, this);
		g2d.drawImage(imenuButton, width/2 - c_width/2, height/7 + (c_height/3 * 4), this);
		g2d.drawImage(imusic, width/2 - c_width/2, height/7 + (c_height/3 * 8), this);
		//g2d.drawImage(iclose, width - c_height, height/7, this);
		g2d.drawImage(ioptions, width/2 - c_width/2 + (c_width - c_height), height/7 + (c_height/3 * 8), this);
		
		//g2d.drawImage(ititle, width/8, height/10, this);
		
		g2d.setColor(Color.lightGray);
		g2d.setFont(this.font);
		
		g2d.drawString("JOIN GAME" , width/2 - c_height, height/7 + c_height/3);
		g2d.drawString("HOST GAME", width/2 - c_height, height/7 + (c_height/3 * 4) + c_height/3);
	}


}
