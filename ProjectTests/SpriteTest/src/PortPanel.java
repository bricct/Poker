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

public class PortPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private BufferedImage formButton, musicOn, musicOff, save, colon, backButton, button, arrowUp, arrowDown;
	private Image iformButton, imusic, isave, itable, icolon, ibackButton, ibutton, iarrowUp, iarrowDown;
	private int width, height, c_height, c_width, o_width, o_height, o_small, c_small, o_short;
	private boolean backing, mus_toggle, checking;
	private boolean[] porting;
//	private TestMenu master;
	private Font font;
	
	
	//private String name;
	
	public PortPanel(TestMenu master, boolean _mus_toggle, boolean mode) {
//		this.master = master;
		
		this.setSize(master.getWidth(), master.getHeight());
		Dimension scr_dim = getSize();
		width = (int) scr_dim.getWidth() - 16;
		height = (int) scr_dim.getHeight() - 39;
		
		this.font = new Font("UglyPoker", Font.TRUETYPE_FONT, (height/150 + width/450));
		
		porting = new boolean[10];
		
		formButton = Sprite.getFormSprite();
		button = Sprite.getBigButtonSprite();
		musicOn = Sprite.getMusicOnSprite();
		musicOff = Sprite.getMusicOffSprite();
		save = Sprite.getSaveSprite();
		colon = Sprite.getColonSprite();
		backButton = Sprite.getReturnSprite();
		arrowUp = Sprite.getArrowUpSprite();
		arrowDown = Sprite.getArrowDownSprite();
		
		backing = false;
		mus_toggle = _mus_toggle;
		
		
		
		c_height = height/5;
		c_small = width/10;
		c_width = width/10 * 3; 
		
		
		o_width = width/5;
		o_height = height/6;
		
		o_small = o_width/10;
		o_short = o_height/10 * 2;

		itable = TestMenu.table.getScaledInstance(width, height, Image.SCALE_FAST);
		
		ibutton = button.getScaledInstance(c_width, c_height, Image.SCALE_FAST);
		
		iformButton = formButton.getScaledInstance(o_width, o_height, Image.SCALE_FAST);
		if(mus_toggle)
			imusic = musicOn.getScaledInstance(c_small, c_height, Image.SCALE_FAST);
		else
			imusic = musicOff.getScaledInstance(c_small, c_height, Image.SCALE_FAST);
		//imusicOff = musicOff.getScaledInstance(c_height, c_height, Image.SCALE_FAST);
		isave = save.getScaledInstance(c_small, c_height, Image.SCALE_FAST);
		icolon = colon.getScaledInstance(o_small, o_height, Image.SCALE_FAST);
		ibackButton = backButton.getScaledInstance(c_height, c_height, Image.SCALE_FAST);
		
		iarrowUp = arrowUp.getScaledInstance(o_small, o_short, Image.SCALE_FAST);
		iarrowDown = arrowDown.getScaledInstance(o_small, o_short, Image.SCALE_FAST);
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
				
				//c_width = width/10 * 4;
				
				o_width = width/5;
				o_height = height/6;
				
				o_small = o_width/10;
				o_short = o_height/10 * 2;
		    	
				font = new Font("UglyPoker", Font.TRUETYPE_FONT, (height/150 + width/450));
				
				
				itable = TestMenu.table.getScaledInstance(width, height, Image.SCALE_FAST);
				
				ibutton = button.getScaledInstance(c_width, c_height, Image.SCALE_FAST);
				iformButton = formButton.getScaledInstance(o_width, o_height, Image.SCALE_FAST);
				if(mus_toggle)
					imusic = musicOn.getScaledInstance(c_small, c_height, Image.SCALE_FAST);
				else
					imusic = musicOff.getScaledInstance(c_small, c_height, Image.SCALE_FAST);
				//imusicOff = musicOff.getScaledInstance(c_height, c_height, Image.SCALE_FAST);
				isave = save.getScaledInstance(c_small, c_height, Image.SCALE_FAST);
				icolon = colon.getScaledInstance(o_small, o_height, Image.SCALE_FAST);
				//iclose = close.getScaledInstance(c_height, c_height, Image.SCALE_FAST);
				ibackButton = backButton.getScaledInstance(c_height, c_height, Image.SCALE_FAST);
				
				iarrowUp = arrowUp.getScaledInstance(o_small, o_short, Image.SCALE_FAST);
				iarrowDown = arrowDown.getScaledInstance(o_small, o_short, Image.SCALE_FAST);
				
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
	                	backing = true;
	                else if (x >= width/2 - c_width/2 + (c_width - c_height) && x < width/2 - c_width/2 + (c_width - c_height) + c_height)
	                	checking = true;
	                else {
	                	backing = false;
	                	checking = false;
	                }
	                
//	            	g2d.drawImage(iformButton, width/2 + 3*o_small/2, height/7, this);
//	        		g2d.drawImage(iformButton, width/2 + 3*o_small/2, height/7 + (c_height/3 * 4), this);
            	} else if (x >= width/2 + 3*o_small/2  && x < width/2 + 3*o_small/2 + o_width) {
                		
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
	                			master.menu(mus_toggle, mode);
	                			
	                		}
