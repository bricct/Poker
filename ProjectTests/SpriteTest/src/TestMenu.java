import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TestMenu extends JFrame {
	
	

	private static final long serialVersionUID = 1L;
	private JPanel panel;

	
	public TestMenu(boolean mode) {
		this.setSize(906, 520);
		System.out.println(this.getWidth() + " " +  this.getHeight());
		
		MusicController.init();
		MusicController.volume = MusicController.Volume.LOW;
		
		if (mode)
			MusicController.THEME.play();
		else {
			MusicController.SECRET.play();
		}
		
		this.panel = new MenuPanel(this, true, mode);
		this.add(this.panel);
		
		
		
//		this.addMouseListener(new MouseAdapter() {
//
//            @Override
//            public void mousePressed(MouseEvent e) {
//            	
//            	
//            	
//                repaint();
//            }
//
//            @Override
//            public void mouseReleased(MouseEvent e) {
//            	game();
//            }
//        });
		
//		this.addKeyListener(new KeyListener() {
//
//			@Override
//			public void keyPressed(KeyEvent arg0) {
//				game();
//			}
//
//			@Override
//			public void keyReleased(KeyEvent arg0) {
//				return;
//				
//			}
//
//			@Override
//			public void keyTyped(KeyEvent arg0) {
//				return;
//				
//			}
//		
//		});
		
	}
	
	public void options(boolean mus_toggle, boolean mode) {
		this.remove(this.panel);
		this.panel = new OptionsPanel(this, mus_toggle, mode);
		this.add(this.panel);
		this.panel.repaint();
		
	}
	
	
	
	public void game(boolean mus_toggle, boolean mode) {
		TestBoard board = new TestBoard(mus_toggle, mode);
		Dimension scr_dim = getSize();
		int width = (int) scr_dim.getWidth();
		int height = (int) scr_dim.getHeight();
		board.setSize(width, height);
		board.setLocationRelativeTo(null);
		board.setVisible(true);
		board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dispose();
	}

	public void menu(boolean mus_toggle, boolean mode) {
		this.remove(this.panel);
		this.panel = new MenuPanel(this, mus_toggle, mode);
		this.add(this.panel);
		this.panel.repaint();
		
	}
	
	
}
