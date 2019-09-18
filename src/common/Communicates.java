package common;

import java.net.DatagramPacket;


public interface Communicates {
	
	public void sendMessage(String message);
	public String receiveMessage();
	
}
