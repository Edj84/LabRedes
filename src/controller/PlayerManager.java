package controller;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import model.Map;
import model.Player;
import model.Room;


public final class PlayerManager {
	
	private static ArrayList<Player> players = new ArrayList<Player>();
		
	public static Player getPlayerByID(String login) {
		
		Player aux = players.stream()
			.filter(p -> p.getId().equals(login))
			.findFirst().get();
		
		if(aux != null)
			return aux;
	
		else 
			return null;
	}	
	
	public static ArrayList<Player> getPlayersInRoom(Room location){
		return (ArrayList<Player>) players.stream()
				.filter(p -> p.getLocation() == location)
				.collect(Collectors.toList());
	}
		
	public static Player login(String login, InetAddress IPAddress) {
		
		Player aux = new Player(login, IPAddress);
		players.add(aux);
		return aux;		
	}
	
	public static void logout(String login) {
		
		Player aux = getPlayerByID(login);
		players.remove(aux);
	}
	
}
