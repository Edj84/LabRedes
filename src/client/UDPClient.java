package client;

// Envia o pacote contendo um arquivo ao servidor

import java.net.*;// DatagramaSocket,InetAddress,DatagramaPacket
import java.io.IOException;
import java.math.BigInteger;

public class UDPClient {
	private int id;
	private DatagramSocket clientSocket;
	private byte[] ipAddr;
	private InetAddress IPAddress;
	private byte[] sendData;
	private DatagramPacket sendPacket;
	private byte[] receiveData;
	private DatagramPacket receivePacket;
	
	public UDPClient(int id){
		this.id = id;
		ipAddr = new byte[]{(byte) 127, (byte) 0, (byte) 0, (byte) 1};
		try {
			IPAddress = InetAddress.getByAddress(ipAddr);
			clientSocket = new DatagramSocket(9090);
		} 
		
		catch (UnknownHostException e) {
			System.out.println("Unable to find server");
		} 
		
		catch (SocketException e) {
			System.out.println("Unable to open socket");
		}
	}
	
	public int getId() {
		return id;
	}
	
	public void sendCommand(String command) throws IOException {
		
	      sendData = new byte[1024];
	      sendData = command.getBytes();
	      sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 8080);
	      clientSocket.send(sendPacket);
	      System.out.println("Enviado " + command);
	}
	
	public String receiveMessage() {
		
		String serverMessage = null;
		
			try {	            		     	
				receiveData = new byte[1024];
				receivePacket = new DatagramPacket(receiveData, receiveData.length);
				clientSocket.receive(receivePacket);
				receiveData = receivePacket.getData();
				serverMessage = new String(receiveData);
				System.out.println("Message from server: " + serverMessage);
			}
			
			catch (IOException e) {
				System.out.println("Unable to receive message from server");
			}			
		
		return serverMessage; 
	}
	
}
