package server;
// Recebe um pacote de algum cliente
// Separa o dado, o endere�o IP e a porta deste cliente
// Imprime o dado na tela

import java.io.*;
import java.net.*;
import java.util.HashMap;

class UDPServer implements common.Communicates{
	private PlayerManager playerManager;
	private static DatagramSocket serverSocket;
	private static DatagramPacket receivedPacket;
	private static byte[] receiveData;
	
	public static void main(String args[]) throws Exception{
		serverSocket = new DatagramSocket(8080);
	       
	    while(true) {
	    	receiveMessage();           
       }
   }
	
	public void receiveMessage() {
		
        try {
			serverSocket.receive(receivedPacket);
			receiveData = new byte[1024];
			receivedPacket = new DatagramPacket(receiveData, receiveData.length);
		} 
        
        catch (IOException e) {
			System.out.println("Unable to receive package from client");
		}

        process();
	}

	private void process() {
		// TODO Auto-generated method stub
		
	}

	public void sendMessage() {
		String sentence = new String(receivedPacket.getData());
        InetAddress IPAddress = receivedPacket.getAddress();
        int port = receivedPacket.getPort();
		
	}

	
   
}
