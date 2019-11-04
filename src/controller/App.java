package controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.ArrayList;
import controller.MessageManager;

import model.Node;

public class App{
    private static Node node;
    private static byte[] receiveData;
    private static DatagramPacket receivePacket;
    
    public static void main(String[] args){
        node = new Node();
       
        Thread createMsg = new Thread(new Runnable() { 
			@Override
			public void run() { 
				while (true) { 
					synchronized (this) { 
						try {
							Thread.sleep(10000);
							node.generateMessage();		
						} 
						catch (InterruptedException e) {
							System.out.println("ERROR: I won't say anything untill I see a lawyer");
						}
										
					} 
				} 
			} 
		});  
        
       Thread send = new Thread(new Runnable() { 
			@Override
			public void run() { 
				while (true) { 
					synchronized (this) { 
						
						node.sendFromQueue(); 
						
					} 
				} 
			} 
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
						
						if(node.isTokenManager()) {
							
							try {
								Thread.sleep(5000);
								node.timeoutRoutine();
							} 
							
							catch (InterruptedException e) {
								System.out.println("ERROR: Thread refuses to sleep. Maybe a song and some hot milk might help");
							}
								
						}
						
						else {
							node.tokenScramble();
						}
						
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