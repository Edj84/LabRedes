package client;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.sound.midi.Receiver;

public class Main {
	
	private static UDPClient player;
	
	public static void main(String[] args) throws IOException {
		criarParticipante();
		
		int n = 1;
		while(true) { 
			sendCommand("Comando " + n++);
			receiveMessage();
		}
				
	}
	
	private static void criarParticipante() {
		
			int id = randomGenerator(100,1000);
			player = new UDPClient(id);				
	}
	
	private static void sendCommand(String command) throws IOException {
		
		player.sendCommand(command);		
	}
	
	private static void receiveMessage() {
		System.out.println(player.receiveMessage());
	}
	
	private static int randomGenerator(int min, int max){
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(max-min) + min;
		return randomInt;
	}
	
}
