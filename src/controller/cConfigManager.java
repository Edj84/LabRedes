package controller;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.ArrayList;

public final class ConfigManager {
	
	private static StringBuilder sb; 
	private static InetAddress IPAddress;
	private static DatagramSocket serverSocket;
	private static String ID; 

		
	public CommandManager(InetAddress IPAddress, ) {
		sb = new StringBuilder();
	}
	
	public static void process(String path) {
		
	}	
		
	private static String cleanBlankSpaces(byte[] data) {
		return new String(data).trim();		
	}
	
		
}