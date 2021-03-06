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

import sound.MusicController;
import ui.Sprite;
import ui.frames.MenuFrame;

/**
 * Join panel is our JPanel object for handling when the user is prior to joining a game
 */
public class JoinPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private BufferedImage formButton, musicOn, musicOff, save, colon, backButton, button, arrowUp, arrowDown, bigButton;
	private Image iformButton, imusic, isave, itable, icolon, ibackButton, ibutton, iarrowUp, iarrowDown, ibigButton;
	private int width, height, c_height, c_width, o_width, o_height, o_small, c_small, c_big, o_short, t_width, t_height;
	private boolean backing, checking, mchanging;
	private Font font;


	/**
	 * The constructor for the joinpanel object
	 * @param master master frame to be embedded in
	 */
	public JoinPanel(MenuFrame master) {


		this.setSize(master.getWidth(), master.getHeight());
		Dimension scr_dim = getSize();
		width = (int) scr_dim.getWidth() - 16;
		height = (int) scr_dim.getHeight() - 39;

		this.font = new Font("UglyPoker", Font.TRUETYPE_FONT, (height/150 + width/450));


		formButton = Sprite.getFormSprite();
		bigButton = Sprite.getBigButtonSprite();
		button = Sprite.getBigButtonSprite();
		musicOn = Sprite.getMusicOnSprite();
		musicOff = Sprite.getMusicOffSprite();
		save = Sprite.getSaveSprite();
		colon = Sprite.getColonSprite();
		backButton = Sprite.getReturnSprite();
		arrowUp = Sprite.getArrowUpSprite();
		arrowDown = Sprite.getArrowDownSprite();

		backing = false;



		c_height = height/5;
		c_small = width/10;
		c_width = width/10 * 3;
		c_big = width/8 * 4;

		o_width = width/5;
		o_height = height/6;

		o_small = o_width/10;
		o_short = o_height/10 * 2;



		itable = MenuFrame.table.getScaledInstance(width, height, Image.SCALE_FAST);

		ibutton = button.getScaledInstance(c_width, c_height, Image.SCALE_FAST);

		iformButton = formButton.getScaledInstance(o_width, o_height, Image.SCALE_FAST);
		if(MenuFrame.mus_toggle)
			imusic = musicOn.getScaledInstance(c_small, c_height, Image.SCALE_FAST);
		else
			imusic = musicOff.getScaledInstance(c_small, c_height, Image.SCALE_FAST);

		isave = save.getScaledInstance(c_small, c_height, Image.SCALE_FAST);
		icolon = colon.getScaledInstance(o_small, o_height, Image.SCALE_FAST);
		ibackButton = backButton.getScaledInstance(c_small, c_height, Image.SCALE_FAST);

		iarrowUp = arrowUp.getScaledInstance(o_small, o_short, Image.SCALE_FAST);
		iarrowDown = arrowDown.getScaledInstance(o_small, o_short, Image.SCALE_FAST);
		ibigButton = bigButton.getScaledInstance(c_big, c_height, Image.SCALE_FAST);



		this.addComponentListener(new ComponentAdapter() {
		    public void componentResized(ComponentEvent componentEvent) {

		    	Dimension scr_dim = getSize();
				width = (int) scr_dim.getWidth();
				height = (int) scr_dim.getHeight();



				c_height = height/5;
				c_small = width/10;
				c_width = width/10 * 3;
				c_big = width/8 * 4;

				o_width = width/5;
				o_height = height/6;

				o_small = o_width/10;
				o_short = o_height/10 * 2;


				font = new Font("UglyPoker", Font.TRUETYPE_FONT, (height/150 + width/450));



				itable = MenuFrame.table.getScaledInstance(width, height, Image.SCALE_FAST);

				ibutton = button.getScaledInstance(c_width, c_height, Image.SCALE_FAST);
				iformButton = formButton.getScaledInstance(o_width, o_height, Image.SCALE_FAST);
				if(MenuFrame.mus_toggle)
					imusic = musicOn.getScaledInstance(c_small, c_height, Image.SCALE_FAST);
				else
					imusic = musicOff.getScaledInstance(c_small, c_height, Image.SCALE_FAST);
				isave = save.getScaledInstance(c_small, c_height, Image.SCALE_FAST);
				icolon = colon.getScaledInstance(o_small, o_height, Image.SCALE_FAST);
				ibackButton = backButton.getScaledInstance(c_small, c_height, Image.SCALE_FAST);
				ibigButton = bigButton.getScaledInstance(c_big, c_height, Image.SCALE_FAST);
				iarrowUp = arrowUp.getScaledInstance(o_small, o_short, Image.SCALE_FAST);
				iarrowDown = arrowDown.getScaledInstance(o_small, o_short, Image.SCALE_FAST);

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
	                	checking = true;
	                else {
	                	backing = false;
	                	checking = false;
	                }

            	} else if (y >  5 && y < 5 + c_height) {
                	if (x >= 2 && x < 2 + c_small) {
                		mchanging = true;
                	}
                } else {
                	backing = false;
            		checking = false;

                }






            }

            @Override
            public void mouseReleased(MouseEvent e) {
            	int x=e.getX();
                int y=e.getY();
            	if (backing || checking) {


	                if ( y < height/7 + (c_height/3 * 8) + c_height && y > height/7 + (c_height/3 * 8)) {

	                	if (backing) {
	                		if (x >= width/2 - c_width/2  && x < width/2 - c_width/2 + c_height) {
	                			master.menu();

	                		}
	                	} else if (checking) {
	                		if (x >= width/2 - c_width/2 + (c_width - c_small) && x < width/2 - c_width/2 + c_width) {
	                			master.game(false);

	                		}
	                	}


	                }


            	}

            	if (x >= (width/2 - (c_width - o_width - 3 * o_small)/2 + c_width/2 + (4*t_width) - (9*t_width)) && x < (width/2 - (c_width - o_width - 3 * o_small)/2 + c_width/2 + (4*t_width) + t_width) ) {

            		if (y >= height/2 - (c_height/2) + o_height/2 - 6 * t_height/5 - o_short/2 && y <= height/2 - (c_height/2) + o_height/2 - 6 * t_height/5 +o_short/2) {

            			int index = ((width/2 - (c_width - o_width - 3 * o_small)/2 + c_width/2 + (4*t_width) + t_width) - (x - t_width/2)) / (2*t_width);
            			int pow = ((int)Math.pow(10,index));
            			MenuFrame.port += pow;
            			if (MenuFrame.port > 65535) MenuFrame.port = 0;

            		} else if (y >= height/2 - (c_height/2) + o_height/2 + t_height/2 && y <= height/2 - (c_height/2) + o_height/2 + t_height/2 + o_short) {

            			int index = ((width/2 - (c_width - o_width - 3 * o_small)/2 + c_width/2 + (4*t_width) + t_width) - (x - t_width/2)) / (2*t_width);
            			int pow = ((int)Math.pow(10,index));
            			MenuFrame.port -= pow;
            			if (MenuFrame.port < 0) MenuFrame.port = 65535;
            		}


                	repaint();

                }



            	if (x >= (width/2 - (c_big - o_width - 3 * o_small)/2 + c_big/2 - (12*t_width)) && x < (width/2 - (c_big - o_width - 3 * o_small)/2 + c_big/2 + (12*t_width)) ) {

            		if (y >= height/4 - (c_height/2) + o_height/2 - 6 * t_height/5 - o_short/2 && y <= height/4 - (c_height/2) + o_height/2 - 6 * t_height/5 +o_short/2) {

            			int index = ((width/2 - (c_big - o_width - 3 * o_small)/2 + c_big/2 + (12*t_width) + t_width) - (x - t_width/2)) / (2*t_width) - 1;
            			System.out.println("" + index);
            			int ip_byte = 3 - index/3;
            			int pow = ((int)Math.pow(10,index%3));
            			MenuFrame.ip[ip_byte] += pow;
            			if (MenuFrame.ip[ip_byte] > 255) MenuFrame.ip[ip_byte] = 0;



            		} else if (y >= height/4 - (c_height/2) + o_height/2 + t_height/2 && y <= height/4 - (c_height/2) + o_height/2 + t_height/2 + o_short) {

            			int index = ((width/2 - (c_big - o_width - 3 * o_small)/2 + c_big/2 + (12*t_width) + t_width) - (x - t_width/2)) / (2*t_width) - 1;
            			int ip_byte = 3 - index/3;
            			int pow = ((int)Math.pow(10,index%3));
            			MenuFrame.ip[ip_byte] -= pow;
            			if (MenuFrame.ip[ip_byte] < 0) MenuFrame.ip[ip_byte] = 255;

            		}


                	repaint();

                }



            	if (y >  5 && y < 5 + c_height) {
                	if (x >= 2 && x < 2 + c_small) {
                		if (mchanging) {
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


            	backing = false; checking = false; mchanging = false;
            }
        });




	}






	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;








		g2d.drawImage(itable, 0, 0, this);



		g2d.drawImage(iformButton, width/2 - (c_width + o_width + 3*o_small)/2, height/2 - (c_height/2), this);
		g2d.drawImage(iformButton, width/2 - (c_big + o_width + 3*o_small)/2, height/4 - (c_height/2), this);

		g2d.drawImage(icolon, width/2 - (c_width - o_width + o_small)/2, height/2 - (c_height/2), this);
		g2d.drawImage(icolon, width/2 - (c_big - o_width + o_small)/2, height/4 - (c_height/2), this);


		g2d.drawImage(ibutton, width/2 - (c_width - o_width - 3 * o_small)/2, height/2 - (c_height/2) - (c_height - o_height)/2, this);
		g2d.drawImage(ibigButton, width/2 - (c_big - o_width - 3 * o_small)/2, height/4 - (c_height/2) - (c_height - o_height)/2, this);

		g2d.drawImage(ibackButton, width/2 - c_width/2, height/7 + (c_height/3 * 8), this);

		g2d.drawImage(isave, width/2 - c_width/2 + (c_width - c_small), height/7 + (c_height/3 * 8), this);



		g2d.setColor(Color.lightGray);
		g2d.setFont(this.font);

		FontMetrics metrics = g2d.getFontMetrics(font);

		g2d.drawString("IP #", width/2 - (c_big + o_width + 3*o_small)/2 + o_width/2 - metrics.stringWidth("IP #")/2, height/4 - (c_height/2) + o_height/2 - metrics.getHeight()/2);
		g2d.drawString("PORT #" , width/2 - (c_width + o_width + 3*o_small)/2 + o_width/2 - metrics.stringWidth("PORT #")/2, height/2 - (c_height/2) + o_height/2 - metrics.getHeight()/2);

		int port = MenuFrame.port;


		for (int i = 0; i < 5; i++) {

			t_width = metrics.stringWidth("A");
			t_height = metrics.getHeight();
			int num = port %  10;
			g2d.drawString("" + num, width/2 - (c_width - o_width - 3 * o_small)/2 + c_width/2 + (4*t_width) - (2*i*t_width), height/2 - (c_height/2) + o_height/2 - t_height/2);
			g2d.drawImage(iarrowUp, width/2 - (c_width - o_width - 3 * o_small)/2 + c_width/2 + (4*t_width) - (2*i*t_width), height/2 - (c_height/2) + o_height/2 - 6 * t_height/5 - o_short/2, this);
			g2d.drawImage(iarrowDown, width/2 - (c_width - o_width - 3 * o_small)/2 + c_width/2 + (4*t_width) - (2*i*t_width), height/2 - (c_height/2) + o_height/2 + t_height/2, this);
			port /= 10;
		}

		for (int j = 0; j < 4; j++) {
			int ip_byte = MenuFrame.ip[3-j];
			for (int i = 0; i < 3; i++) {

				t_width = metrics.stringWidth("A");
				t_height = metrics.getHeight();

				int num = ip_byte %  10;

				if (i == 2 && j != 3) {
					g2d.drawString(".",  width/2 - (c_big - o_width - 3 * o_small)/2 + c_big/2 + (10*t_width) - (2*i*t_width) - (6*j*t_width) - 3*t_width/4, height/4 - (c_height/2) + o_height/2 - t_height/2);
				}

				g2d.drawString("" + num, width/2 - (c_big - o_width - 3 * o_small)/2 + c_big/2 + (10*t_width) - (2*i*t_width) - (6*j*t_width) , height/4 - (c_height/2) + o_height/2 - t_height/2);
				g2d.drawImage(iarrowUp, width/2 - (c_big - o_width - 3 * o_small)/2 + c_big/2 + (10*t_width) - (2*i*t_width) - (6*j*t_width) , height/4 - (c_height/2) + o_height/2 - 6 * t_height/5 - o_short/2, this);
				g2d.drawImage(iarrowDown, width/2 - (c_big - o_width - 3 * o_small)/2 + c_big/2 + (10*t_width) - (2*i*t_width) - (6*j*t_width) , height/4 - (c_height/2) + o_height/2 + t_height/2, this);
				ip_byte /= 10;
			}
		}

		g2d.drawImage(imusic, 2, 5, this);
	}


}
