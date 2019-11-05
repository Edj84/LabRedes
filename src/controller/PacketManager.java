package controller;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;

public class PacketManager {
	private ArrayList<DatagramPacket> queue;
	private HashMap<String, ArrayList<String>> sentLog;
	private static InetAddress nextIPAddress;
	private StringBuilder sb;
	private int retentionLapse;
	
	public PacketManager(InetAddress IP, int retentionLapse) {
		queue = new ArrayList<DatagramPacket>();
		this.nextIPAddress = IP;
		sentLog = new HashMap<String, ArrayList<String>>();
		this.retentionLapse = retentionLapse*1000;  
	}
	
	public int getRetentionLapse(){
		return retentionLapse;
	}
	
	public static InetAddress getNextInetAddress() {
		return nextIPAddress;
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
		
		if(type.equals("2345")) {
		
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
			msgParts.add(cleanData);
		}
		
		return msgParts;
	}

	private static String cleanBlankSpaces(byte[] data) {
		return new String(data).trim();		
	}
		
	public DatagramPacket createPacket(String errorControl, String origin, String destiny, String CRC, String msg) {
		
		sb = new StringBuilder();
		
		sb.append("2345;");
		sb.append(errorControl + ":");
		sb.append(origin + ":");
		sb.append(destiny + ":");
		sb.append(CRC + ":");
		sb.append(msg);
		
		String aux = sb.toString();
		
		byte[] sendData = new byte[1024];
		
		sendData = aux.getBytes();

		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, nextIPAddress, 8080);		
		
		return sendPacket;
		
	}
	
	public void queueNewPacket(DatagramPacket sendPacket) {
		
		System.out.println("Fila com " + queue.size());
		
		if(queue.size() < 10 ) {
			queue.add(sendPacket);
		}

		System.out.println("Fila com " + queue.size());		
	}
	
	public void queuePacket(DatagramPacket sendPacket) {
		queue.add(0, sendPacket);
	}
	
	public DatagramPacket getPacketFromQueue() {
		
		if(!queue.isEmpty()) {
			
			DatagramPacket packet = queue.remove(0); 
			return packet;
		}
			
		return null;
	}
	
	public int getSendAttemps(String destiny, String CRC, String msg) {
		
		ArrayList<String> sent = sentLog.get(destiny);
		
		if(sent != null) {
		
			for(int i = 0; i < sent.size(); i = i + 3) {
			
				if(sent.get(i).equals(CRC))
					return Integer.valueOf(sent.get(i + 2));
			}
			
			for(int i = 1; i < sent.size(); i = i + 3) {
				
				if(sent.get(i).equals(msg))
					return Integer.valueOf(sent.get(i + 1));
			}
			
		}
		
		return 0;
	}

	public String retrieveSentMsg(String destiny, String CRC, String msg) {
		
		ArrayList<String> sent = sentLog.get(destiny);
		
		if(sent != null) {
			
			for(int i = 0; i < sent.size(); i = i + 3) {
			
				if(sent.get(i).equals(CRC))
					return sent.get(i+1);
			}
			
			for(int i = 1; i < sent.size(); i = i + 3) {
				
				if(sent.get(i).equals(msg))
					return msg;
			}
			
		}
		
		return null;
		
	}
	
}
