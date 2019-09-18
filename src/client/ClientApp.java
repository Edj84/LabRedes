package client;
import java.util.Scanner;
import common.Commands;

import controller.CommandManager;

public class ClientApp{
	private static UDPClient client;
	private static Scanner scan;
	private static View view;
    private static boolean running;
    private static Commands commands;
        
    public static void main(String args[]){
        client = new UDPClient();
        scan = new Scanner(System.in);
        view = new View();
        running = true;
        run();
    }

	private static void run() {
		
		client.connect();
		String message = null;
		
		while(running) {
			message = client.receiveMessage();
			
			if(message != null) {
				
				switch(message){
					
		            case "PLAY":
		            	//view.displayMessage(player.getStatus());
		            	view.displayMessage("É a sua vez de jogar. O que deseja fazer?");
		            	String command = null;
		            	
		            	while(command == null) {
		            			command = scan.next();
		            			
		            			if(!commands.contains(command)) {
		            				view.displayMessage("Comando desconhecido");
		            				command = null;
		            			}
		            			
		            			else
		            				client.sendMessage(command);
		            			
		            	}
		            	
		                break;
		            
		            case "ERROR":
	
		                break;
	
		            case "INFO":
	
		                break;
		            
		            case "SPEAK":
		            
		            case "WHISPER": 
		            
		            case "MAP": 
		            	break;
		            
		            default:

	        }
				
		}
		
		client.endGame(message);
		
	}
		
	}
    
}