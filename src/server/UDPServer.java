package server;


import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

import controller.*;
import model.Player;
import model.Response;

public class UDPServer {
	
	private static InetAddress clientIPAddress;
	private static DatagramSocket serverSocket;
	private static byte[] sendData;
	private static DatagramPacket sendPacket;
	private static byte[] receiveData;
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
	    	DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
	    	serverSocket.receive(receivePacket);            	   
	    	String[] message = splitPackage(receivePacket);   	
	    	Response response = process(receivePacket, message);
	    	
	    	send(receivePacket, response);
	    	
    	}
    
        catch(SocketException e) {
			System.out.println("Game over!");
		} 
		
		catch (IOException e) {
			System.out.println("Unable to get package from client");
		}
	}
	
	private static String[] splitPackage(DatagramPacket receivePacket) {
		
		byte[] data = receivePacket.getData();
		data = cleanBlankSpaces(data);		
		String command = new String(data);
		String[] words = new String(data).split("\\s");
				
		return words;
		
	}
	
	private static byte[] cleanBlankSpaces(byte[] data) {
		
		return new String(data).trim().getBytes();		
	}

	private static Response process(DatagramPacket receivePacket, String[] message) {
	
		String command = message[0];
		System.out.println(command);
		
		String playerID = message[1];
		System.out.println(playerID);
		player = PlayerManager.getPlayerByID(playerID);
		if(player == null) {
			clientIPAddress = receivePacket.getAddress();
			player = PlayerManager.createLogin(playerID, clientIPAddress);
		}	
		
		Response response = CommandManager.process(player, command);
		
		return response;
}
	
	private static void send(DatagramPacket receivePacket, Response response) {
		
		sendData = new byte[1024];	    
		try {
			clientIPAddress = receivePacket.getAddress();
			sendData = response.getPlayerResponse().getBytes();
	    	sendPacket = new DatagramPacket(sendData, sendData.length, clientIPAddress, 9090);
			serverSocket.send(sendPacket);
		} 
	    catch (IOException e) {
			System.out.println("Unable to send response to client");
		}
		
		if(response.getThirdParties() != null) {
			
			for(Player tp : response.getThirdParties()) {
				
				try {
					clientIPAddress = tp.getIPAddress();
					sendData = response.getThirdPartiesResponse().getBytes();
			    	sendPacket = new DatagramPacket(sendData, sendData.length, clientIPAddress, 9090);
					serverSocket.send(sendPacket);
				}
				
			    catch (IOException e) {
					System.out.println("Unable to send response to client");
				}
				
			}
		}
	}
		
}
    	
    