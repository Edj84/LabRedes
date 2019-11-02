package model;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;


public class UDPSocket { 
	
	
	private static DatagramSocket socket;
	  
	public UDPSocket(int port){
		
		try {
			socket = new DatagramSocket(port);
						
		} catch (SocketException e) {
			System.out.println("ERROR: Unable to create socket");
		}
	}
	
	
	
	
	private static void send(DatagramPacket packet) throws IOException {
		socket.send(packet);		
	}
	
	private static void receive() throws IOException {
		byte[] receiveData = new byte[1024]; 
		DatagramPacket receivePackage = new DatagramPacket(receiveData,receiveData.length);
		socket.receive(receivePackage); 
		String content = (new String(receiveData)).trim();		
	} 
	
			
} 
