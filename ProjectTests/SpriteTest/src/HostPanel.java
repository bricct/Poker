import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class HostPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private BufferedImage formButton, musicOn, musicOff, save, colon, backButton;
	private Image iformButton, imusic, isave, itable, icolon, ibackButton;
	private int width, height, c_height, c_width, o_width, o_height, o_small, c_small;
	private boolean backing, tableing, naming, optioning, mchanging;

	private Font font;
	

	
	public HostPanel(TestMenu master) {

		
		this.setSize(master.getWidth(), master.getHeight());
		Dimension scr_dim = getSize();
		width = (int) scr_dim.getWidth() - 16;
		height = (int) scr_dim.getHeight() - 39;
		
		this.font = new Font("UglyPoker", Font.TRUETYPE_FONT, (height/150 + width/450));

		formButton = Sprite.getFormSprite();
		musicOn = Sprite.getMusicOnSprite();
		musicOff = Sprite.getMusicOffSprite();
		save = Sprite.getSaveSprite();
		colon = Sprite.getColonSprite();
		backButton = Sprite.getReturnSprite();
		
		backing = false;
		
		
		
		c_height = height/5;
		c_small = width/10;
		c_width = width/10 * 3; 
		
		o_width = width/5;
		o_height = height/6;
		
		o_small = o_width/10;
		
		itable = TestMenu.table.getScaledInstance(width, height, Image.SCALE_FAST);
		
		iformButton = formButton.getScaledInstance(o_width, o_height, Image.SCALE_FAST);
		if(TestMenu.mus_toggle)
			imusic = musicOn.getScaledInstance(c_small, c_height, Image.SCALE_FAST);
		else
			imusic = musicOff.getScaledInstance(c_small, c_height, Image.SCALE_FAST);
		isave = save.getScaledInstance(c_small, c_height, Image.SCALE_FAST);
		icolon = colon.getScaledInstance(o_small, o_height, Image.SCALE_FAST);
		ibackButton = backButton.getScaledInstance(c_small, c_height, Image.SCALE_FAST);

		
		this.addComponentListener(new ComponentAdapter() {
		    public void componentResized(ComponentEvent componentEvent) {
		    	
		    	Dimension scr_dim = getSize();
				width = (int) scr_dim.getWidth();
				height = (int) scr_dim.getHeight();
				

				
				c_height = height/5;
				c_small = width/10;
				c_width = width/10 * 3; 
				
				o_width = width/5;
				o_height = height/6;
				
				o_small = o_width/10;
		    	
				font = new Font("UglyPoker", Font.TRUETYPE_FONT, (height/150 + width/450));
				
				
				itable = TestMenu.table.getScaledInstance(width, height, Image.SCALE_FAST);
				
				iformButton = formButton.getScaledInstance(o_width, o_height, Image.SCALE_FAST);
				if(TestMenu.mus_toggle)
					imusic = musicOn.getScaledInstance(c_small, c_height, Image.SCALE_FAST);
				else
					imusic = musicOff.getScaledInstance(c_small, c_height, Image.SCALE_FAST);
				isave = save.getScaledInstance(c_small, c_height, Image.SCALE_FAST);
				icolon = colon.getScaledInstance(o_small, o_height, Image.SCALE_FAST);
				ibackButton = backButton.getScaledInstance(c_small, c_height, Image.SCALE_FAST);
				
		    }
		});
		
		
		
		this.addMouseListener(new MouseAdapter() {
	
			
            @Override
            public void mousePressed(MouseEvent e) {
            	int x=e.getX();
                int y=e.getY();
                
                
                
                if (y < height/7 + (c_height/3 * 8) + c_height && y > height/7 + (c_height/3 * 8)) {


	                if (x >= width/2 - c_width/2  && x < width/2 - c_width/2 + c_height) 
	                	backing = true;
	                else if (x >= width/2 - c_width/2 + (c_width - c_height) && x < width/2 - c_width/2 + (c_width - c_height) + c_height)
	                	optioning = true;
	                else {
	                	backing = false;
	                	optioning = false;
	                }
	                
                } else if (x >= width/2 + 3*o_small/2  && x < width/2 + 3*o_small/2 + o_width) {
                		if (y < height/7 + o_height && y > height/7) {
                			tableing = true;
                		} else if (y < height/7 + (c_height/3 * 4) + o_height && y > height/7 + (c_height/3 * 4)) {
                			naming = true;
                		} else {
                        	naming = false; tableing = false;
                        }
                } else if (y >  5 && y < 5 + c_height) {
                	if (x >= 2 && x < 2 + c_small) {
                		mchanging = true;
                	}
            	} else {
                	backing = false;
            		optioning = false;
                	naming = false;
                	tableing = false;
                }
                
                
                
                
                
                
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            	int x=e.getX();
                int y=e.getY();
            	if (backing || optioning) {
	            	
	            	
	                if ( y < height/7 + (c_height/3 * 8) + c_height && y > height/7 + (c_height/3 * 8)) {
	                	
	                	if (backing) {
	                		if (x >= width/2 - c_width/2  && x < width/2 - c_width/2 + c_height) {
	                			master.menu();
	                			
	                		}
	                	} else if (optioning) {
	                		if (x >= width/2 - c_width/2 + (c_width - c_small) && x < width/2 - c_width/2 + c_width) {
	                			master.port();
	                		}
	                	}
	                	 
	                	
	                }
	                

            	} else if (tableing || naming) {
            		
            		if (x >= width/2 + 3*o_small/2  && x < width/2 + 3*o_small/2 + o_width) {
            			if (tableing) {
            				if (y < height/7 + o_height && y > height/7) {
            					if (SwingUtilities.isRightMouseButton(e)) {
            						TestMenu.starting_ind -= 1;
		            				if (TestMenu.starting_ind < 0) {
		            					TestMenu.starting_ind = TestMenu.starting_c.length - 1;
		            				}
	            				} else if (SwingUtilities.isLeftMouseButton(e)) {
	            				
	            					TestMenu.starting_ind += 1;
		            				if (TestMenu.starting_ind == TestMenu.table_c.length) {
		            					TestMenu.starting_ind = 0;
		            				}
	            				}
            					
            					TestMenu.starting_cash = TestMenu.starting_c[TestMenu.starting_ind];
	            				repaint();
	            			} 
            			}
            			if (naming) {
	            			if (y < height/7 + (c_height/3 * 4) + o_height && y > height/7 + (c_height/3 * 4)) {
	            				if (SwingUtilities.isRightMouseButton(e)) {
	            					TestMenu.blind_ind -= 1;
		            				if (TestMenu.blind_ind < 0) {
		            					TestMenu.blind_ind = TestMenu.blind_c.length - 1;
		            				}
	            				} else if (SwingUtilities.isLeftMouseButton(e)) {
	            				
	            					TestMenu.blind_ind += 1;
		            				if (TestMenu.blind_ind == TestMenu.blind_c.length) {
		            					TestMenu.blind_ind = 0;
		            				}
	            				}
	            				TestMenu.blind_cash = TestMenu.blind_c[TestMenu.blind_ind];
	            				repaint();
		            		} 
            			}
	            	}
            		
            		
            	} else if (mchanging) {
            		if (y >  5 && y < 5 + c_height) {
                    	if (x >= 2 && x < 2 + c_small) {
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
            	
            	naming = false; tableing = false; backing = false; optioning = false; mchanging = false;
            }
        });
		
		
		
		
	}
	
	
	
	
	
	
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		
		
		
		

		
		g2d.drawImage(itable, 0, 0, this);
		g2d.drawImage(iformButton, width/2 - o_width - (3*o_small/2), height/7, this);
		g2d.drawImage(iformButton, width/2 - o_width - 3*o_small/2, height/7 + (c_height/3 * 4), this);
		g2d.drawImage(iformButton, width/2 + 3*o_small/2, height/7, this);
		g2d.drawImage(iformButton, width/2 + 3*o_small/2, height/7 + (c_height/3 * 4), this);
		g2d.drawImage(ibackButton, width/2 - c_width/2, height/7 + (c_height/3 * 8), this);
		g2d.drawImage(icolon, width/2 - o_small/2, height/7, this);
		g2d.drawImage(icolon, width/2 - o_small/2, height/7 + (c_height/3 * 4), this);
		g2d.drawImage(isave, width/2 - c_width/2 + (c_width - c_small), height/7 + (c_height/3 * 8), this);

		
		g2d.setColor(Color.lightGray);
		g2d.setFont(this.font);
		
		FontMetrics metrics = g2d.getFontMetrics(font);
		
		g2d.drawString("STARTING $" , width/2 - metrics.stringWidth("STARTING $")/2 - o_width/2 - (3*o_small/2), height/7 + c_height/3);
		g2d.drawString("$" + TestMenu.starting_cash, width/2 + 3*o_small/2 + o_width/2 - metrics.stringWidth("$" + TestMenu.starting_cash)/2, height/7 + o_height/2 - metrics.getHeight()/2);
		g2d.drawString("BLIND $", width/2 - metrics.stringWidth("BLIND $")/2 - o_width/2 - (3*o_small/2), height/7 + (c_height/3 * 4) + c_height/3);
		g2d.drawString("$" + TestMenu.blind_cash, width/2 + 3*o_small/2 + o_width/2 - metrics.stringWidth("$" + TestMenu.blind_cash)/2, height/7 + (c_height/3 * 4) + o_height/2 - metrics.getHeight()/2);
		
		g2d.drawImage(imusic, 2, 5, this);
		
	}


}
