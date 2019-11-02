package controller;

import java.io.IOException;
import java.net.DatagramPacket;

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
						
						node.send();
					} 
				} 
			//} 
		}); 

		Thread receive = new Thread(new Runnable() { 
			@Override
			public void run() { 
				while (true) { 
					synchronized (this) { 
						
						System.out.println("Im listening");
						node.receive();		 
					} 
				} 
			} 
		}); 

		send.start(); 
		receive.start();
		
		while(true) {
			send.run();
			receive.run();
		}
		
	} 
	
}