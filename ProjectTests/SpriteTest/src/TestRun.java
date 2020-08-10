import java.awt.EventQueue;

import javax.swing.JFrame;

public class TestRun {

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			
			TestBoard board = new TestBoard();
			board.setVisible(true);
			board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			
		});
		
		
	}
	
	
}
