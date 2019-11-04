package model;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;


public class UDPSocket3 { 
	
	private DatagramSocket socket;
	  
	public UDPSocket3(int port){
		
		try {
			socket = new DatagramSocket(port);
						
		} catch (SocketException e) {
			System.out.println("ERROR: Unable to create socket using port " + port);
		}
	}
	
	public void send(DatagramPacket packet) throws IOException {
		socket.send(packet);		
	}
	
	public DatagramPacket receive() throws IOException {
		byte[] receiveData = new byte[1024]; 
		DatagramPacket receivePackage = new DatagramPacket(receiveData,receiveData.length);
		socket.receive(receivePackage);
		return receivePackage;
	}
			
} 
