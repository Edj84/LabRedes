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
	private byte[] ipAddr;
	private InetAddress IPAddress; 
	private byte[] sendData;
	
    public void receiveMessage() {
       
    }

    public void sendMessage() {
    	sendData = new byte[1024];
    }
    
   
	
   public static void main(String args[]) throws Exception {
      

      // obtem endereço IP do servidor com o DNS
      
        
      // cria pacote com o dado, o endereço do server e porta do servidor
      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 8080);

      //envia o pacote
      clientSocket.send(sendPacket);

      // fecha o cliente
      clientSocket.close();
   }
   
   public void run() {
		
		try{
			clientSocket = new DatagramSocket(8080);
			InetAddress.getByAddress(ipAddr);
			running = true;
		
			while(running)
				receiveMessage();
			
			clientSocket.close();
		}
		
		catch(SocketException e){
				System.out.println("Unable to create server socket");
		}
					
	}
}
