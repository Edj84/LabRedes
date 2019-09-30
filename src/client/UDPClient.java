package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPClient { 
	
	private static InetAddress IPAddress;
	private static DatagramSocket clientSocket;
	private static boolean running;
	private static Scanner scan;
	private static String command;
	private static String serverMessage;

	public static void main(String args[]) throws IOException, InterruptedException { 

		// create DatagramSocket and gets ip 
		clientSocket = new DatagramSocket(7070); 
		IPAddress = InetAddress.getLocalHost();
		scan = new Scanner(System.in);
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
	
	public static void welcome() {
		title();
		System.out.println("Bem-vindo(a) ao NOME DO JOGO!\n");
	}
	
	public static void title() {
		System.out.println("####################");
		System.out.println("#                  #");
		System.out.println("#   NOME DO JOGO   #");
		System.out.println("#                  #");
		System.out.println("####################\n");
	}
	
	public static void goal() {
		System.out.println("Seu objetivo neste jogo é OBJETIVO DO JOGO...\n");
	}
	
	public static void login() {
		scan = new Scanner(System.in);
		
		System.out.println("Para começar, insira seu nome de usuário.");
		System.out.print("Usuário: ");
		
		String login = scan.nextLine();
		command = "LOGIN " + login.toUpperCase().trim();		
	}
	
	public static void prompt() {
		scan = new Scanner(System.in);
		
		System.out.println("O que você quer fazer?");
		String text = scan.nextLine();
		
		command = text.toUpperCase();		
	}
		
} 
