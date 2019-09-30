package controller;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import model.AbstractObject;
import model.Door;
import model.Map;
import model.Player;
import model.Response;
import model.Room;

public final class CommandManager {
	
	private static StringBuilder sb = new StringBuilder();
	private static InetAddress playerIP;
	private static Response response; 
	private static Player player = null;
	private static Room location = null; 
	private static String playerResponseContent;
	private static ArrayList<Player> thirdParties;
	private static String thirdPartiesResponseContent;		
	private static String verb;
	
	
	public static ArrayList<DatagramPacket> process(DatagramPacket receivePacket, ArrayList<String> inBuffer) {
		
		sb.setLength(0);
		response = null;
		player = null;
		playerIP = receivePacket.getAddress();
		location = null; 
		playerResponseContent = null;
		thirdParties = null;
		thirdPartiesResponseContent = null;		
		verb = inBuffer.get(0);
		System.out.println(verb);
		
		if (verb.equals("LOGIN")) {
			player = PlayerManager.login(inBuffer.get(1), playerIP);
    		location = Map.getRandomRoom();
			player.setLocation(location);
			sb.append("Bem vindo, " + player.getID() + "\n");
    		sb.append("Sua localização inicial é: " + location.getColor() + "\n");
    		sb.append(location.getDescription() + "\n");
    		playerResponseContent = sb.toString();
    	}
		
		else {
			
			player = PlayerManager.getPlayerByIPAddress(playerIP);
			location = player.getLocation();
			String direction = null;
			
			switch(verb) {
			
				case "EXAMINAR":
	        		String target = inBuffer.get(1);
	        		direction = inBuffer.get(2);
	        		
	        		switch(target) {
					
					case "SALA": 
						sb.append(location.getDescription());
		    			playerResponseContent = sb.toString();
		    			thirdParties = location.getThirdParties(player);
		    			if(!thirdParties.equals(null))
	        				thirdPartiesResponseContent = player.getID() + " examinou a sala";
		    			break;
				
					case "PORTA":
						direction = inBuffer.get(2);
						Door door = getDoor(location, direction);
						
						if(door == null)
							sb.append("Impossível examinar: essa sala não tem porta na direção " + direction);
						else {
							sb.append(door.getDescription());
							thirdParties = location.getThirdParties(player);
			    			if(!thirdParties.equals(null))
		        				thirdPartiesResponseContent = player.getID() + " examinou a porta " + direction;
						}
						
						playerResponseContent = sb.toString();
						break;
			    		
					default:
						if(location.search(target)) {
							AbstractObject auxItem = location.getItem(target);
							sb.append(auxItem.getDescription());
							thirdParties = location.getThirdParties(player);
			    			if(!thirdParties.equals(null))
		        				thirdPartiesResponseContent = player.getID() + " examinou " + direction;
						}
						
						else
							sb.append("Item " + target + " não encontrado na sala.");
						
						playerResponseContent = sb.toString();
						break;
					}
	        		break;
			
		        case "MOVER":
	            	
	            	direction = inBuffer.get(2);
	            	Door door = getDoor(location, direction);
	            	
	            	if(door.equals(null))
        				sb.append("Impossível mover: essa sala não tem porta na direção " + direction);
        			
        			else {
        				
        				if(door.checkClosed())
        					sb.append("Impossível mover: a porta " + direction + " está fechada");
        				
        				else {
        					Room destination = location.getDestination(direction);
        					location.removePlayer(player);
        					destination.addPlayer(player);
        					sb.append("Você se moveu para o " + direction);
        					sb.append(destination.getDescription());
        					thirdParties = location.getThirdParties(player);
        				}
        			}
        			
        			playerResponseContent = sb.toString();
        			
        			if(!thirdParties.equals(null))
        				thirdPartiesResponseContent = player.getID() + " moveu para o " + direction;
        			break;
	            	
        		case "PEGAR":
        			target = inBuffer.get(1);
        			
        			if(!location.search(target))
        				sb.append("Objeto " + target + " não encontrado na sala");
        			else {	
        				AbstractObject item = location.getItem(target);
        				player.keep(item);
        				location.removeItem(item);
        				sb.append("Você coletou " + item.getDescription());
        				thirdParties = location.getThirdParties(player);        				
        			}
        			
        			playerResponseContent = sb.toString();
        			
        			if(!thirdParties.equals(null))
        				thirdPartiesResponseContent = player.getID() + " pegou o item " + target;
        				        			
	                break;
	            
	            case "LARGAR":
	            	
	            	target = inBuffer.get(1);
        			
        			if(!player.searchBackPack(target)) 
        				sb.append("Objeto " + target + " não encontrado na sala");
        			else {	
        				AbstractObject item = player.getObjectFromBackpack(target);
        				player.drop(item);
        				location.addItem(item);
        				sb.append("Você largou " + item.getDescription());
        				thirdParties = location.getThirdParties(player);				
        			}
        			
        			playerResponseContent = sb.toString();
        			
        			if(!thirdParties.equals(null))
        				thirdPartiesResponseContent = player.getID() + " largou o item " + target;
        					            	
	            	break;
	            
	            case "INVENTORIO":
	            	for(AbstractObject item : player.getBackPack())
	            		sb.append(item.getDescription());
	            	
	            	playerResponseContent = sb.toString();
	            	thirdParties = location.getThirdParties(player);
	            	if(!thirdParties.equals(null))
	            		thirdPartiesResponseContent = player.getID() + " examinou seu inventório";
	            	     	
	            	break;
	            
	            case "USAR": 
	            	break;
	            	
	            case "FALAR":
	            	
	            	for(int i = 1; i < inBuffer.size(); i++) 
	            		sb.append(inBuffer.get(i) + " ");
	            	
	            	String words = sb.toString();
	            	playerResponseContent = "Você disse '" + words + "'";
	            	thirdParties = location.getThirdParties(player);
	            	if(!thirdParties.equals(null))
	            		thirdPartiesResponseContent = player.getID() + "diz '" + words + "'";
	            	
	            	break;
	            	
	            
	            case "COCHICAR":
	            	target = inBuffer.get(inBuffer.size()-1);
	            	Player listener = location.getPlayerByID(target);
	            	
	            	for(int i = 1; i < inBuffer.size()-1; i++) 
	            		sb.append(inBuffer.get(i) + " ");
	            	
	            	words = sb.toString();
	            	String crazyOne = "";
	            	
	            	if(player.equals(listener))
	            		crazyOne = "(Deus tá vendo você andando por aí e resmungando sozinho pelos cantos em labirintos escuros)";
	            	
	            	playerResponseContent = "Você cochichou '" + words + "' para " + listener.getID() + "\n" + crazyOne;
	            	thirdParties = location.getThirdParties(player);
	            	if(!thirdParties.equals(null))
	            		thirdPartiesResponseContent = player.getID() + "diz '" + words + "'";
	            	
	            	break;
	            	
	            case "AJUDA":
	            	break;
	                            
	            default:
	            	playerResponseContent = "Ação não pode ser reconhecida. "
	            							+ "\nTente de novo e/ou digite AJUDA se precisar de instruções";
	            	break;
	        	} 
		}
		
		if(thirdPartiesResponseContent.equals(null))
			response = new Response(player, playerResponseContent);
		else
			response = new Response(player, playerResponseContent, thirdParties, thirdPartiesResponseContent);
		
		return ResponseManager.packResponses(response);
		
     }
	
	private static Door getDoor(Room location, String direction) {
		Door[] doors = new Door[4];
		
		doors = location.getDoors();
		
		int target = -1;
		
		switch(direction) {
			case "NORTE":
				target = 0;
				break;
			case "SUL":
				target = 1;
				break;
			case "LESTE":
				target = 2;
				break;
			case "OESTE":
				target = 3;
				break;
			default:
				break;
		}
		
		Door door = null;
		
		if(target > -1 && !doors[target].equals(null))
			door = doors[target];
		
		return door;
	}

	
}




    
