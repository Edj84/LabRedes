package controller;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class PackManager {
	private ArrayList<DatagramPacket> queue;
	private ArrayList<DatagramPacket> sent;
	private InetAddress nextIPAddress;
	private StringBuilder sb;
	
	public PackManager(byte[] nextIPAddress) {
		queue = new ArrayList<DatagramPacket>();
		sent = new ArrayList<DatagramPacket>();
		try {
			this.nextIPAddress = InetAddress.getByAddress(nextIPAddress);
		} catch (UnknownHostException e) {
			System.out.println("ERROR: Unable to set IP for next node in token ring");
			e.printStackTrace();
		}
		sb = new StringBuilder();
	}
	
	public ArrayList<String> unpack(DatagramPacket packet){
		
		
		
		return null; 
	}
	
	public void createPacket(String originID, String destinationID, String CRC, String content) {
		//2345;naocopiado:Bob:Alice:19385749:Oi Mundo!
		sb.append("2345;");
		sb.append("naocopiado:");
		sb.append(originID + ":");
		sb.append(destinationID + ":");
		sb.append(getCRC() + ":");
		sb.append(content);
		
		String aux = sb.toString();
		
		byte[] sendData = new byte[1024];
		
		sendData = aux.getBytes();

		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, nextIPAddress, 8080);		
		
		queuePacket(sendPacket);
		
	}
	
	private void queuePacket(DatagramPacket sendPacket) {
		queue.add(sendPacket);
	}
	
	public DatagramPacket getPacket() {
		
		if(!queue.isEmpty()) {
			DatagramPacket packet = queue.remove(0); 
			sent.add(packet);
			return packet;
		}
		
		else
			return null;
	}

	private String getCRC() {
		
		return null;
	}
	
	
}
