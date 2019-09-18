package controller;
import java.net.DatagramPacket;
import java.util.ArrayList;
import common.*;

public class CommandManager {
	
	
    public CommandManager(){
    	 
    }
    	
	public void serverProcess(String command) {

        switch(command){

            case "EXAMINAR":

                break;
            
            case "MOVER":

                break;

            case "PEGAR":

                break;
            
            case "LARGAR":
            
            case "INVENTORIO": 
            
            case "USAR": 
            
            case "FALAR": 
            
            case "COCHICAR": 
            
            case "AJUDA":

                break;
            
            default:

        }

     }
	
	public void clientProcess(String command) {

        switch(command){

            case "PLAY":

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


}




    
