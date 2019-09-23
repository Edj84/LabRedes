package client;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.sound.midi.Receiver;

public class Main2 {
	
	private static UDPClient clientSocket;
	private static View userInterface;
	
	public static void main(String[] args) throws IOException {
		clientSocket = new UDPClient(9091);
		init();
		login();
		
		while(true) { 
			send(userInterface.prompt());
			receiveMessage();
		}
			
	}
	
	private static void init() {
		userInterface = new View();
		userInterface.welcome();
	}
	
	private static void login() throws IOException {
		String login = userInterface.login();
		send(login);
		receiveMessage();
	}
	
	private static void send(String command) throws IOException {
		clientSocket.send(command);		
	}
	
	private static void receiveMessage() {
		System.out.println(clientSocket.receive());
	}
	
}
		