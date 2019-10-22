import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;


public class UDPServer { 
	
	private static InetAddress IPAddress;
	private static DatagramSocket serverSocket;
	private static DatagramPacket receivePacket;
	private static byte[] receiveData;
	private static byte[] sendData;
	private static ArrayList<DatagramPacket> outBuffer;
	private static String message;
		
	public static void main(String args[]) { 
		
		try {
			IPAddress = InetAddress.getLocalHost();
		} 
		catch (UnknownHostException e) {
			System.out.println("Unable to get server IPAddress");
		}
		try {
			serverSocket = new DatagramSocket(8080);
		} catch (SocketException e) {
			System.out.println("Unable to start server socket");
		} 
		
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
							
							//exit condition 
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
							
							System.out.println("Im listening");
							receive();
							process(receivePacket);
							
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
		
		while(true) {
			send.run();
			receive.run();
		}

		//send.join(); 
		//receive.join(); 
	} 
	
	private static void receive() throws IOException {
		receiveData = new byte[1024];
		receivePacket = new DatagramPacket(receiveData, receiveData.length); 
		serverSocket.receive(receivePacket);		
	}
		
	private static void process(DatagramPacket receivePacket) {
		
		ArrayList<DatagramPacket> packets = new ArrayList<DatagramPacket>();
		outBuffer = packets;
	}
	
	private static void send(DatagramPacket sendPacket) throws IOException {
		sendData = sendPacket.getData();
		serverSocket.send(sendPacket);
		String aux = new String(sendData).trim();
		System.out.println("Enviei ao cliente " + aux);
	}
	
	private static void errorPacket(DatagramPacket receivePacket, String errorMessage) throws IOException {
		
		sendData = new byte[1024];
		sendData = errorMessage.getBytes();
		InetAddress clientIPAddress = receivePacket.getAddress();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientIPAddress, 7070);		
		send(sendPacket);
		
	}
		
}
    	
    