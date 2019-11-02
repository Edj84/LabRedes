package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.DatagramPacket;
import java.net.InetAddress;
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
    	byte[] IP = config.get(0).getBytes();
    	packMan = new PackManager(IP);   	
    	
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
			scan.useDelimiter(":");
			
			//Reading next node IPAddress
			String aux = scan.next();
			System.out.println(aux);
			config.add(aux);
			//Reading next node port
			aux = scan.next();
			System.out.println(aux);
			config.add(aux);
			scan.reset();
			
			//Reading node ID, token time and token manager flag
			while(scan.hasNext()) {
				aux = scan.next();
				config.add(aux);
				System.out.println(aux);
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
	
}