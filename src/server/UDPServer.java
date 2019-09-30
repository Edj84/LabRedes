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
	
	private static DatagramSocket inSocket;
	private static DatagramPacket receivePacket;
	private static byte[] receiveData;
	
	private static DatagramSocket outSocket;
	private static DatagramPacket sendPacket;
	private static byte[] sendData;
	
	private static Map map;
	private static ArrayList<String> inBuffer;
	private static ArrayList<String> outBuffer;
	
	private static boolean running;
	
	public static void main(String args[]) throws Exception {
		
		createWorld();
		running = true;
		System.out.println("Let the game begin!");
		inSocket = new DatagramSocket(8080);
		outSocket = new DatagramSocket(9090);       
		new Thread(receive).start();
				
		while(running) 
			receive.run();
		
	}
			
	private static void createWorld() {		
		map = new Map();
	}

	private static Runnable send = new Runnable() {
		
		public void run() {
						
			try {
				sendData = new byte[1024];
				sendData = command.getBytes();
				sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 8080);
				outSocket.send(sendPacket);
			} 
			
			catch (IOException e) {
				System.out.println("Unable to send message to server");
			}		        
		   
		} 
	
	};
	
	
	private static Runnable receive = new Runnable() {
		
		public void run() {
			
			receiveData = new byte[1024];
			
			try {	            		     	
		    	receivePacket = new DatagramPacket(receiveData, receiveData.length);
		    	inSocket.receive(receivePacket);            	   
		    	inBuffer = new ArrayList<String>();
		    	splitPackage(receivePacket);   	
		    	Response response = process(receivePacket);
		    	
	    	}
	    
	        catch(SocketException e) {
				System.out.println("Game over!");
			} 
			
			catch (IOException e) {
				System.out.println("Unable to get package from client");
			}
		}
	};
	
	private static void splitPackage(DatagramPacket receivePacket) {
		
		byte[] data = receivePacket.getData();
		data = cleanBlankSpaces(data);		
		String[] command = new String(data).split("\\s");
		
		for(String s : command)
			inBuffer.add(s);
		
	}
	
	private static byte[] cleanBlankSpaces(byte[] data) {
		
		return new String(data).trim().getBytes();		
	}

	private static DatagramPacket[] process(DatagramPacket receivePacket) {
		
		DatagramPacket[] serverMessages = CommandManager.process(receivePacket, inBuffer);
		
		return serverMessages;
	}
	
	private static void send(DatagramPacket receivePacket, Response response) {
		
		sendData = new byte[1024];	    
		try {
			clientIPAddress = receivePacket.getAddress();
			sendData = response.getPlayerResponse().getBytes();
	    	sendPacket = new DatagramPacket(sendData, sendData.length, clientIPAddress, 6060);
			inSocket.send(sendPacket);
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
					inSocket.send(sendPacket);
				}
				
			    catch (IOException e) {
					System.out.println("Unable to send response to client");
				}
				
			}
		}
	}
		
}
    	
    