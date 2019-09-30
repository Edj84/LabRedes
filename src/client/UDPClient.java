package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient { 
	
	private static InetAddress IPAddress;
	private static DatagramSocket clientSocket;

	public static void main(String args[]) throws IOException, InterruptedException { 

		// create DatagramSocket and gets ip 
		clientSocket = new DatagramSocket(7070); 
		IPAddress = InetAddress.getLocalHost(); 
		
		send(login());
		
		// create a sender thread with a nested runnable class definition 
		Thread send = new Thread(new Runnable() { 
			
			@Override
			public void run() { 
				try { 
					
					while (true) { 
						synchronized (this) { 
							
							String command = View.prompt(); 
							send(command);
							
							// exit condition 
							if (command.toUpperCase().equals("SAIR")) { 
								System.out.println("Fugindo da briga...");
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
		
		// create a receiver thread with a nested runnable class definition 
		Thread receive = new Thread(new Runnable() {
			
			@Override
			public void run() { 
				try { 

					while (true) { 
						synchronized (this) { 

							String serverMessage = receive();
							
							//exit condition 
							if (serverMessage.toUpperCase().equals("PARAR")) { 
								System.out.println("O jogo acabou"); 
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
	
	private static String send(String command) throws IOException {
		
		byte[] sendData = new byte[1024];
		sendData = command.getBytes();

		DatagramPacket sendPackage = new DatagramPacket(sendData, sendData.length, IPAddress, 8080);
		clientSocket.send(sendPackage); 
		
		System.out.println("Mandei " + command);

		return command;		 
	}
	
	private static String receive() throws IOException {
		byte[] receiveData = new byte[1024]; 
		DatagramPacket receivePackage = new DatagramPacket(receiveData,receiveData.length);
		clientSocket.receive(receivePackage); 
		String serverMessage = (new String(receiveData)).trim(); 
		System.out.println("Recebi " + serverMessage);
		
		return "Mensagem do servidor: " + serverMessage;
	} 

	private static String login() {
		return View.login();
	} 
} 
