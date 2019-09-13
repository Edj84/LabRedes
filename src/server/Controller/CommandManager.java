package server.Controller;

import java.net.DatagramPacket;
import java.util.ArrayList;
import common.*;

public class CommandManager implements Communicates {

    public CommandManager(){
    
    }
    
    enum Command {

        'EXAMINAR', 'MOVER', 'PEGAR', 'LARGAR', 'INVENTORIO', 'USAR', 'FALAR', 'COCHICAR', 'AJUDA'
    
    }

    public void sendMessage() {

    }

    public void receiveMessage() {
        
    }

	public void process(DatagramPacket packet) {

        Command command = Command.getData().toString().split("#");

        switch(command){

            case 'EXAMINAR':

                break;
            
            case 'MOVER':

                break;

            case 'PEGAR':

                break;
            
            case 'LARGAR':
            
            case 'INVENTORIO': 
            
            case 'USAR': 
            
            case 'FALAR': 
            
            case 'COCHICAR': 
            
            case 'AJUDA':

                break;
            
            default:

        }

     }

	}




    

}