package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import controller.CommandManager;
import controller.PlayerManager;
import model.Map; 

public class UDPServer { 
	
	private static InetAddress IPAddress;
	private static DatagramSocket serverSocket;
	private static DatagramPacket receivePacket;
	private static byte[] receiveData;
	private static byte[] sendData;
	private static ArrayList<DatagramPacket> outBuffer;
	
	public static void main(String args[]) throws IOException, InterruptedException { 
		
		IPAddress = InetAddress.getLocalHost();
		serverSocket = new DatagramSocket(8080); 
		outBuffer = new ArrayList<DatagramPacket>();
		receiveData = new byte[1024];
		sendData = new byte[1024];
		
		Thread send = new Thread(new Runnable() { 
			@Override
			public void run() { 
				try { 
					
					while (true) { 
						synchronized (this) { 
							
							if(!outBuffer.isEmpty())
								send(outBuffer.remove(0));
							
							String serverMessage = new String(sendData); 
							
							// exit condition 
							if((serverMessage).toUpperCase().equals("PARAR")) { 
								System.out.println("O jogo acabou"); 
								break; 
							}
						} 
					} 
				} 
				catch (IOException e) { 
					System.out.println("Exception occured" + e.getMessage()); 
				} 
			} 
		}); 

		Thread receive = new Thread(new Runnable() { 
			@Override
			public void run() { 
				try { 
					while (true) { 
						synchronized (this) { 
							
							String clientMessage = receive();
							outBuffer = process(receivePacket);
							
							// Exit condition 
							if(clientMessage.toUpperCase().equals("SAIR")) { 
								System.out.println("Cliente encerrou a conexão"); 
								break; 
							} 
						} 
					} 
				} 
				catch (IOException e) {
					System.out.println("Exception occured"); 
				} 
			} 
		}); 

		send.start(); 
		receive.start(); 

		send.join(); 
		receive.join(); 
	} 
	
	private static String receive() throws IOException {
		receiveData = new byte[1024];
		receivePacket = new DatagramPacket(receiveData, receiveData.length); 
		serverSocket.receive(receivePacket);
		String clientMessage = (new String(receiveData)).trim();
		System.out.println("Recebi " + clientMessage);
				
		return clientMessage;
	}
		
	private static ArrayList<DatagramPacket> process(DatagramPacket receivePacket) {
		
		ArrayList<DatagramPacket> serverMessages = CommandManager.process(receivePacket);
		return serverMessages;
	}
	
	private static void send(DatagramPacket sendPacket) throws IOException {
		sendData = sendPacket.getData();
		serverSocket.send(sendPacket);
		String aux = new String(sendData).trim();
		System.out.println("Mandei " + aux);
	}
		
}
    	
    