package server;
// Recebe um pacote de algum cliente
// Separa o dado, o endere�o IP e a porta deste cliente
// Imprime o dado na tela

import java.io.*;
import java.net.*;
import java.util.HashMap;

class UDPServer {
	private PlayerManager playerManager;
	
	public static void main(String args[])  throws Exception{
	   
	   // cria socket do servidor com a porta 9876
       DatagramSocket serverSocket = new DatagramSocket(8080);

       byte[] receiveData = new byte[1024];
       
       while(true) {
    	   System.out.println("Servidor UDP ouvindo... (fala que eu te escuto)");            	
            
           //declara o pacote a ser recebido
           DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

           //recebe o pacote do cliente
           serverSocket.receive(receivePacket);

           //pega os dados, o endereçoo IP e a porta do cliente
           //para poder mandar a msg de volta
           String sentence = new String(receivePacket.getData());
           InetAddress IPAddress = receivePacket.getAddress();
           int port = receivePacket.getPort();

           System.out.println("Mensagem recebida: \n" + sentence);
       }
   }
   
}
