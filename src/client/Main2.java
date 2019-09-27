package client;
import java.io.IOException;
import java.util.concurrent.Semaphore;

public class Main2 {
	
	private static UDPClient clientSocket;
	private static View userInterface;
	private static String command = null;
	private static boolean running = false;
	
	public static void main(String[] args) {
		clientSocket = new UDPClient(9090);
		init();
				
		new Thread(MyConnection).start();
		
		while(true)
			MyConnection.run();		
	}
	
	private static void init() {
		userInterface = new View();
		userInterface.welcome();
	}
	
	private static Runnable MyConnection = new Runnable() {
		
		Semaphore semaphore = new Semaphore(1);
		
		public void run() {

			try {

				System.out.println("Tentando conectar...");
				semaphore.acquire();
				System.out.println("Conectado");

				if(!running) {
		        		
					try {
						command = login();
						
						if(command != null)
							running = true;
					}
		        		
		        	catch (IOException e) {
		        		System.out.println("Unable to read login");
					}
		        }
		        	
		        else 
		        	command = userInterface.prompt();           		
		        	
				send(command);
		        receiveMessage();
		        
		    } 
			
			catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		         
			finally {
				semaphore.release();
			}
		}
	};

	private static String login() throws IOException {
		return userInterface.login();		
	}

	private static void receiveMessage() {
		System.out.println("Mensagem do servidor:\n" + clientSocket.receive());
		
	}

	private static void send(String login) {
		try {
			clientSocket.send(login);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}		
	}
			
}
		