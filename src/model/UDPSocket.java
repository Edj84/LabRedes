import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPSocket { 
	
	
	private static DatagramSocket socket;
	  
	public UDPSocket(){
		
        
    // create DatagramSocket and gets ip 
	socket = new DatagramSocket(7070);
		
	//create a sender thread with a nested runnable class definition 
		Thread send = new Thread(new Runnable() { 
			
			@Override
			public void run() { 
				try { 
					
					while (true) { 
						synchronized (this) { 
							
							send();
							
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
		
	}
	
	private static void send(DatagramPacket packet) throws IOException {
		socket.send(packet);		
	}
	
	private static void receive() throws IOException {
		byte[] receiveData = new byte[1024]; 
		DatagramPacket receivePackage = new DatagramPacket(receiveData,receiveData.length);
		socket.receive(receivePackage); 
		String content = (new String(receiveData)).trim();		
	} 
	
			
} 
