package controller;
import java.net.DatagramPacket;
import java.util.ArrayList;
import common.*;

public class CommandManager {
	
	
    public CommandManager(){
    	 
    }
    	
	public void process(DatagramPacket packet) {

        //Ver projArq
		String command = packet.getData().toString();

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

}




    
