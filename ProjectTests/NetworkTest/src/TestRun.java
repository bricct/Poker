import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import javax.swing.JFrame;

/**
 * Simple test class function for testing our objects
 */
public class TestRun {

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {

			try {
				GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
				ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("UglyPoker.ttf")));
			} catch (IOException|FontFormatException e) {
				e.printStackTrace();
			}

			TestTitle title = new TestTitle();
			title.setVisible(true);
			title.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		});


	}


}
