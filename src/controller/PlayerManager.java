package controller;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Optional;
import model.Player;


public final class PlayerManager {
	
	private static ArrayList<Player> players = new ArrayList<Player>();
		
	public static boolean loginExists(String login){
		
		return players.stream()
				.anyMatch(p -> p.getId().equals(login));
	}
	
	public static Player createLogin(String login, InetAddress IPAdress) {
		
		Player newPlayer = new Player(login, IPAdress);
		
		return newPlayer;
	}
	
	public static void login(String login, InetAddress IPAddress) {
		Player aux = null;
		
		if(loginExists(login)) {
			aux = players.stream()
			.filter(p -> p.getId().equals(login))
			.findFirst().get();
			
			aux.setIPAddress(IPAddress);
			
		}
			
		
		else {			
			aux = new Player(login, IPAddress);
			players.add(aux);		
		}
		
	}

	public static Player getPlayerByID(String ID) {
		
		if(loginExists(ID))
			players.stream()
			.filter(p -> p.getId().equals(login))
			.findFirst().get();
	}
		
}
