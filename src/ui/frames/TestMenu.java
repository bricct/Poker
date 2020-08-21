package ui.frames;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import netwrk.User;
import sound.MusicController;
import ui.panels.HostPanel;
import ui.panels.JoinPanel;
import ui.panels.MenuPanel;
import ui.panels.OptionsPanel;
import ui.panels.PortPanel;

/**
 * Menu class for the game
 */
public class TestMenu extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel panel;

	public static final String[] names = {"Salami", "Nerd", "SweatBag", "Jimbabwe", "Santa", "Struedel", "Epstein", "WetKisser", "Pudgy", "ThiccBoi", "Wedgie",
											"Grundle", "Scoob", "Carl", "BedPan", "Willie"};
	public static final String[] table_c = {"Green", "Red", "Purple"};

	public static final int[] starting_c = {500, 1000, 1500};
	public static final int[] blind_c = {10, 20, 50};

	public static int starting_cash;
	public static int blind_cash;
	public static int port;
	public static int[] ip;

	public static boolean mus_toggle, mode;

	public static String name;
	public static int table_ind, name_ind, blind_ind, starting_ind;


	public static BufferedImage tables[];
	public static BufferedImage table;

	/**
	 * Constructor for the menu panel
	 * @param mode mode of the game
	 */
	public TestMenu(boolean mode) {
		this.setSize(906, 520);
		System.out.println(this.getWidth() + " " +  this.getHeight());

		TestMenu.mode = mode;

		TestMenu.mus_toggle = true;

		MusicController.init();
		MusicController.volume = MusicController.Volume.LOW;

		starting_cash = starting_c[0];
		blind_cash = blind_c[0];
		port = 12345;

		ip = new int[4];
		ip[0] = 67;
		ip[1] = 242;
		ip[2] = 72;
		ip[3] = 82;



		if (TestMenu.mode)
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



		this.panel = new MenuPanel(this);
		this.add(this.panel);

		Random random = new Random();



		name_ind = random.nextInt(names.length);
		blind_ind = 0; table_ind = 0; starting_ind = 0;

		name = names[name_ind];

	}


	/**
	 * Trigger the host panel
	 * @param mus_toggle
	 * @param mode
	 */
	public void host() {
		this.remove(this.panel);
		this.panel = new HostPanel(this);
		this.add(this.panel);
		this.panel.repaint();

	}

	/**
	 * Trigger the host panel
	 * @param mus_toggle
	 * @param mode
	 */
	public void port() {
		this.remove(this.panel);
		this.panel = new PortPanel(this);
		this.add(this.panel);
		this.panel.repaint();

	}


	/**
	 * Trigger the options panel
	 * @param mus_toggle
	 * @param mode
	 */
	public void options() {
		this.remove(this.panel);
		this.panel = new OptionsPanel(this);
		this.add(this.panel);
		this.panel.repaint();

	}

	public void join() {
		this.remove(this.panel);
		this.panel = new JoinPanel(this);
		this.add(this.panel);
		this.panel.repaint();

	}



	/**
	 * Initializes the game with the new given settings
	 * @param mus_toggle
	 * @param mode
	 */
	public void game(boolean hosting) {
		if (hosting) {
			TestMenu.ip[0] = 127;
			TestMenu.ip[1] = 0;
			TestMenu.ip[2] = 0;
			TestMenu.ip[3] = 1;
		}
		new User(this);

		// TestBoard board = new TestBoard(this, new User());
		// Dimension scr_dim = getSize();
		// int width = (int) scr_dim.getWidth();
		// int height = (int) scr_dim.getHeight();
		// board.setSize(width, height);
		// board.setLocationRelativeTo(null);
		// board.setVisible(true);
		// board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// this.setVisible(false);
	}



	/**
	 * Triggers the actual menu ui
	 * @param mus_toggle
	 * @param mode
	 */
	public void menu() {
		this.remove(this.panel);
		this.panel = new MenuPanel(this);
		this.add(this.panel);
		this.panel.repaint();

	}


}
