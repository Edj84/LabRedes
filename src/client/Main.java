package client;
import java.io.IOException;
import java.util.concurrent.Semaphore;

public class Main {
	
	private static UDPClient outSocket;
	private static UDPClient inSocket;
	private static View userInterface;
	private static String command = null;
	private static boolean running = false;
	
	public static void main(String[] args) {
		
		outSocket = new UDPClient(9090);
		inSocket = new UDPClient(8080);
		init();
				
		new Thread(send).start();
		new Thread(receive).start();
		
		while(true) {
			send.run();
			receive.run();
		}
	}
	
	private static void init() {
		userInterface = new View();
		userInterface.welcome();
	}
	
		
}
		