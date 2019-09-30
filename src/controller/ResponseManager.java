package controller;

import java.net.DatagramPacket;
import java.util.ArrayList;

import model.Player;
import model.Response;

public class ResponseManager {
	private static ArrayList<String> responses;
	private static DatagramPacket outcomming;
	private static byte[] sendData;
	private static DatagramPacket sendPacket;
	private static ArrayList<DatagramPacket> sendPackets;
	private static Player client;
	private static ArrayList<Player> clients;
	
	public static ArrayList<DatagramPacket> packResponses(Response response) {
		
		responses = response.getServerMessages();
		System.out.println("responses " + responses.size());
		clients = response.getListeners();
		System.out.println("clients " + clients.size());
		sendData = new byte[1024];
		
		if(!responses.isEmpty()) {
			
			sendData = responses.remove(0).getBytes();
			client = response.getListeners().remove(0);
			sendPacket = new DatagramPacket(sendData, sendData.length, client.getIPAddress(), 7070);
			sendPackets = new ArrayList<DatagramPacket>();
			sendPackets.add(sendPacket);
		
			while(!clients.isEmpty()) {
				sendData = new byte[1024];
				sendData = responses.get(0).getBytes();
				client = response.getListeners().remove(0);
				sendPacket = new DatagramPacket(sendData, sendData.length, client.getIPAddress(), 7070);
				sendPackets.add(sendPacket);
			}
		}
			
		return sendPackets;
	}
	
}
