package controller;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.ArrayList;

public final class CommandManager {
	
	private static StringBuilder sb = new StringBuilder();
	private static Response response; 
		
	public CommandManager() {
		playerManager = new PlayerManager();
		map = new Map();
		thirdParties = new ArrayList<Player>();
	}
	
	public static ArrayList<DatagramPacket> process(DatagramPacket receivePacket) throws NullPointerException {
	
	}
		
	private static String cleanBlankSpaces(byte[] data) {
		return new String(data).trim();		
	}
	
		
}




    
