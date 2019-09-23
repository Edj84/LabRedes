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
		
		if(loginExists(login)) {
			System.out.println("Jogador " + login + " encontrado");
		
			return players.stream()
			.filter(p -> p.getId().equals(login))
			.findFirst().get();
		}
		
		else {
			System.out.println("Jogador " + login + " não encontrado");
			return null;
		}
		
	}	
	
	public static ArrayList<Player> getPlayersInRoom(Room location){
		return (ArrayList<Player>) players.stream()
				.filter(p -> p.getLocation() == location)
				.collect(Collectors.toList());
	}
	
	private static boolean loginExists(String login){
		
		return players.stream()
				.anyMatch(p -> p.getId().equals(login));
	}
	
	public static Player createLogin(String login, InetAddress IPAdress, Map map) {
		System.out.println("Criando login do jogador " + login);
		Player newPlayer = new Player(login, IPAdress, map);		
		return newPlayer;
	}
	
	public static void login(String login, InetAddress IPAddress, Map map) {
		Player aux = null;
		
		if(loginExists(login)) {
			aux = players.stream()
			.filter(p -> p.getId().equals(login))
			.findFirst().get();
			
			aux.setIPAddress(IPAddress);
			
			System.out.println("Jogador " + login + " fez login de novo");
		}			
		
		else {			
			aux = createLogin(login, IPAddress, map);
			players.add(aux);		
		}
		
	}

	
		
}
