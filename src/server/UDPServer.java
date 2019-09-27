package server;


import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Random;

import controller.*;
import model.Map;
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
	private static Map map;
	
	private static boolean running;
	
	public static void main(String args[]) throws Exception {
		
		createWorld();
		running = true;
		System.out.println("Let the game begin!");
		serverSocket = new DatagramSocket(8080);        
				
		while(running) 
			listen();
		
	}
			
	private static void createWorld() {		
		map = new Map();
	}

	private static void listen() {
		
		receiveData = new byte[256];
		
		try {	            		     	
	    	DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
	    	serverSocket.receive(receivePacket);            	   
	    	String[] command = splitPackage(receivePacket);   	
	    	Response response = process(receivePacket, command);
	    	
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
		String text = new String(data);
		String[] command = new String(data).split("\\s");
				
		return command;		
	}
	
	private static byte[] cleanBlankSpaces(byte[] data) {
		
		return new String(data).trim().getBytes();		
	}

	private static Response process(DatagramPacket receivePacket, String[] message) {
		
		Response response = CommandManager.process(receivePacket, message);
		
		return response;
	}
	
	private static void send(DatagramPacket receivePacket, Response response) {
		
		sendData = new byte[256];	    
		try {
			clientIPAddress = receivePacket.getAddress();
			sendData = response.getPlayerResponse().getBytes();
	    	sendPacket = new DatagramPacket(sendData, sendData.length, clientIPAddress, 6060);
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
			    	sendPacket = new DatagramPacket(sendData, sendData.length, clientIPAddress, 6060);
					serverSocket.send(sendPacket);
				}
				
			    catch (IOException e) {
					System.out.println("Unable to send response to client");
				}
				
			}
		}
	}
		
}
    	
    