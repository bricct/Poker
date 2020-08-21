package ui.panels;

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
import javax.swing.JPanel;

import sound.MusicController;
import ui.Sprite;
import ui.frames.TestMenu;

/**
 * MenuPanel is our UI panel for handling the menu options within the game
 */
public class MenuPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private BufferedImage menuButton, musicOn, musicOff, options, close;
	private Image imenuButton, imusic, ioptions, itable, iclose;
	private int width, height, c_height, c_width, c_small;
	private boolean mchanging,  joining, hosting, optioning, closing;

	private Font font;
	
	/**
	 * Menupanel constructor
	 * @param master Parent jframe to be embedded in.
	 */
	public MenuPanel(TestMenu master) {


		
		this.setSize(master.getWidth(), master.getHeight());
		
		Dimension scr_dim = getSize();
		width = (int) scr_dim.getWidth() - 16;
		height = (int) scr_dim.getHeight() - 39;

		
		this.font = new Font("UglyPoker", Font.TRUETYPE_FONT, (height/100 + width/300));
		
		
		menuButton = Sprite.getButtonSprite();
		musicOn = Sprite.getMusicOnSprite();
		musicOff = Sprite.getMusicOffSprite();
		options = Sprite.getOptionsSprite();
		close = Sprite.getCloseSprite();
		
		mchanging = false;
		
		c_height = height/5;
		c_width = width/10 * 3; 
		c_small = width/10;
		
		itable = TestMenu.table.getScaledInstance(width, height, Image.SCALE_FAST);
		
		imenuButton = menuButton.getScaledInstance(c_width, c_height, Image.SCALE_FAST);
		if(TestMenu.mus_toggle)
			imusic = musicOn.getScaledInstance(c_small, c_height, Image.SCALE_FAST);
		else
			imusic = musicOff.getScaledInstance(c_small, c_height, Image.SCALE_FAST);

		ioptions = options.getScaledInstance(c_small, c_height, Image.SCALE_FAST);
		iclose = close.getScaledInstance(c_small, c_height, Image.SCALE_FAST);
		
		

		
		
		
		
		
		this.addComponentListener(new ComponentAdapter() {
		    public void componentResized(ComponentEvent componentEvent) {
		    	
		    	Dimension scr_dim = getSize();
				width = (int) scr_dim.getWidth();
				height = (int) scr_dim.getHeight();
				
				c_height = height/5;
				c_width = width/10 * 3; 
				c_small = width/10;
		    	
				font = new Font("UglyPoker", Font.TRUETYPE_FONT, (height/100 + width/300));
				
				
				itable = TestMenu.table.getScaledInstance(width, height, Image.SCALE_FAST);
				
				imenuButton = menuButton.getScaledInstance(c_width, c_height, Image.SCALE_FAST);
				
				if(TestMenu.mus_toggle)
					imusic = musicOn.getScaledInstance(c_small, c_height, Image.SCALE_FAST);
				else
					imusic = musicOff.getScaledInstance(c_small, c_height, Image.SCALE_FAST);
				ioptions = options.getScaledInstance(c_small, c_height, Image.SCALE_FAST);
				iclose = close.getScaledInstance(c_small, c_height, Image.SCALE_FAST);
				
		    }
		});
		
		
		
		this.addMouseListener(new MouseAdapter() {

		
            @Override
            public void mousePressed(MouseEvent e) {
            	int x=e.getX();
                int y=e.getY();
                
                
                
                if (y < height/7 + (c_height/3 * 8) + c_height && y > height/7 + (c_height/3 * 8)) {


	                if (x >= width/2 - c_width/2  && x < width/2 - c_width/2 + c_height) 
	                	closing = true;
	                else if (x >= width/2 - c_width/2 + (c_width - c_height) && x < width/2 - c_width/2 + (c_width - c_height) + c_height) {
	                	optioning = true;
	                	System.out.println("optioning");
	                }
	                else {
	                	mchanging = false;
	                	optioning = false;
	                }
            	} else if (x >= width/2 - c_width/2  && x < width/2 + c_width/2) {
                		if (y < height/7 + c_height && y > height/7) {
                			joining = true;
                		} else if (y < height/7 + (c_height/3 * 4) + c_height && y > height/7 + (c_height/3 * 4)) {
                			hosting = true;
                		} else {
                        	hosting = false; joining = false;
                        }
                } else if (y >  5 && y < 5 + c_height) {
                	if (x >= 2 && x < 2 + c_small) {
                		mchanging = true;
                	}
                } else {
                	mchanging = false;
            		optioning = false;
                	hosting = false;
                	joining = false;
                }
                
                
                
                
                
                
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            	int x=e.getX();
                int y=e.getY();
            	if (closing || optioning) {
	            	
	            	
	                if (y < height/7 + (c_height/3 * 8) + c_height && y > height/7 + (c_height/3 * 8)) {
	                	
	                	if (closing) {
	                		if (x >= width/2 - c_width/2 && x <= width/2 - c_width/2 + c_small) {
	                			System.exit(0);
	                		}
	                	}
	                	
	                	if (optioning) {  	
	    	                if ( y < height/7 + (c_height/3 * 8) + c_height && y > height/7 + (c_height/3 * 8)) {
	                    		if (x >= width/2 - c_width/2 + (c_width - c_small) && x < width/2 - c_width/2 + c_width) {
	                    			System.out.println(x + " " + y);
	                    			master.options();
	                    		}
	    	                }
	                	}
	                }
          	 
	                	
	                
	                

            	} else if (joining || hosting) {
            		
            		if (x >= width/2 - c_width/2  && x < width/2 + c_width/2) {
            			if (joining) {
            				if (y < height/7 + c_height && y > height/7) master.join();
            			} else if (hosting) {
            				if (y < height/7 + (c_height/3 * 4) + c_height && y > height/7 + (c_height/3 * 4)) master.host();
            			}
            		}
            		
            		
            	} else if (y >  5 && y < 5 + c_height) {
                	if (x >= 2 && x < 2 + c_small) {
                		if (mchanging) {
                			if (TestMenu.mus_toggle) {
    	                		imusic = musicOff.getScaledInstance(c_small, c_height, Image.SCALE_FAST);
    	                		if (TestMenu.mode)
    	                			MusicController.THEME.stop();
    	                		else {
    	                			MusicController.SECRET.stop();
    	                		}
    	                	} else {
    	                		imusic = musicOn.getScaledInstance(c_small, c_height, Image.SCALE_FAST);
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
            	
            	hosting = false; joining = false; mchanging = false; optioning = false;
            }
        });
		
		
		
		
	}
	
	
	
	
	
	
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		
		
		
		
		

		g2d.drawImage(itable, 0, 0, this);
		g2d.drawImage(imenuButton, width/2 - c_width/2, height/7, this);
		g2d.drawImage(imenuButton, width/2 - c_width/2, height/7 + (c_height/3 * 4), this);
		g2d.drawImage(imusic, 2, 5, this);
		g2d.drawImage(iclose, width/2 - c_width/2, height/7 + (c_height/3 * 8), this);
		g2d.drawImage(ioptions, width/2 - c_width/2 + (c_width - c_small), height/7 + (c_height/3 * 8), this);

		g2d.setColor(Color.lightGray);
		g2d.setFont(this.font);
		
		g2d.drawString("JOIN GAME" , width/2 - c_small, height/7 + c_height/3);
		g2d.drawString("HOST GAME", width/2 - c_small, height/7 + (c_height/3 * 4) + c_height/3);
	}


}
