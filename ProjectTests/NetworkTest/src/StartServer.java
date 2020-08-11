import java.io.IOException;

public class StartServer {
	public static void main(String[] args) {
		
		
		int num_players = 3;
		
		try {
			@SuppressWarnings("unused")
			Server s = new Server(num_players);
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
}
