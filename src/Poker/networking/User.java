package poker.networking;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;


public class User {
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			
			Client client2 = new Client();
			int id = client2.connect();
			System.out.println("" + id);
			if (id == -1) System.out.println("uhoh");
			JFrame frame = new JFrame();
			frame.setVisible(true);
			frame.setResizable(false);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			JButton b = new JButton("send message");
			b.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				  {
				    try {
				    	client2.sendMessage(id + " poopie weiner");
				    } catch (Exception ee) {
				    	System.out.println("oopsies");
				    	ee.printStackTrace();
				    }
				  }
			});
			
			frame.add(b);
			frame.pack();
				   
		});
	   
	}
	
	
	
	
}