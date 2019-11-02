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
	
	public PackManager(InetAddress IP) {
		queue = new ArrayList<DatagramPacket>();
		sent = new ArrayList<DatagramPacket>();
		this.nextIPAddress = IP;
		sb = new StringBuilder();
	}
	
	public ArrayList<String> unpack(DatagramPacket packet){
		
		byte[] rawData = new byte[1024]; 
		rawData = packet.getData();
		String cleanData = cleanBlankSpaces(rawData);		
		
		return separateData(cleanData);
	}
	
	private ArrayList<String> separateData(String cleanData) {
		
		ArrayList<String> msgParts = new ArrayList<String>();
		
		msgParts.add(cleanData.substring(0, 4));
		
		String aux = cleanData.substring(5);
		int separatorIndex;
		
		for(int n = 1; n < 5; n++) {
			separatorIndex = aux.indexOf(":");
			String part = cleanData.substring(0, separatorIndex);
			msgParts.add(part);
			System.out.println("Adicionei " + part);
			aux = cleanData.substring(separatorIndex + 1);									
		}
		
		return msgParts;
	}

	private static String cleanBlankSpaces(byte[] data) {
		return new String(data).trim();		
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
