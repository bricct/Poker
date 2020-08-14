import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

public class TestTitle extends JFrame {
	
	

	private static final long serialVersionUID = 1L;
	private TitlePanel panel;
	private boolean control, shift, c;
	
	
	public TestTitle() {
		this.setSize(906, 520);
		this.control = false;
		this.shift = false;
		this.c = false;
		this.panel = new TitlePanel();
		this.add(this.panel);
		
		this.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
            	
            	
            	
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            	game(true);
            }
        });
		
		this.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				int keys = e.getKeyCode();

			    if (keys == KeyEvent.VK_CONTROL) {
			        control = true;
			    } 
			    if (keys == KeyEvent.VK_SHIFT) {
			    	shift = true;
			    }
			    if (keys == KeyEvent.VK_C) {
			        c = true;
			    } 
			}


			@Override
			public void keyReleased(KeyEvent arg0) {
				if (control && shift && c) {
					game(false);
				} else {
				
					game(true);
				}
				return;
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				return;
				
			}
		
		});
		
	}
	
	
	public void game(boolean normalMode) {
		TestMenu board = new TestMenu(normalMode);
		Dimension scr_dim = getSize();
		int width = (int) scr_dim.getWidth();
		int height = (int) scr_dim.getHeight();
		board.setSize(width, height);
		board.setLocationRelativeTo(null);
		board.setVisible(true);
		board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dispose();
	}
	
	
}
