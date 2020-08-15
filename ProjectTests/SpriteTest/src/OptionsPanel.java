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
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class OptionsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private BufferedImage formButton, musicOn, musicOff, save, colon;
	private Image iformButton, imusic, isave, itable, icolon;
	private int width, height, c_height, c_width, o_width, o_height, o_small, c_small;
	private boolean mchanging, mus_toggle, tableing, naming, optioning;
//	private TestMenu master;
	private Font font;
	
	private int name_ind;
	protected int table_ind;
	//private String name;
	
	public OptionsPanel(TestMenu master, boolean _mus_toggle, boolean mode) {
//		this.master = master;
		
		this.setSize(master.getWidth(), master.getHeight());
		Dimension scr_dim = getSize();
		width = (int) scr_dim.getWidth() - 16;
		height = (int) scr_dim.getHeight() - 39;
		
		this.font = new Font("UglyPoker", Font.TRUETYPE_FONT, (height/150 + width/450));
		
		
		this.table_ind = 0;
		
		formButton = Sprite.getFormSprite();
		musicOn = Sprite.getMusicOnSprite();
		musicOff = Sprite.getMusicOffSprite();
		save = Sprite.getSaveSprite();
		colon = Sprite.getColonSprite();
		//close = Sprite.getCloseSprite();
		
		mchanging = false;
		mus_toggle = _mus_toggle;
		
		
		
		c_height = height/5;
		c_small = width/10;
		c_width = width/10 * 3; 
		
		o_width = width/5;
		o_height = height/6;
		
		o_small = o_width/10;
		
		itable = TestMenu.table.getScaledInstance(width, height, Image.SCALE_FAST);
		
		iformButton = formButton.getScaledInstance(o_width, o_height, Image.SCALE_FAST);
		if(mus_toggle)
			imusic = musicOn.getScaledInstance(c_small, c_height, Image.SCALE_FAST);
		else
			imusic = musicOff.getScaledInstance(c_small, c_height, Image.SCALE_FAST);
		//imusicOff = musicOff.getScaledInstance(c_height, c_height, Image.SCALE_FAST);
		isave = save.getScaledInstance(c_small, c_height, Image.SCALE_FAST);
		icolon = colon.getScaledInstance(o_small, o_height, Image.SCALE_FAST);
		//iclose = close.getScaledInstance(c_height, c_height, Image.SCALE_FAST);
		
		
		//ititle = title.getScaledInstance(width/4 * 3, height/5 * 4, Image.SCALE_FAST);
		//iformButton = 
		
//		Random random = new Random();
//		this.name_ind = random.nextInt(this.names.length);
		
		
		
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
				if(mus_toggle)
					imusic = musicOn.getScaledInstance(c_small, c_height, Image.SCALE_FAST);
				else
					imusic = musicOff.getScaledInstance(c_small, c_height, Image.SCALE_FAST);
				//imusicOff = musicOff.getScaledInstance(c_height, c_height, Image.SCALE_FAST);
				isave = save.getScaledInstance(c_small, c_height, Image.SCALE_FAST);
				icolon = colon.getScaledInstance(o_small, o_height, Image.SCALE_FAST);
				//iclose = close.getScaledInstance(c_height, c_height, Image.SCALE_FAST);
				
		    }
		});
		
		
		
		this.addMouseListener(new MouseAdapter() {

//			g2d.drawImage(itable, 0, 0, this);
//			g2d.drawImage(iformButton, width/2 - c_width/2, height/7, this);
//			g2d.drawImage(iformButton, width/2 - c_width/2, height/7 + (c_height/3 * 4), this);
//			g2d.drawImage(imusic, width/2 - c_width/2, height/7 + (c_height/3 * 8), this);
//			g2d.drawImage(isave, width/2 - c_width/2 + (c_width - c_height), height/7 + (c_height/3 * 8), this);
			
			
            @Override
            public void mousePressed(MouseEvent e) {
            	int x=e.getX();
                int y=e.getY();
                
                
                
                if (y < height/7 + (c_height/3 * 8) + c_height && y > height/7 + (c_height/3 * 8)) {


	                if (x >= width/2 - c_width/2  && x < width/2 - c_width/2 + c_height) 
	                	mchanging = true;
	                else if (x >= width/2 - c_width/2 + (c_width - c_height) && x < width/2 - c_width/2 + (c_width - c_height) + c_height)
	                	optioning = true;
	                else {
	                	mchanging = false;
	                	optioning = false;
	                }
	                
//	            	g2d.drawImage(iformButton, width/2 + 3*o_small/2, height/7, this);
//	        		g2d.drawImage(iformButton, width/2 + 3*o_small/2, height/7 + (c_height/3 * 4), this);
            	} else if (x >= width/2 + 3*o_small/2  && x < width/2 + 3*o_small/2 + o_width) {
                		if (y < height/7 + o_height && y > height/7) {
                			tableing = true;
                		} else if (y < height/7 + (c_height/3 * 4) + o_height && y > height/7 + (c_height/3 * 4)) {
                			naming = true;
                		} else {
                        	naming = false; tableing = false;
                        }
                } else {
                	mchanging = false;
            		optioning = false;
                	naming = false;
                	tableing = false;
                }
                
                
                
                
                
                
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            	int x=e.getX();
                int y=e.getY();
            	if (mchanging || optioning) {
	            	
	            	
	                if ( y < height/7 + (c_height/3 * 8) + c_height && y > height/7 + (c_height/3 * 8)) {
	                	
	                	if (mchanging) {
	                		if (x >= width/2 - c_width/2  && x < width/2 - c_width/2 + c_height) {
	                			if (mus_toggle) {
	    	                		imusic = musicOff.getScaledInstance(c_small, c_height, Image.SCALE_FAST);
	    	                		if (mode)
	    	                			MusicController.THEME.stop();
	    	                		else {
	    	                			MusicController.SECRET.stop();
	    	                		}
	    	                	} else {
	    	                		imusic = musicOn.getScaledInstance(c_small, c_height, Image.SCALE_FAST);
	    	                		if (mode)
	    	                			MusicController.THEME.play();
	    	                		else {
	    	                			MusicController.SECRET.play();
	    	                		}
	    	                	}
	    	            		mus_toggle = !mus_toggle;
	    	            		repaint();
	                			
	                		}
	                	} else if (optioning) {
	                		if (x >= width/2 - c_width/2 + (c_width - c_small) && x < width/2 - c_width/2 + c_width) {
	                			master.menu(mus_toggle, mode);
	                		}
	                	}
	                	 
	                	
	                }
	                

            	} else if (tableing || naming) {
            		
            		if (x >= width/2 + 3*o_small/2  && x < width/2 + 3*o_small/2 + o_width) {
            			if (tableing) {
            				if (y < height/7 + o_height && y > height/7) {
            					if (SwingUtilities.isRightMouseButton(e)) {
	            					table_ind -= 1;
		            				if (table_ind < 0) {
		            					table_ind = TestMenu.table_c.length - 1;
		            				}
	            				} else if (SwingUtilities.isLeftMouseButton(e)) {
	            				
	            					table_ind += 1;
		            				if (table_ind == TestMenu.table_c.length) {
		            					table_ind = 0;
		            				}
	            				}
            					
            					TestMenu.table = TestMenu.tables[table_ind];
            					itable = TestMenu.table.getScaledInstance(width, height, Image.SCALE_FAST);
	            				repaint();
	            			} 
            			}
            			if (naming) {
	            			if (y < height/7 + (c_height/3 * 4) + o_height && y > height/7 + (c_height/3 * 4)) {
	            				if (SwingUtilities.isRightMouseButton(e)) {
	            					name_ind -= 1;
		            				if (name_ind < 0) {
		            					name_ind = TestMenu.names.length - 1;
		            				}
	            				} else if (SwingUtilities.isLeftMouseButton(e)) {
	            				
		            				name_ind += 1;
		            				if (name_ind == TestMenu.names.length) {
		            					name_ind = 0;
		            				}
	            				}
	            				TestMenu.name = TestMenu.names[name_ind];
	            				repaint();
		            		} 
            			}
	            	}
            		
            		
            	}
            	
            	naming = false; tableing = false; mchanging = false; optioning = false;
            }
        });
		
		
		
		
	}
	
	
	
	
	
	
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		
		
		
		
		
		
		//g2d.drawString("You",(int) Math.floor( width/2 + 1.2 * c_width), 6* height/8);
		
		
		g2d.drawImage(itable, 0, 0, this);
		g2d.drawImage(iformButton, width/2 - o_width - (3*o_small/2), height/7, this);
		g2d.drawImage(iformButton, width/2 - o_width - 3*o_small/2, height/7 + (c_height/3 * 4), this);
		g2d.drawImage(iformButton, width/2 + 3*o_small/2, height/7, this);
		g2d.drawImage(iformButton, width/2 + 3*o_small/2, height/7 + (c_height/3 * 4), this);
		g2d.drawImage(imusic, width/2 - c_width/2, height/7 + (c_height/3 * 8), this);
		g2d.drawImage(icolon, width/2 - o_small/2, height/7, this);
		g2d.drawImage(icolon, width/2 - o_small/2, height/7 + (c_height/3 * 4), this);
		//g2d.drawImage(iclose, width - c_height, height/7, this);
		g2d.drawImage(isave, width/2 - c_width/2 + (c_width - c_small), height/7 + (c_height/3 * 8), this);
		
		//g2d.drawImage(ititle, width/8, height/10, this);
		
		g2d.setColor(Color.lightGray);
		g2d.setFont(this.font);
		
		FontMetrics metrics = g2d.getFontMetrics(font);
		
		g2d.drawString("TABLE" , width/2 - 2*o_width/3 - (3*o_small/2), height/7 + c_height/3);
		g2d.drawString(TestMenu.table_c[table_ind], width/2 + 3*o_small/2 + o_width/2 - metrics.stringWidth(TestMenu.table_c[table_ind])/2, height/7 + o_height/2 - metrics.getHeight()/2);
		g2d.drawString("NAME", width/2 - (2*o_width/3) - 3*o_small/2, height/7 + (c_height/3 * 4) + c_height/3);
		g2d.drawString(TestMenu.name, width/2 + 3*o_small/2 + o_width/2 - metrics.stringWidth(TestMenu.name)/2, height/7 + (c_height/3 * 4) + o_height/2 - metrics.getHeight()/2);
	}


}