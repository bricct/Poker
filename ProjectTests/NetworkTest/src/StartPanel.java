import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.Timer;

public class StartPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private StartBox master;
	private Timer connected_timer;
	private Font font;
	private int connected;
	private int width, height;
	private boolean starting, cancelling;
	//private BufferedImage form, button;
	private Image iform, ibutton;
	public StartPanel(StartBox master) {
		this.master = master;
		this.connected = 0;
		this.connected_timer = new Timer(20, updatePanel);
		this.connected_timer.start();
		this.font = new Font("UglyPoker.ttf", Font.TRUETYPE_FONT, 14);
		this.starting = false;
		this.cancelling = false;
		
		Dimension scr_dim = getSize();
		width = (int) scr_dim.getWidth() - 16;
		height = (int) scr_dim.getHeight() - 39;
		
		this.ibutton = Sprite.getButtonSprite().getScaledInstance(width, height/2, Image.SCALE_FAST);
		this.iform = Sprite.getFormSprite().getScaledInstance(width/2, height/2, Image.SCALE_FAST);;
		
		
		this.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				
				if (y > height/2 && y < height) {
					if (x > 0 && x < width/2) {
						cancelling = true;
					} else if (x > width/2 && x < width) {
						starting = true;
					}
				} else {
					cancelling = false;
					starting = false;
				}
				
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				if (starting || cancelling) {
					int x = e.getX();
					int y = e.getY();
					if (y > height/2 && y < height) {
						if (cancelling) {
							if (x > 0 && x < width/2) {
								connected_timer.stop();
								master.cancelServer();
							}
						} else if (starting) {
							if (x > width/2 && x < width) {
								connected_timer.stop();
								master.startServer();
						 	}
						}
					} else {
						cancelling = false;
						starting = false;
					}
				}
			}
			
		});
		
		
		
	}
	
	
	
	private ActionListener updatePanel = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			connected = master.getConnected();
			repaint();
			revalidate();
		}
	};
	
	
	
	public void paintComponent(Graphics g) {
		System.out.println("hey connected # is " + this.connected);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setBackground(Color.black);
		g2d.drawImage(ibutton, 0, 0, this);
		g2d.drawImage(iform, 0, height/2, this);
		g2d.drawImage(iform, width/2, height/2, this);
		
		FontMetrics metrics = g2d.getFontMetrics(this.font);
		
		
		g2d.drawString(this.connected + " players connected", width/2 - metrics.stringWidth(this.connected + " players connected")/2, height/4 - metrics.getHeight());
		g2d.drawString("Cancel", width/4 - metrics.stringWidth("Cancel")/2, 3 * height/4 - metrics.getHeight());
		g2d.drawString("Start", 3 * width/4 - metrics.stringWidth("Start")/2, 3 * height/4 - metrics.getHeight());
		
		
	
	}
}	
