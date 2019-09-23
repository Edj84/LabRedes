package controller;

import model.AbstractObject;
import model.Player;
import model.Room;

public final class CommandManager {
	
	public static void process(Player player, String command) {

        switch(command.toUpperCase()){

        	case "LOGIN":
        		
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
            
            case "INVENTORIO": 
            
            case "USAR": 
            
            case "FALAR":
            	say(player, command);
            	break;
            
            case "COCHICAR": 
            
            case "AJUDA":

                break;
            
            default:

        }

     }

	private static void say(Player player, String command) {
				
	}

	private static void exam(Player player, AbstractObject obj) {
		
		
	}

	private static void move(Player player, String command) {
		
		
	}
	
}




    
