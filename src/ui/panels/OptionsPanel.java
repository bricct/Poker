package ui.panels;

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

import sound.MusicController;
import ui.Sprite;
import ui.frames.MenuFrame;

/**
 * Optionspanel deals with the UI of the game for the options menu.
 */
public class OptionsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private BufferedImage formButton, musicOn, musicOff, save, colon;
	private Image iformButton, imusic, isave, itable, icolon;
	private int width, height, c_height, c_width, o_width, o_height, o_small, c_small;
	private boolean mchanging, tableing, naming, optioning;
	private Font font;


	/**
	 * Constructor for the options panel
	 * @param master The parent jframe to be embedded in
	 */
	public OptionsPanel(MenuFrame master) {

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

		mchanging = false;



		c_height = height/5;
		c_small = width/10;
		c_width = width/10 * 3;

		o_width = width/5;
		o_height = height/6;

		o_small = o_width/10;

		itable = MenuFrame.table.getScaledInstance(width, height, Image.SCALE_FAST);

		iformButton = formButton.getScaledInstance(o_width, o_height, Image.SCALE_FAST);
		if(MenuFrame.mus_toggle)
			imusic = musicOn.getScaledInstance(c_small, c_height, Image.SCALE_FAST);
		else
			imusic = musicOff.getScaledInstance(c_small, c_height, Image.SCALE_FAST);
		isave = save.getScaledInstance(c_small, c_height, Image.SCALE_FAST);
		icolon = colon.getScaledInstance(o_small, o_height, Image.SCALE_FAST);


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


				itable = MenuFrame.table.getScaledInstance(width, height, Image.SCALE_FAST);

				iformButton = formButton.getScaledInstance(o_width, o_height, Image.SCALE_FAST);
				if(MenuFrame.mus_toggle)
					imusic = musicOn.getScaledInstance(c_small, c_height, Image.SCALE_FAST);
				else
					imusic = musicOff.getScaledInstance(c_small, c_height, Image.SCALE_FAST);
				isave = save.getScaledInstance(c_small, c_height, Image.SCALE_FAST);
				icolon = colon.getScaledInstance(o_small, o_height, Image.SCALE_FAST);

		    }
		});



		this.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
            	int x=e.getX();
                int y=e.getY();



                if (y < height/7 + (c_height/3 * 8) + c_height && y > height/7 + (c_height/3 * 8)) {


                	if (x >= width/2 - c_width/2 + (c_width - c_height) && x < width/2 - c_width/2 + (c_width - c_height) + c_height)
	                	optioning = true;
	                else {
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

            	if (optioning) {
	                if ( y < height/7 + (c_height/3 * 8) + c_height && y > height/7 + (c_height/3 * 8)) {
                		if (x >= width/2 - c_width/2 + (c_width - c_small) && x < width/2 - c_width/2 + c_width) {
                			master.menu();
                		}
	                }

            	} else if (tableing || naming) {

            		if (x >= width/2 + 3*o_small/2  && x < width/2 + 3*o_small/2 + o_width) {
            			if (tableing) {
            				if (y < height/7 + o_height && y > height/7) {
            					if (SwingUtilities.isRightMouseButton(e)) {
            						MenuFrame.table_ind -= 1;
		            				if (MenuFrame.table_ind < 0) {
		            					MenuFrame.table_ind = MenuFrame.table_c.length - 1;
		            				}
	            				} else if (SwingUtilities.isLeftMouseButton(e)) {

	            					MenuFrame.table_ind += 1;
		            				if (MenuFrame.table_ind == MenuFrame.table_c.length) {
		            					MenuFrame.table_ind = 0;
		            				}
	            				}

            					MenuFrame.table = MenuFrame.tables[MenuFrame.table_ind];
            					itable = MenuFrame.table.getScaledInstance(width, height, Image.SCALE_FAST);
	            				repaint();
	            			}
            			}
            			if (naming) {
	            			if (y < height/7 + (c_height/3 * 4) + o_height && y > height/7 + (c_height/3 * 4)) {
	            				if (SwingUtilities.isRightMouseButton(e)) {
	            					MenuFrame.name_ind -= 1;
		            				if (MenuFrame.name_ind < 0) {
		            					MenuFrame.name_ind = MenuFrame.names.length - 1;
		            				}
	            				} else if (SwingUtilities.isLeftMouseButton(e)) {

	            					MenuFrame.name_ind += 1;
		            				if (MenuFrame.name_ind == MenuFrame.names.length) {
		            					MenuFrame.name_ind = 0;
		            				}
	            				}
	            				MenuFrame.name = MenuFrame.names[MenuFrame.name_ind];
	            				repaint();
		            		}
            			}
	            	}


            	} else if (mchanging) {
            		if (y >  5 && y < 5 + c_height) {
            			if (x >= 2 && x < 2 + c_small) {
                			if (MenuFrame.mus_toggle) {
    	                		imusic = musicOff.getScaledInstance(c_small, c_height, Image.SCALE_FAST);
    	                		if (MenuFrame.mode)
    	                			MusicController.THEME.stop();
    	                		else {
    	                			MusicController.SECRET.stop();
    	                		}
    	                	} else {
    	                		imusic = musicOn.getScaledInstance(c_small, c_height, Image.SCALE_FAST);
    	                		if (MenuFrame.mode)
    	                			MusicController.THEME.play();
    	                		else {
    	                			MusicController.SECRET.play();
    	                		}
    	                	}
    	            		MenuFrame.mus_toggle = !MenuFrame.mus_toggle;
    	            		repaint();
                		}
                	}
                }

            	naming = false; tableing = false; mchanging = false; optioning = false;
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
		g2d.drawImage(imusic, 2, 5, this);
		g2d.drawImage(icolon, width/2 - o_small/2, height/7, this);
		g2d.drawImage(icolon, width/2 - o_small/2, height/7 + (c_height/3 * 4), this);
		g2d.drawImage(isave, width/2 - c_width/2 + (c_width - c_small), height/7 + (c_height/3 * 8), this);


		g2d.setColor(Color.lightGray);
		g2d.setFont(this.font);

		FontMetrics metrics = g2d.getFontMetrics(font);

		g2d.drawString("TABLE" , width/2 - 2*o_width/3 - (3*o_small/2), height/7 + c_height/3);
		g2d.drawString(MenuFrame.table_c[MenuFrame.table_ind], width/2 + 3*o_small/2 + o_width/2 - metrics.stringWidth(MenuFrame.table_c[MenuFrame.table_ind])/2, height/7 + o_height/2 - metrics.getHeight()/2);
		g2d.drawString("NAME", width/2 - (2*o_width/3) - 3*o_small/2, height/7 + (c_height/3 * 4) + c_height/3);
		g2d.drawString(MenuFrame.name, width/2 + 3*o_small/2 + o_width/2 - metrics.stringWidth(MenuFrame.name)/2, height/7 + (c_height/3 * 4) + o_height/2 - metrics.getHeight()/2);
	}


}
