package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import controller.CommandManager;
import controller.PlayerManager;
import model.Map;
import model.Response; 

public class Node { 
	
	private static InetAddress IPAddress;
	private static DatagramSocket socket;
	private static DatagramPacket receivePacket;
	private static byte[] data;
	private static ArrayList<Packet> buffer;
		
	public node(InetAddress IPAddress, int port){
		this.IPAddress = IPAddress;
		socket = new DatagramSocket(port);
		
	}
		
}
    	
    