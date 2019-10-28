package model;

import java.util.ArrayList;

public class Response {
	private Player player;
	private ArrayList<Player> listeners;
	private ArrayList<String> serverMessages;
	private boolean endGame;
	
	public Response (Player player, String playerResponse, ArrayList<Player> thirdParties, String thirdPartiesResponse) {
		this.player = player;
		this.listeners = thirdParties;
		serverMessages = new ArrayList<String>();
		serverMessages.add(playerResponse);
		serverMessages.add(thirdPartiesResponse);
	}
	
	public Response (Player player, String playerResponse) {
		this.player = player;
		this.listeners = new ArrayList<Player>();
		serverMessages = new ArrayList<String>();
		serverMessages.add(playerResponse);		
	}

	public Player getPlayer() {
		return player;
	}
	
	public ArrayList<String> getServerMessages() {
		return serverMessages;
	}

	public boolean endGame() {
		return endGame;
	}

	public void setEndGame(boolean endGame) {
		this.endGame = endGame;
	}
		
	public ArrayList<Player> getListeners() {
		return listeners;
	}

}
	
