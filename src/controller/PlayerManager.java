package controller;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;
import model.Player;
import model.Room;


public class PlayerManager {
	
	private static ArrayList<Player> players;
	private static HashMap<InetAddress, Player> ipTable; 
	
	public PlayerManager() {
		players = new ArrayList<Player>();
		ipTable  = new HashMap<InetAddress, Player>(); 
	}
	
	public static Player getPlayerByID(String login) {
		
		Player aux = players.stream()
			.filter(p -> p.getID().equals(login))
			.findFirst().get();
		
		if(aux != null)
			return aux;
	
		else 
			return null;
	}
	
	public static Player getPlayerByIPAddress(InetAddress IPAddress) {
		
		for(InetAddress ip : ipTable.keySet())
			System.out.println("IP " + ip);
		
		System.out.println("IP na tabela " + ipTable.containsKey(IPAddress));
		
		Player player = ipTable.get(IPAddress);
		
		System.out.println("Player " + player.getID());
		return player;	
	}

	public static ArrayList<Player> getPlayersInRoom(Room location){
		return (ArrayList<Player>) players.stream()
				.filter(p -> p.getLocation() == location)
				.collect(Collectors.toList());
	}
		
	public static Player login(String login, InetAddress IPAddress) {
		
		Player player = new Player(login, IPAddress);
		System.out.println(players);
		players.add(player);
		System.out.println("incluí " + player.getID() + " na lista");
		System.out.println("IP jogador " + player.getIPAddress());
		ipTable.put(IPAddress, player);
		
		System.out.println("incluí " + player.getIPAddress() + " na tabela de roteamento");
		return player;		
	}
	
	public static void logout(String login) {
		
		Player aux = getPlayerByID(login);
		players.remove(aux);
	}
	
}
