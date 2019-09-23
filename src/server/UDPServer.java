package server;


import java.io.IOException;
import java.net.*;

import controller.*;
import model.Player;

public class UDPServer {
	
	//Gerenciador de players
	private static InetAddress clientIPAddress;
	
	private static DatagramSocket serverSocket;
	private static byte[] sendData;
	private static DatagramPacket sendPacket;
	private static byte[] receiveData;
	private static DatagramPacket receivePacket;
	private static Player player;
	private static String command;
	
	private static boolean running;
	
	public static void main(String args[]) throws Exception {
		
		running = true;
		System.out.println("Let the game begin!");
		serverSocket = new DatagramSocket(8080);        
    	
		while(running) 
			listen();
	}
			
	private static void listen() {
		
		receiveData = new byte[1024];
		try {	            		     	
	    	receivePacket = new DatagramPacket(receiveData, receiveData.length);
	    	serverSocket.receive(receivePacket);            	    
	    	clientIPAddress = receivePacket.getAddress();
	    	splitPackage(receivePacket);
	    	running = CommandManager.process(player, command);
	    	
	    	if(running)
	    		sendMessage("RESPOSTA AO CLIENTE");	    		
    	}
    
        catch(SocketException e) {
			System.out.println("Game over!");
		} 
		
		catch (IOException e) {
			System.out.println("Unable to get package from client");
		}
	}
	
	private static void splitPackage(DatagramPacket receivePacket) {
		byte[] data = receivePacket.getData();
		String[] words = new String(data).split("#");
		playerID = words[0];
		command = words[1];		
	}

	private static void sendMessage(String message) {
		sendData = new byte[1024];
	    sendData = message.getBytes();
	    
	    try {
	    	
	    	sendPacket = new DatagramPacket(sendData, sendData.length, clientIPAddress, 9090);
			serverSocket.send(sendPacket);
		} 
	    catch (IOException e) {
			System.out.println("Unable to send response to client");
		}
	}
	
}
    	
    