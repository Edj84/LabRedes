package client;

// Envia o pacote contendo um arquivo ao servidor

import java.io.*; // classes para input e output streams e
import java.net.*;// DatagramaSocket,InetAddress,DatagramaPacket
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import common.Communicates;

public class UDPClient implements Communicates{
	
	private DatagramSocket clientSocket; 
	private boolean running;
	private InetAddress IPAddress;
	private byte[] ipAddr;
	private byte[] sendData;
	private byte[] receivedData;
	private static DatagramPacket receivedPacket;
	private static DatagramPacket sendPacket;
	
    public String receiveMessage() {
    	
    	try {
			receivedData = new byte[1024];
			receivedPacket = new DatagramPacket(receivedData, receivedData.length);
			clientSocket.receive(receivedPacket);
			String message = new String(receivedPacket.getData(),0,receivedPacket.getLength());
			
			return message;		
		} 
        
        catch (IOException e) {
			System.out.println("Unable to receive package from client");
			return null;
		}
        
	}
    
    public void sendMessage(String message) {
    	 	
    	
    	try {
    		sendData = new byte[2048];
    		sendData = message.getBytes();
        	sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 8080);
    		clientSocket.send(sendPacket);
		
	    } catch (IOException e) {
			System.out.println("Failed to send message");
		}
    	
    }
       
    public void connect() {
		
		try{
			clientSocket = new DatagramSocket(8080);
			ipAddr = new byte[]{(byte) 10, (byte) 32, (byte) 162, (byte) 173};
		    IPAddress = InetAddress.getByAddress(ipAddr);			
		 }
		
		 catch(SocketException e){
			System.out.println("Unable to connect to server");	
		 }
		
		 catch(UnknownHostException e){
			System.out.println("Unable to find server");
		 }			
	}
    
    public void endGame(String message) {
    	clientSocket.close();
    	System.out.println("Game is over. Thank you for playing!");		
    }

	
    
}
