// import java.awt.EventQueue;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.util.PriorityQueue;
//
//import javax.swing.JButton;
//import javax.swing.JFrame;
//
//public class UserCopy {
//	public static void main(String[] args) {
//		EventQueue.invokeLater(() -> {
//			
//			
//			PriorityQueue<String> commands = new PriorityQueue<>();
//			
//			Client client = new Client();
//			int id = client.connect();
//			System.out.println("" + id);
//			if (id == -1) System.out.println("uhoh");
//			JFrame frame = new JFrame();
//			frame.setVisible(true);
//			frame.setResizable(false);
//			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//			
//			JButton b = new JButton("send message");
//			b.addActionListener(new ActionListener() {
//				public void actionPerformed(ActionEvent e)
//				  {
//				    try {
//				    	client.sendMessage(id + " raise");
//				    } catch (Exception ee) {
//				    	System.out.println("oopsies");
//				    	ee.printStackTrace();
//				    }
//				  }
//			});
//			
//			frame.add(b);
//			frame.pack();
//			
//			UThread t = new UThread(client, commands);
//			t.start();
//			
//			System.out.println("gg");
//			
////			while(true) {
////		    	String cmd = commands.poll();
////		    	if (cmd != null) {
////		    		System.out.println(cmd);
////		    		
////		    	}
////		    	try {
////					Thread.sleep(20);
////				} catch (InterruptedException e1) {
////					// TODO Auto-generated catch block
////					e1.printStackTrace();
////				}
////		    	frame.repaint();
////		    }
//			
//			
//		});
//	   
//	}
//	
//	
//	
//	
//}