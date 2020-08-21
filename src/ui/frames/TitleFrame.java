package ui.frames;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import ui.panels.TitlePanel;

/**
 * Testing class for testing the title panel class
 */
public class TitleFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private TitlePanel panel;
	private boolean control, shift, c;

	/**
	 * Constructor for testing the panel
	 */
	public TitleFrame() {
		this.setSize(906, 520);
		this.setLocationRelativeTo(null);
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

	/**
	 * Creates a game object for the test class
	 */
	public void game(boolean normalMode) {
		MenuFrame board = new MenuFrame(normalMode);
		Dimension scr_dim = getSize();
		int width = (int) scr_dim.getWidth();
		int height = (int) scr_dim.getHeight();
		board.setSize(width, height);
		board.setLocationRelativeTo(this);
		board.setVisible(true);
		board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dispose();
	}


}
