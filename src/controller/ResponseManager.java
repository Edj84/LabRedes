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
		clients = response.getListeners();
		
		sendData = new byte[1024];
		sendData = responses.remove(0).getBytes();
		client = response.getListeners().remove(0);
		sendPacket = new DatagramPacket(sendData, sendData.length, client.getIPAddress(), 7070);
		sendPackets.add(sendPacket);
		
		do {
			sendData = new byte[1024];
			sendData = responses.get(0).getBytes();
			client = response.getListeners().remove(0);
			sendPacket = new DatagramPacket(sendData, sendData.length, client.getIPAddress(), 7070);
			sendPackets.add(sendPacket);
		}
		
		while(!clients.isEmpty());
		
		return sendPackets;
		
	}

}
