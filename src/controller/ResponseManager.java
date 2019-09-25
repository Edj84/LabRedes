package controller;

import java.net.DatagramPacket;
import java.util.ArrayList;

import model.Response;

public class ResponseManager {
	ArrayList<Response> responses;
	DatagramPacket outcomming;
	
	public ResponseManager(DatagramPacket incoming) {
		responses = new ArrayList<Response>();
	}
	
	public DatagramPacket packResponse() {
		
	}

}
