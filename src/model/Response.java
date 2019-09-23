package model;

import java.util.ArrayList;

public class Response {
	private String playerResponse;
	private ArrayList<Player> thirdParties;
	private String thirdPartiesResponse;
	private boolean endGame;
	
	public Response (String playerResponse, ArrayList<Player> thirdParties, String thirdPartiesResponse) {
		this.playerResponse = playerResponse;
		this.thirdParties = thirdParties;
		this.playerResponse = thirdPartiesResponse;
	}
	
	public Response (String playerResponse) {
		this.playerResponse = playerResponse;
	}

	public String getPlayerResponse() {
		return playerResponse;
	}

	public boolean endGame() {
		return endGame;
	}

	public void setEndGame(boolean endGame) {
		this.endGame = endGame;
	}

	public void setPlayerResponse(String playerResponse) {
		this.playerResponse = playerResponse;
	}

	public ArrayList<Player> getThirdParties() {
		return thirdParties;
	}

	public void setThirdParties(ArrayList<Player> thirdParties) {
		this.thirdParties = thirdParties;
	}

	public String getThirdPartiesResponse() {
		return thirdPartiesResponse;
	}

	public void setThirdPartiesResponse(String thirdPartiesResponse) {
		this.thirdPartiesResponse = thirdPartiesResponse;
	}
	
}
