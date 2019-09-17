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
	
    public void receiveMessage() {
        
    }

    public void sendMessage() {
        
    }
    
    private static String getFile(String filePath)
	{
	    String content = "";
	    try
	    {
	        content = new String (Files.readAllBytes(new File(filePath).toPath()));
	    }
	    catch (IOException e)
	    {
	        e.printStackTrace();
	    }
	    return content;
	}
	
   public static void main(String args[]) throws Exception {
      // declara socket cliente
      DatagramSocket clientSocket = new DatagramSocket();

      // obtem endereço IP do servidor com o DNS
      byte[] ipAddr = new byte[]{(byte) 10, (byte) 32, (byte) 143, (byte) 192};
      InetAddress IPAddress = InetAddress.getByAddress(ipAddr);

      byte[] sendData = new byte[1024];
        
      sendData = getFile("fileGrande.txt").getBytes();

      // cria pacote com o dado, o endereço do server e porta do servidor
      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 8080);

      //envia o pacote
      clientSocket.send(sendPacket);

      // fecha o cliente
      clientSocket.close();
   }
}
