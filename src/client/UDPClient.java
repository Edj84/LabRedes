package client;

// Envia o pacote contendo um arquivo ao servidor

import java.net.*;// DatagramaSocket,InetAddress,DatagramaPacket
import java.io.IOException;

public class UDPClient {
	
	private static String ID;
	private static byte[] ipAddr;
	private static InetAddress IPAddress;
	
	private static DatagramSocket inSocket;
	private static DatagramPacket receivePacket;
	private static byte[] receiveData;
	
	private static DatagramSocket outSocket;
	private static DatagramPacket sendPacket;
	private static byte[] sendData;
	
	private static View userInterface;
	private static boolean running;
		
	public UDPClient(){
		
		ipAddr = new byte[]{(byte) 127, (byte) 0, (byte) 0, (byte) 1};
		running = false;
	}
	
	public static void main(String args[]) {
		
		UDPClient client = new UDPClient();
		
		try {
			IPAddress = InetAddress.getByAddress(ipAddr);
			inSocket = new DatagramSocket(6060);
			outSocket = new DatagramSocket(7070);
			if(IPAddress != null)
				System.out.println("IP OK");
			if(inSocket != null)
				System.out.println("IN OK");
			if(outSocket != null)
				System.out.println("OUT OK");
				
		} 
		
		catch (UnknownHostException e) {
			System.out.println("Unable to find server");
		} 
		
		catch (SocketException e) {
			System.out.println("Unable to open socket");
		}
		
		
		init();
		
		new Thread(send).start();
		new Thread(receive).start();
		
		while(true) {
			send.run();
			receive.run();
		}
	}
	
	public String getID() {
		return ID;
	}
	
	public void setID(String ID) {
		this.ID = ID;
	}
	
	private static void init() {
		userInterface = new View();
		userInterface.welcome();
	}
	
	private static Runnable send = new Runnable() {
		
		public void run() {
						
			if(!running) {				
				
				String command = userInterface.login();
						
				if(command != null)
					running = true;
				
				else 
		        	command = userInterface.prompt();           		
		        	
				try {
					sendData = new byte[4096];
				    sendData = command.getBytes();
				    System.out.println(sendData.length);
				    sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 8080);
				    outSocket.send(sendPacket);
				} 
				catch (IOException e) {
					System.out.println("Unable to send message to server");
				}		        
		    } 
			
		}
	};
	
	private static Runnable receive = new Runnable() {
		
		public void run() {
			
			try {
				receiveData = new byte[256];
				receivePacket = new DatagramPacket(receiveData, receiveData.length);
				inSocket.receive(receivePacket);
				receiveData = receivePacket.getData();
				String serverContent = new String(receiveData);
				System.out.println("Message from server:\n" + serverContent);
			} 
			
			catch (IOException e) {
				System.out.println("Unable to receive message from server");
			}
		}
	};
		
}
