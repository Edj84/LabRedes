package controller;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.ArrayList;
import model.AbstractObject;
import model.Door;
import model.Map;
import model.Player;
import model.Response;
import model.Room;
import model.Key;

public final class CommandManager {
	
	private static StringBuilder sb = new StringBuilder();
	private static InetAddress playerIP;
	private static Response response; 
	private static PlayerManager playerManager;
	private static Player player = null;
	private static Room location = null; 
	private static String playerResponseContent;
	private static ArrayList<Player> thirdParties;
	private static String thirdPartiesResponseContent;	
	private static ArrayList<String> inBuffer;
	private static String verb;
	private static Map map;
	
	public CommandManager() {
		playerManager = new PlayerManager();
		map = new Map();
		thirdParties = new ArrayList<Player>();
	}
	
	public static ArrayList<DatagramPacket> process(DatagramPacket receivePacket) throws NullPointerException {
		
		sb.setLength(0);		
		response = null;
		player = null;
		playerIP = receivePacket.getAddress();
		location = null; 
		playerResponseContent = "";
		thirdPartiesResponseContent = "";		
		splitPackage(receivePacket);
		verb = inBuffer.get(0);
		System.out.println(verb);
		
		if (verb.equals("LOGIN")) {
			System.out.println(inBuffer.get(1));
			System.out.println(playerIP);
			player = PlayerManager.login(inBuffer.get(1), playerIP);
    		location = Map.getRandomRoom();
			location.addPlayer(player);
    		player.setLocation(location);
			sb.append("Bem vindo, " + player.getID() + "\n");
    		sb.append("Sua localização inicial é no quarto " + location.getColor() + "\n");
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
	        		System.out.println(target);
	        		
	        		switch(target) {
					
					case "SALA": 
						sb.append(location.getDescription());
		    			playerResponseContent = sb.toString();
		    			thirdParties = location.getThirdParties(player);
		    			if(!thirdParties.isEmpty())
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
			    			if(!thirdParties.isEmpty())
		        				thirdPartiesResponseContent = player.getID() + " examinou a porta " + direction;
						}
						
						playerResponseContent = "A porta ao " + direction + " parece ser feita de metais " + sb.toString();
						break;
			    		
					default:
						if(location.search(target)) {
							AbstractObject auxItem = location.getItem(target);
							sb.append(auxItem.getDescription());
							thirdParties = location.getThirdParties(player);
							if(!thirdParties.isEmpty())
		        				thirdPartiesResponseContent = player.getID() + " examinou " + direction;
						}
						
						else
							sb.append("Item " + target + " não encontrado na sala.");
						
						playerResponseContent = sb.toString();
						break;
					}
	        		break;
			
		        case "MOVER":
	            	
	            	direction = inBuffer.get(1);
	            	Door door = getDoor(location, direction);
	            	
	            	if(door == null)
        				sb.append("Impossível mover: essa sala não tem porta na direção " + direction);
        			
        			else {
        				
        				if(door.checkClosed())
        					sb.append("Impossível mover: a porta " + direction + " está fechada");
        				
        				else {
        					Room destination = location.getDestination(direction);
        					location.removePlayer(player);
        					destination.addPlayer(player);
							player.setLocation(destination);
							sb.append("Você se moveu para o " + direction + "\n");
							sb.append(destination.getDescription());
							if(destination.equals(Map.getMonsterRoom())){
								sb.append("Você venceu");
							}
        					thirdParties = location.getThirdParties(player);
        				}
        			}
        			
        			playerResponseContent = sb.toString();
        			
        			if(!thirdParties.isEmpty())
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
        			
        			if(!thirdParties.isEmpty())
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
        			
        			if(!thirdParties.isEmpty())
        				thirdPartiesResponseContent = player.getID() + " largou o item " + target;
        					            	
	            	break;
	            
	            case "INVENTORIO":
	            	
	            	ArrayList<AbstractObject> backPack = player.getBackPack();
	            	
	            	if(backPack.isEmpty())
	            		sb.append("Sua mochila está vazia");
	            	else {
	            		for(AbstractObject item : backPack)
	            			sb.append(item.getDescription());
	            	}
	            	
	            	playerResponseContent = sb.toString();
	            	thirdParties = location.getThirdParties(player);
	            	if(!thirdParties.isEmpty())
	            		thirdPartiesResponseContent = player.getID() + " examinou a mochila";
	            	     	
	            	break;
	            
				case "USAR": 
					String key = inBuffer.get(1);
					direction = inBuffer.get(2);
					Door doorr = getDoor(location, direction);						
					if(doorr == null)
						sb.append("Impossível usar: essa sala não tem porta na direção " + direction);
					else {
						thirdParties = location.getThirdParties(player);
						if(!thirdParties.isEmpty()){
							thirdPartiesResponseContent = player.getID() + " usou a chave na porta " + direction;
						}
						Key keys = doorr.getRequirement();
						if(!player.searchBackPack(keys.getDescription())) 
        					sb.append("Voce nao possui a chave necessaria para esta porta");
						else {	
							AbstractObject item = player.getObjectFromBackpack(keys.getDescription());
							doorr.useKey(keys);
							sb.append("Você abriu a porta");
							thirdParties = location.getThirdParties(player);				
						}
					}
					
					playerResponseContent = sb.toString();
	            	break;
	            	
	            case "FALAR":
	            	
	            	for(int i = 1; i < inBuffer.size(); i++) 
	            		sb.append(inBuffer.get(i) + " ");
	            	
	            	String words = sb.toString();
	            	playerResponseContent = "Você disse '" + words + "'";
	            	thirdParties = location.getThirdParties(player);
	            	if(!thirdParties.isEmpty())
	            		thirdPartiesResponseContent = player.getID() + "diz '" + words + "'";
	            	
	            	break;
	            	
	            
	            case "COCHICAR":
					target = inBuffer.remove(inBuffer.size()-1);
					
	            	Player listener = location.getPlayerByID(target);
	            	
	            	for(int i = 1; i < inBuffer.size(); i++) 
	            		sb.append(inBuffer.get(i) + " ");
	            	
	            	words = sb.toString();
	            	String crazyOne = "";
	            	
	            	if(player.equals(listener))
	            		crazyOne = "(E Deus tá vendo você andando por aí e resmungando sozinho pelos cantos em labirintos escuros)";
	            	
	            	playerResponseContent = "Você cochichou '" + words + "' para " + listener.getID() + "\n" + crazyOne;
	            	thirdParties = location.getThirdParties(player);
	            	if(!thirdParties.isEmpty())
	            		thirdPartiesResponseContent = player.getID() + " diz '" + words + "'";
	            	
	            	break;
	            	
	            case "AJUDA":
	            	break;
	                            
	            default:
	            	playerResponseContent = "Ação não pode ser reconhecida. "
	            							+ "\nTente de novo e/ou digite AJUDA se precisar de instruções";
	            	break;
	        	} 
		}
		
		System.out.println(playerResponseContent);
		
		if(thirdPartiesResponseContent.equals(""))
			response = new Response(player, playerResponseContent);
		else {
			System.out.println(playerResponseContent);
			response = new Response(player, playerResponseContent, thirdParties, thirdPartiesResponseContent);
		}
		
		return ResponseManager.packResponses(response);
		
     }
	
	private static void splitPackage(DatagramPacket receivePackage) {
		
		byte[] data = new byte[1024]; 
		data = receivePackage.getData();
		String clientMessage = cleanBlankSpaces(data);		
		String[] command = new String(clientMessage).split("\\s");
		
		inBuffer = new ArrayList<String>();
		
		for(String s : command)
			inBuffer.add(s);
		
	}
	
	private static String cleanBlankSpaces(byte[] data) {
		return new String(data).trim();		
	}
	
	private static Door getDoor(Room location, String direction) {
		Door[] doors = new Door[4];
		
		doors = location.getDoors();
		
		int target = -1;
		
		switch(direction.toUpperCase()) {
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
		
		if(target > -1 && doors[target] != null)
			door = doors[target];
		
		return door;
	}

	
}




    
