import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Menu class for the game
 */
public class TestMenu extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	public static final String[] names = {"Salami", "Nerd", "PorkSweat", "Jimbabwe", "Santa", "Struedel", "Epstein", "WetKisser", "Pudgy", "ThiccDaddy"};
	public static final String[] table_c = {"Green", "Red", "Purple"};
	public static String name;
	public static BufferedImage tables[];
	public static BufferedImage table;

	/**
	 * Constructor for the menu panel
	 * @param mode mode of the game
	 */
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
		tables = new BufferedImage[3];
		try {
			//title = ImageIO.read(new File("title.png"));
			tables[0] = ImageIO.read(new File("tableGreen.png"));
			tables[1] = ImageIO.read(new File("tableRed.png"));
			tables[2] = ImageIO.read(new File("tablePurple.png"));
		} catch (IOException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		table = tables[0];
		
		this.panel = new MenuPanel(this, true, mode);
		this.add(this.panel);
		
		Random random = new Random();
		name = names[random.nextInt(names.length)];
		
		
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
	
	/**
	 * Trigger the options panel
	 * @param mus_toggle
	 * @param mode
	 */
	public void options(boolean mus_toggle, boolean mode) {
		this.remove(this.panel);
		this.panel = new OptionsPanel(this, mus_toggle, mode);
		this.add(this.panel);
		this.panel.repaint();
		
	}
	
	/**
	 * Initializes the game with the new given settings
	 * @param mus_toggle
	 * @param mode
	 */
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

	/**
	 * Triggers the actual menu ui
	 * @param mus_toggle
	 * @param mode
	 */
	public void menu(boolean mus_toggle, boolean mode) {
		this.remove(this.panel);
		this.panel = new MenuPanel(this, mus_toggle, mode);
		this.add(this.panel);
		this.panel.repaint();
		
	}
	
	
}
