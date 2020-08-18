import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import javax.swing.JFrame;

public class TestRun {

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {

			try {
			     GraphicsEnvironment ge =
			         GraphicsEnvironment.getLocalGraphicsEnvironment();
			     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("UglyPoker.ttf")));
			} catch (IOException|FontFormatException e) {
			     //Handle exception
			}

			TestTitle title = new TestTitle();
			title.setVisible(true);
			title.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//			@SuppressWarnings("unused")
//			User user = new User();
//			@SuppressWarnings("unused")
//			User user2 = new User();
//			@SuppressWarnings("unused")
//			User user3 = new User();
////			@SuppressWarnings("unused")
////			User user4 = new User();
////			@SuppressWarnings("unused")
////			User user5 = new User();
//
		});


	}


}
