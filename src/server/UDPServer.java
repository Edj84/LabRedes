package server;
// Recebe um pacote de algum cliente
// Separa o dado, o endere�o IP e a porta deste cliente
// Imprime o dado na tela

import java.io.*;
import java.net.*;
import controller.*;


class UDPServer implements common.Communicates{
	
	private PlayerManager playerManager;
	private CommandManager commandManager;
	private static DatagramSocket serverSocket;
	private static DatagramPacket receivedPacket;
	private static byte[] receivedData;
	boolean running;
	
	public UDPServer() {
		playerManager = new PlayerManager();
		commandManager = new CommandManager();
	}
	
	public void receiveMessage() {
		
        try {
			receivedData = new byte[1024];
			receivedPacket = new DatagramPacket(receivedData, receivedData.length);
			serverSocket.receive(receivedPacket);
			process(receivedPacket);
		} 
        
        catch (IOException e) {
			System.out.println("Unable to receive package from client");
		}
        
	}

	private void process(DatagramPacket packet) {
		commandManager.process(packet);		
	}

	public void sendMessage() {
		String sentence = new String(receivedPacket.getData());
        InetAddress IPAddress = receivedPacket.getAddress();
        int port = receivedPacket.getPort();
		
	}

	public void run() {
		
		try{
			serverSocket = new DatagramSocket(8080);
			running = true;
		
			while(running)
				receiveMessage();
			
			serverSocket.close();
		}
		
		catch(SocketException e){
				System.out.println("Unable to create server socket");
		}
					
	}
   
}
