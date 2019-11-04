package controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.ArrayList;

import model.Node3;

public class App3{
    private static Node3 node;
    private static byte[] receiveData;
    private static DatagramPacket receivePacket;
    
    public static void main(String[] args){
        node = new Node3();
        
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