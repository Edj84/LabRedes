package controller;

import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.HashMap;

import model.AbstractObject;
import model.Map;
import model.Player;
import model.Response;
import model.Room;

public final class CommandManager {
	
	public static Response process(Player player, String command, Map map) {
		
		Response response = null;
				
		String[] words = command.split("\\s");
		
		for(String w : words)
				System.out.println("Word is " + w);
		
		switch(words[0]) {
		
			case "LOGIN":
        		PlayerManager.login(player.getId(), player.getIPAddress(), map);
        		response = (new Response("Bem vindo, " + player.getId()));
        		break;
        	
        	case "EXAMINAR":
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
                            
            default:
            	break;
        	}
         
        return response;

     }

	private static Response say(Player player, String command) {
		Room location = player.getLocation();
		ArrayList<Player> thirdParties = PlayerManager.getPlayersInRoom(location);
		
		return new Response("Você disse " + command + " em voz alta.", thirdParties, player.getId() + " disse '" + command + "' em voz alta.");		 
	}

	private static void exam(Player player, AbstractObject obj) {
		
		
	}

	private static void move(Player player, String command) {
		
		
	}
	
	
}




    
