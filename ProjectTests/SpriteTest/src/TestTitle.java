import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

public class TestTitle extends JFrame {
	
	

	private static final long serialVersionUID = 1L;
	private TitlePanel panel;
	
	
	public TestTitle() {
		this.setSize(906, 520);
		
		this.panel = new TitlePanel();
		this.add(this.panel);
		
		this.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
            	
            	
            	
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            	game();
            }
        });
		
		this.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				game();
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				return;
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				return;
				
			}
		
		});
		
	}
	
	
	public void game() {
		TestBoard board = new TestBoard();
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
