package controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.ArrayList;

import model.Node;

public class App{
    private static Node node;
    private static byte[] receiveData;
    private static DatagramPacket receivePacket;
    
    public static void main(String[] args){
        node = new Node();
        
        Thread send = new Thread(new Runnable() { 
			@Override
			public void run() { 
				//while (true) { 
					synchronized (this) { 
						
						node.sendFromQueue(); 
						
					} 
				} 
			//} 
		}); 

		Thread receive = new Thread(new Runnable() { 
			@Override
			public void run() { 
				
				System.out.println("Im listening. My ID is " + node.getID());
				
				while (true) { 
					synchronized (this) { 
						
						String msg = node.receive(); 
						
						if(msg != null)
							System.out.println(msg);							
						
					} 
				} 
			} 
		}); 
		
		Thread manageToken = new Thread(new Runnable() { 
			@Override
			public void run() { 
				
				while (true) { 
					synchronized (this) { 
						
						if(node.isTokenManager())
							node.checkTokenTimeout();							
						
					} 
				} 
			} 
		}); 

		send.start(); 
		receive.start();
		manageToken.start();
		
		while(true) {
			send.run();
			receive.run();
			manageToken.run();
		}
		
	} 
	
}