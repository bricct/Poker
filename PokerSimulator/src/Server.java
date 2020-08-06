import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.logging.Logger;

public class Server implements Connectable {
	public Server() throws IOException {
		this(DEFAULT_PORT);
	}
	
	public Server(int port) throws IOException {
		this.log = Logger.getLogger("global");
		
		this.port = port;
		this.servSocket = new ServerSocket(this.port);
		log.info(String.format("Server socket was created on port %d.\n", port));
	}
	
	@Override
	public boolean hasMessage() {
		return this.in.hasNextLine();
	}
	
	@Override
	public void connect() throws IOException {
		this.socket = this.servSocket.accept();
		log.info(String.format("Incoming connection from a client at %s accepted.\n", this.socket.getRemoteSocketAddress().toString()));
		this.inStream =  this.socket.getInputStream();
		this.outStream = this.socket.getOutputStream();
		this.in = new Scanner(this.inStream);
		this.out = new PrintWriter(new OutputStreamWriter(this.outStream, StandardCharsets.UTF_8), true /*autoFlush */);		
	}
	
	@Override
	public void send(String message) {
		this.out.println(message);
		log.info(String.format("Message %s sent.\n", message));
	}
	
	@Override
	public String receive() {
		String message = this.in.nextLine();
		log.info(String.format("Message %s received.\n", message));
		return message;
	}
	
	public int getPort() {
		return this.port;
	}

	public static final int DEFAULT_PORT = 8189;
	
	private int port;
	private Socket socket;
	private ServerSocket servSocket;
	private InputStream inStream;
	private OutputStream outStream;
	Scanner in;
	PrintWriter out;
	private Logger log;
}
