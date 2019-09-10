package common;

import java.net.DatagramPacket;


public interface Communicates {
	
	public void sendMessage(DatagramPacket pack);
	public void receiveMessage(DatagramPacket pack);
	
}
