package controller;

import java.util.ArrayList;
import model.Player;


public final class PlayerManager {
	
	private ArrayList<Player> players;
	
	public void GerenciadorDeJogadores(){
		
	}
	
	public boolean login(Player player){
		
		return players.stream().anyMatch(p -> p.getId().equals(player.getId()));
	}
	
	public boolean login(Player player){
		
		return players.stream().anyMatch(p -> p.getId().equals(player.getId()));
	}
	

}
