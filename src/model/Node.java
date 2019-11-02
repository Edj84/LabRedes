package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

import controller.PackManager;
import controller.TokenManager;

public class Node{
    private String ID;
    private UDPSocket inSocket;
    private UDPSocket outSocket;
    private PackManager packMan;
    private TokenManager tokenMan;
    private ArrayList<String> config;
    private DatagramPacket token;

    public Node(){
        readConfig();
        setConfig();        
    }
    
    private void setConfig() {
    	
    	//Setting up IP for next node in ring and pack management module
    	InetAddress IP;
		try {
			IP = InetAddress.getByName(config.get(0));
			packMan = new PackManager(IP);   	
		} 
		
		catch (UnknownHostException e) {
			System.out.println("ERROR: Unable to set IP for next node in token ring");
		}
    	
    	//Reading next node port
		int nextNodePort = Integer.parseInt(config.get(1));
    	
    	//Setting up inSocket
    	inSocket = new UDPSocket(nextNodePort-1);
    	
    	//Setting up outSocket
    	outSocket = new UDPSocket(nextNodePort);
    	
    	//Setting up node ID
    	ID = config.get(2);
    	
    	//Setting up token management module
    	tokenMan = new TokenManager(Integer.parseInt(config.get(3)), Boolean.parseBoolean(config.get(4)));    			
    			
	}


	private void readConfig() {
    	config = new ArrayList<String>();
		File file = new File("config.txt");
    	Scanner scan = null;
		try {
			scan = new Scanner(file);
						
			//Reading next node IPAddress anda port
			String aux = scan.nextLine();
			
			//Split IP and port
			String[] parts = aux.split(":");
			for(String s : parts) 
				config.add(s);
							
			//Reading node ID, token time and token manager flag
			while(scan.hasNextLine()) {
				aux = scan.nextLine();
				config.add(aux);
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
    	return token != null;
    }
	
	public void receive() {
		try {
			DatagramPacket packet = inSocket.receive();
			packMan.unpack(packet);
		} 
		
		catch (IOException e) {
			System.out.println("ERROR: Unable to receive packet");
		}
	}
	
}