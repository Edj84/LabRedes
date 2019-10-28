import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPSocket { 
	
	private static InetAddress IPAddress;
	private static DatagramSocket socket;
	private static String command;
	private static String serverMessage;
    private boolean type; // true -> in ; false -> out

    public UDPSocket(){

        
    }

		// create DatagramSocket and gets ip 
		socket = new DatagramSocket(070); 
		byte[] ipAddr = new byte[]{(byte) Integer.parseInt(split[0]), (byte) Integer.parseInt(split[1]), (byte) Integer.parseInt(split[2]), (byte) Integer.parseInt(split[3])};
		IPAddress = InetAddress.getByAddress(ipAddr);
		
		serverMessage = "";
		running = false;
		
		// create a sender thread with a nested runnable class definition 
		Thread send = new Thread(new Runnable() { 
			
			@Override
			public void run() { 
				try { 
					
					while (true) { 
						synchronized (this) { 
							
							prompt(); 
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

							receive();
							
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
		
		while(true) {
			send.run();
			receive.run();
		}

		//send.join(); 
		//receive.join(); 
	}
	
	private static String send(String command) throws IOException {
		
		byte[] sendData = new byte[1024];
		sendData = command.getBytes();

		DatagramPacket sendPackage = new DatagramPacket(sendData, sendData.length, IPAddress, 8080);
		clientSocket.send(sendPackage); 
		
		System.out.println("Enviei ao servidor: " + command);

		return command;		 
	}
	
	private static void receive() throws IOException {
		byte[] receiveData = new byte[1024]; 
		DatagramPacket receivePackage = new DatagramPacket(receiveData,receiveData.length);
		clientSocket.receive(receivePackage); 
		String serverMessage = (new String(receiveData)).trim(); 
		System.out.println("Mensagem do servidor: " + serverMessage);		
		serverMessage = "Mensagem do servidor: " + serverMessage;
	} 
	
			
} 
