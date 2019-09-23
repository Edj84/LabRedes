package client;

// Envia o pacote contendo um arquivo ao servidor

import java.net.*;// DatagramaSocket,InetAddress,DatagramaPacket
import java.io.IOException;

public class UDPClient {
	private String ID;
	private DatagramSocket clientSocket;
	private byte[] ipAddr;
	private InetAddress IPAddress;
	private byte[] sendData;
	private DatagramPacket sendPacket;
	private byte[] receiveData;
	private DatagramPacket receivePacket;
		
	public UDPClient(int port){
		
		ipAddr = new byte[]{(byte) 127, (byte) 0, (byte) 0, (byte) 1};
		try {
			IPAddress = InetAddress.getByAddress(ipAddr);
			clientSocket = new DatagramSocket(port);
		} 
		
		catch (UnknownHostException e) {
			System.out.println("Unable to find server");
		} 
		
		catch (SocketException e) {
			System.out.println("Unable to open socket");
		}
	}
	
	public String getID() {
		return ID;
	}
	
	public void setID(String ID) {
		this.ID = ID;
	}
	
	public void send(String message) throws IOException {
		
		sendData = new byte[1024];
	    sendData = message.getBytes();
	    sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 8080);
	    clientSocket.send(sendPacket);
	    System.out.println("Enviado " + message);
	}
	
	public String receive() {
		
		String serverContent = null;
		
			try {	            		     	
				receiveData = new byte[1024];
				receivePacket = new DatagramPacket(receiveData, receiveData.length);
				clientSocket.receive(receivePacket);
				receiveData = receivePacket.getData();
				serverContent = new String(receiveData);
				System.out.println("Message from server: " + serverContent);
			}
			
			catch (IOException e) {
				System.out.println("Unable to receive message from server");
			}			
		
		return serverContent; 
	}
	
}
