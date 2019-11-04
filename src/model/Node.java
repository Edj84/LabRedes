package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;

import controller.MessageManager;
import controller.PacketManager;
import controller.TokenManager;
import controller.SecurityManager;
import controller.Rand;

public class Node{
    private static String ID;
    private UDPSocket inSocket;
    private UDPSocket outSocket;
    private PacketManager packMan;
    private TokenManager tokenMan;
    private SecurityManager securityMan;
    private MessageManager messageMan;
    private ArrayList<String> config;
    private boolean hasToken;

    public Node(){
        readConfig();
        setConfig();
        
        if(isTokenManager())
        	tokenMan.aquireToken();
    }
    
    private void setConfig() {
    	
    	//Setting up IP for next node in ring and pack management module
    	InetAddress IP;
		try {
			IP = InetAddress.getByName(config.get(0));
			packMan = new PacketManager(IP, Integer.parseInt(config.get(3)));   	
		} 
		
		catch (UnknownHostException e) {
			System.out.println("ERROR: Unable to set IP for next node in token ring");
		}
    	
    	//Reading next node port
		int nextNodePort = Integer.parseInt(config.get(1));
    	
    	//Setting up inSocket
    	inSocket = new UDPSocket(8080);
    	
    	//Setting up outSocket
    	outSocket = new UDPSocket(nextNodePort);
    	
    	//Setting up node ID
    	ID = config.get(2);
    	
    	//Setting up token management module
    	tokenMan = new TokenManager(Boolean.parseBoolean(config.get(4)), Integer.parseInt(config.get(3)), 3); // 3 = numero de pcs
    			
    	//Creating message manager
    	messageMan = new MessageManager();		
    			
	}

	private void readConfig() {
    	
		config = new ArrayList<String>();
		File file = new File("config.txt");
    	Scanner scan = null;
		
    	try {
			scan = new Scanner(file);
						
			//Reading next node IPAddress and port
			String aux = scan.nextLine();
			
			//Split IP and port
			String[] parts = aux.split(":");
			for(String s : parts) { 
				config.add(s);
				System.out.println("Adicionando " + s);
			}
							
			//Reading node ID, token time and token manager flag
			while(scan.hasNextLine()) {
				aux = scan.nextLine();
				config.add(aux);
				System.out.println("Adicionando " + aux);
			}
		
		} 
		
		catch (FileNotFoundException e) {
			System.out.println("ERROR: Unable to find config file");
		}
		
		finally {
			scan.close();
		}
		
	}

	public boolean hasToken() {
    	return tokenMan.getToken() != null;		
    }
	
	public void send(DatagramPacket sendPacket) {
		
		try {
			outSocket.send(sendPacket);
		}
		
		catch (IOException e) {
			System.out.println("ERROR: Unable to send packet");
		}
		
	}
	
	public void sendFromQueue() {
		
		if(hasToken) {
			DatagramPacket sendPacket = packMan.getPacketFromQueue();
				
			if(sendPacket != null)
				send(sendPacket);
		}
		
	}
	
	public String receive() {
		
		String msg = null;
		
		try {
			DatagramPacket packet = inSocket.receive();
			ArrayList<String> data = packMan.unpack(packet);
			msg = process(data);
		} 
		
		catch (IOException e) {
			System.out.println("ERROR: Unable to receive packet");
		}
		
		return msg;
		
	}

	private String process(ArrayList<String> data) {
		
		String packetType = data.get(0);
		String buffer = null;
		
		switch(packetType) {
		
			case "1234":
					
				tokenMan.aquireToken();
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		        buffer = "Token received (Local time is " + timestamp + ")";
		        break;
				
			case "2345":
				
				String errorControl = data.get(1);
				String origin = data.get(2);
				String destiny = data.get(3);
				String CRC = data.get(4);
				String msg = data.get(5);
				
				if (origin.equals(ID)) {
					
					DatagramPacket token = tokenMan.createToken();
					send(token);
					hasToken = false;
					
					errorControl = errorControl.toUpperCase();
					
					if(errorControl.equals("ERROR")){
						
						int attemps = packMan.getSendAttemps(destiny, CRC, msg);
						
						if(attemps < 2) {
							msg = packMan.retrieveSentMsg(destiny, CRC, msg);
							
							if(msg != null) {
								//CRC = getCRC(msg)
								DatagramPacket packet = packMan.createPacket("naocopiado", origin, destiny, CRC, msg);
								packMan.queuePacket(packet);
							}
							
							else {
								buffer = "ERROR: unable to recover original message (CRC and content are both corrupt)";
							}
						}
					}
					
				}
				
				else {
					
					if(destiny.equals(ID)) {
						
						if (securityMan.check(CRC, msg)) {
							errorControl = "ACK";
						}
						
						else {
							errorControl = "erro";
						}
						
						buffer = "Incoming message from " + origin + ": " + msg;
						
					}
					
					if(destiny.equals("TODOS"))
						buffer = "Incoming message from " + origin + ": " + msg;
					
					DatagramPacket packet = packMan.createPacket(errorControl, origin, destiny, CRC, msg);
					send(packet);
						
				}
				
				break;	
			
			default:
				buffer = "ERROR: Invalid header (packet type identifier " + packetType + ")";
			}
		
		return buffer;
				
	}

	public static String getID() {
		return ID;
	}
	
	public boolean isTokenManager() {
		return tokenMan.getIsTokenManager();
	}
	
	public void timeoutRoutine() {
		
		if (tokenMan.checkTokenTimeout()) {
			DatagramPacket token = tokenMan.createToken();
			send(token);
			System.out.println("Where the heck is that old token? Here goes a new one'");
		}
		
	}
	
	public void tokenScramble() {
		
		if(Rand.getRandInt(0, 20) > 1) {
			DatagramPacket token = tokenMan.createToken();
			send(token);
			System.out.println("Ops, that token just slipped out. Sorry about that");
		}
		
		if(hasToken) {
			if(Rand.getRandInt(0, 20) > 1) 
				tokenMan.destroyToken();
			System.out.println("Damn, I killed your token pet. Sorry, pal!");
		}
	}
	
	
	public void generateMessage() {
		ArrayList<String> data = messageMan.getNewMessage();		
		DatagramPacket packet = packMan.createPacket(data.get(0), data.get(1), data.get(2), data.get(3), data.get(4));
		packMan.queueNewPacket(packet);		
	}
}