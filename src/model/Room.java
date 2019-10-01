package model;

import java.util.ArrayList;
import java.util.stream.Collectors;


public class Room extends AbstractObject {
    private static int id = 0;
	private Door[] doors;
    private String description;
    private ArrayList<AbstractObject> items;
    private ArrayList<Player> players;
	
    public Room (String description){
        this.id = ++id;
        doors = new Door[4];    	
        this.description = description;
        this.items = new ArrayList<AbstractObject>();
        this.players = new ArrayList<Player>();
    }
    
    public String getDescription() {
    	StringBuilder sb = new StringBuilder();
    	
    	sb.append("Quarto " + description + "\n");
    	
    	String direction = "";
    	
    	for(int i = 0; i <doors.length; i++) {
    		
    		switch(i) {
    			case 0:
    			direction = "NORTE";
    				break;
    			case 1:
        			direction = "SUL";
        				break;
    			case 2:
        			direction = "LESTE";
        				break;
    			case 3:
        			direction = "OESTE";
        				break;    			
    		}
    	
    		Door aux = doors[i]; 
    			
    		if(aux == null)
    			sb.append("Ao " + direction + " não há passagem.\n");
    		
    		else {
    			if(aux.checkClosed()) {
    				sb.append("Ao " + direction + " há uma porta fechada.\n");
    			}
    			else {
    				sb.append("Ao " + direction + " há uma porta aberta que leva ao quarto " + getDestination(direction).getColor() + "\n");
    			}
    		}
    		
    	}
    		
    		if(items.isEmpty())
    			sb.append("Não há itens neste quarto\n");
    		
    		else {
    			sb.append("O quarto contém os seguintes itens: \n");
    			
    			for(AbstractObject item : items)
    				sb.append(item.getDescription() + "\n");    			
    		}
    		
    		sb.append("Estão no quarto os seguintes jogadores: \n");
    			
    			for(Player player : players)
    				sb.append(player.getID() + "\n");    			
    		
    	
    	return sb.toString();
    }
    
    public ArrayList<Player> getPlayers(){
    	
    	if(players.isEmpty())
    		return null;
    	
    	return players;
    }
    
    public ArrayList<Player> getThirdParties(Player player){
    	
    	ArrayList<Player> thirdParties = new ArrayList<>(players);
    	thirdParties.remove(player);
    	
    	return thirdParties;
    }
    
    public Player getPlayerByID(String ID) {
    	
    	Player player = players.stream()
       		 .filter(p -> p.getID().equals(ID))
       		 .findFirst()
       		 .orElseGet(null);
       	
       	return player;
    }
    
    public boolean addPlayer(Player player) {
    	return players.add(player);
    }
    
    public boolean removePlayer(Player player) {
    	return players.remove(player);
    }
    
    public ArrayList<AbstractObject> getAllItems(){
    	return items;
    }
    
    public boolean search(String item) {
		if(item == "chave"){
			return true;
		}
    	
    	return items.stream()
    		 .anyMatch(i -> i.getDescription().equals(item));    		
    }
    
    public void addItem(AbstractObject item) {
    	items.add(item);
    }
    
    public void removeItem(AbstractObject item) {
    	items.remove(item);
    }
    
    public AbstractObject getItem(String item) {
    	
    	AbstractObject obj = items.stream()
    		 .filter(i -> i instanceof Key)
    		 .findFirst()
    		 .orElseGet(null);
    	
    	return obj;
    	
    }
    
    public Door[] getDoors() {
    	return doors;
    }
    
    public void setDoor(Door door, int direction) {
    	doors[direction] = door;
    }

	public String getColor() {
		return description;
	}

	public Room getDestination(String direction) {
		
		Room destination = null;
		Room[] connection = null;
		
		switch(direction.toUpperCase()) {
		case "NORTE":
			connection = doors[0].getConnection();
			break;
		case "SUL":
			connection = doors[1].getConnection();
			break;
		case "LESTE":
			connection = doors[2].getConnection();
			break;
		case "OESTE":
			connection = doors[3].getConnection();
			break;
		}
				
		if(connection[0].equals(this))
			destination = connection[1];
		else
			destination = connection[0];
						
		return destination;
	}
    
}