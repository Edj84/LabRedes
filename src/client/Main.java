package client;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.sound.midi.Receiver;

public class Main {
	
	private static UDPClient player;
	private static View userInterface;
	
	public static void main(String[] args) throws IOException {
		init();
		
		int n = 1;
		
		while(true) { 
			sendCommand("Comando " + n++);
			receiveMessage();
		}
				
	}
	
	private static void init() {
		userInterface = new View();
		userInterface.welcome();
	
	}
	
	private static void createLogin() throws IOException {
		String login = userInterface.login();
		sendCommand(login);
		player = new UDPClient(login);				
	}
	
	private static void sendCommand(String command) throws IOException {
		player.sendCommand(command);		
	}
	
	private static void receiveMessage() {
		System.out.println(player.receiveMessage());
	}
	
	/*private static int randomGenerator(int min, int max){
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(max-min) + min;
		return randomInt;
	}
	*/
}
