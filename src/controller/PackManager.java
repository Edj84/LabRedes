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
	}
	
	public ArrayList<String> unpack(DatagramPacket packet){
		
		byte[] rawData = new byte[1024]; 
		rawData = packet.getData();
		String cleanData = cleanBlankSpaces(rawData);		
		
		return separateData(cleanData);
	}
	
	private ArrayList<String> separateData(String cleanData) {
		
		//System.out.println("Recebi para separar " + cleanData);
		ArrayList<String> msgParts = new ArrayList<String>();
		
		String type =  cleanData.substring(0, 4);
		
		msgParts.add(type);
		
		cleanData = cleanData.substring(5);
		//System.out.println("Tirei pack type. Data agora é " + cleanData);
		int separatorIndex;
		
		for(int n = 1; n < 5; n++) {
			separatorIndex = cleanData.indexOf(":");
			//System.out.println("N é " + n + " Separator index is " + separatorIndex);
			String part = cleanData.substring(0, separatorIndex);
			//System.out.println("Separei " + part);
			msgParts.add(part);
			cleanData = cleanData.substring(separatorIndex + 1);
			//System.out.println("Data agora é " + cleanData);
		}
		
		return msgParts;
	}

	private static String cleanBlankSpaces(byte[] data) {
		return new String(data).trim();		
	}
		
	public void createPacket(String originID, String destinationID, String CRC, String content) {
		
		sb = new StringBuilder();
		
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
