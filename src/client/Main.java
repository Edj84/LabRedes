package client;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.sound.midi.Receiver;

public class Main {
	
	private static UDPClient clientSocket;
	private static View userInterface;
	private static String command = null;
	private static boolean running = true;
	
	public static void main(String[] args) throws IOException {
		clientSocket = new UDPClient(9090);
		init();
		login();
		
		new Thread(send).start();
		new Thread(receive).start();	
		
		while(true) {
			send.run();
			receive.run();
		}
	}
	
	private static Runnable send = new Runnable() {
        public void run() {
            try{
            	command = userInterface.prompt();
            	send(command);		
            }
            
            catch (Exception e){
            	System.out.println("Unable to run send thread");
            }
           
        }
	};
        
     private static Runnable receive = new Runnable() {
    	 public void run() {
    		 try{
    			 receiveMessage();		
             }
                
             catch (Exception e){
            	 System.out.println("Unable to run receive thread");
             }
               
        }
     };
	
	private static void init() {
		userInterface = new View();
		userInterface.welcome();
	}
	
	private static void login() throws IOException {
		String login = userInterface.login();
		send(login);
		receiveMessage();
	}

	private static void receiveMessage() {
		System.out.println(clientSocket.receive());			
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
		