//	                		if (x >= width/2 - c_width/2  && x < width/2 - c_width/2 + c_height) {
//	                			if (mus_toggle) {
//	    	                		imusic = musicOff.getScaledInstance(c_small, c_height, Image.SCALE_FAST);
//	    	                		if (mode)
//	    	                			MusicController.THEME.stop();
//	    	                		else {
//	    	                			MusicController.SECRET.stop();
//	    	                		}
//	    	                	} else {
//	    	                		imusic = musicOn.getScaledInstance(c_small, c_height, Image.SCALE_FAST);
//	    	                		if (mode)
//	    	                			MusicController.THEME.play();
//	    	                		else {
//	    	                			MusicController.SECRET.play();
//	    	                		}
//	    	                	}
//	    	            		mus_toggle = !mus_toggle;
//	    	            		repaint();
//	                			
//	                		}
	                	} else if (checking) {
	                		if (x >= width/2 - c_width/2 + (c_width - c_small) && x < width/2 - c_width/2 + c_width) {
	                			System.out.println("Starting Server");
	                		}
	                	}
	                	 
	                	
	                } 
	                

            	} else { 
                	TestMenu.port += 1;
                	repaint();
                }
            		

            	for (int i = 0; i < porting.length; i++) porting[i] = false; 
            	backing = false; checking = false;
            }
        });
		
		
		
		
	}
	
	
	
	
	
	
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		
		
		
		
		
		
		//g2d.drawString("You",(int) Math.floor( width/2 + 1.2 * c_width), 6* height/8);
		
		
		g2d.drawImage(itable, 0, 0, this);
		//g2d.drawImage(iformButton, width/2 - o_width - (3*o_small/2), height/7, this);
		//g2d.drawImage(iformButton, width/2 - o_width - 3*o_small/2, height/7 + (c_height/3 * 4), this);
		//g2d.drawImage(iformButton, width/2 + 3*o_small/2, height/7, this);
		
		
		g2d.drawImage(iformButton, width/2 - (c_width + o_width + 3*o_small)/2, 2 * height/5 - (c_height/2), this);
		g2d.drawImage(icolon, width/2 - (c_width - o_width + o_small)/2, 2 * height/5 - (c_height/2), this);
		
		
		g2d.drawImage(ibutton, width/2 - (c_width - o_width - 3 * o_small)/2, 2 * height/5 - (c_height/2) - (c_height - o_height)/2, this);
		
		g2d.drawImage(ibackButton, width/2 - c_width/2, height/7 + (c_height/3 * 8), this);
		//g2d.drawImage(icolon, width/2 - o_small/2, height/7, this);
		//g2d.drawImage(icolon, width/2 - o_small/2, height/7 + (c_height/3 * 4), this);
		//g2d.drawImage(ibackButton, width - c_height, height/7, this);
		g2d.drawImage(isave, width/2 - c_width/2 + (c_width - c_small), height/7 + (c_height/3 * 8), this);
		
		//g2d.drawImage(ititle, width/8, height/10, this);
		
		g2d.setColor(Color.lightGray);
		g2d.setFont(this.font);
		
		FontMetrics metrics = g2d.getFontMetrics(font);
		
		g2d.drawString("PORT #" , width/2 - (c_width + o_width + 3*o_small)/2 + o_width/2 - metrics.stringWidth("PORT #")/2, 2 * height/5 - (c_height/2) + o_height/2 - metrics.getHeight()/2);
		
		int port = TestMenu.port;
		
		
		for (int i = 0; i < 5; i++) {
			//int pow = ((int)Math.pow(10,i));
			int num = port %  10;
			int t_width = metrics.stringWidth("" + num);
			g2d.drawString("" + num, width/2 - (c_width - o_width - 3 * o_small)/2 + c_width/2 + (4*t_width) - (2*i*t_width), 2 * height/5 - (c_height/2) + o_height/2 - metrics.getHeight()/2);
			g2d.drawImage(iarrowUp, width/2 - (c_width - o_width - 3 * o_small)/2 + c_width/2 + (4*t_width) - (2*i*t_width), 2 * height/5 - (c_height/2) + o_height/2 - 2 * metrics.getHeight()/2 - o_short/2, this);
			g2d.drawImage(iarrowDown, width/2 - (c_width - o_width - 3 * o_small)/2 + c_width/2 + (4*t_width) - (2*i*t_width), 2 * height/5 - (c_height/2) + o_height/2 + metrics.getHeight()/2, this);
			port /= 10;
		}
		//g2d.drawString("BLIND $", width/2 - metrics.stringWidth("BLIND $")/2 - o_width/2 - (3*o_small/2), height/7 + (c_height/3 * 4) + c_height/3);
		//g2d.drawString("$" + TestMenu.blind_cash, width/2 + 3*o_small/2 + o_width/2 - metrics.stringWidth("$" + TestMenu.blind_cash)/2, height/7 + (c_height/3 * 4) + o_height/2 - metrics.getHeight()/2);
	}


}
