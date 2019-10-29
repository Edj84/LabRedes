package controller;

import java.io.IOException;
import java.net.DatagramPacket;

import model.Node;

public class App{
    private static Node node;
    private static byte[] receiveData;
    private static DatagramPacket receivePacket;
    

    public static void main(String[] args){
        node = new Node("Nó");

    Thread send = new Thread(new Runnable() { 
			@Override
			public void run() { 
				try { 
					
					while (true) { 
						synchronized (this) { 
																					
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
							//Método que processa mensagens recebidas 
                            //process(receivePacket);
														 
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
	
	private static void receive(DatagramPacket receivePacket) throws IOException {
		byte[] receiveData = new byte[1024];
		receivePacket = new DatagramPacket(receiveData, receiveData.length); 
		serverSocket.receive(receivePacket);
		//refatorar; pacote mais complexo! abrir receiveData e desmembrar 
		System.out.println("Recebi do cliente " + receivePacket.getAddress() + ": " + clientMessage);
				
	}

    private static void send(DatagramPacket sendPacket) throws IOException {
		sendData = sendPacket.getData();
		serverSocket.send(sendPacket);
		String aux = new String(sendData).trim();
		System.out.println("Enviei ao cliente " + aux);
	}

}