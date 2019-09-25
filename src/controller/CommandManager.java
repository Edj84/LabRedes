package controller;

import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import model.AbstractObject;
import model.Map;
import model.Player;
import model.Response;
import model.Room;

public final class CommandManager {
	
	public static Response process(DatagramPacket receivePacket, String[] command) {
		
		Response response = null;
		
		String verb = command[0];
		System.out.println(verb);
		
		String params = command[1];
		System.out.println(params);
				
		String[] particles = params.split("\\s");
		
		for(String p : particles)
				System.out.println("Particle is " + p);
		
		StringBuilder sb = new StringBuilder();
		
		boolean sucess = true; 
		
		switch(verb) {
			
			case "LOGIN":
        		
				Player aux = PlayerManager.login(particles[0], receivePacket.getAddress());
        		Room location = Map.getRandomRoom();
				aux.setLocation(location);
				sb.append("Bem vindo, " + aux.getId() + "\n");
        		sb.append("Sua localização inicial é: " + location.getColor() + "\n");
        		sb.append(location.getDescription() + "\n");
        		String playerResponse = sb.toString();
        		response = new Response(playerResponse);        		
        		break;
        	
        	/*case "EXAMINAR":
                break;
            
            case "MOVER":
            	move(player, command);
            	exam(player, player.getLocation());
                break;

            case "PEGAR":
                break;
            
            case "LARGAR":
            	break;
            
            case "INVENTORIO":
            	break;
            
            case "USAR": 
            	break;
            	
            case "FALAR":
            	response = say(player, command);
            	break;
            
            case "COCHICAR": 
            	break;
            case "AJUDA":
            	break;
                        */    
            default:
            	sucess = false;
            	break;
        	}        
		
        return response;

     }

	private static Response say(Player player, String command) {
		Room location = player.getLocation();
		ArrayList<Player> thirdParties = PlayerManager.getPlayersInRoom(location);
		
		return new Response("Você disse '" + command + "'.", thirdParties, player.getId() + " disse '" + command + "'.");		 
	}

	private static void exam(Player player, AbstractObject obj) {
		
		
	}

	private static void move(Player player, String command) {
		
		
	}
	
	
}




    
