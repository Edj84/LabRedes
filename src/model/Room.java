package model;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Room extends AbstractObject {
    private static int id = 0;
	private Door north;
    private Door south;
    private Door east;
    private Door west;
    private String description;
    private ArrayList<AbstractObject> items;
    private ArrayList<Player> players;
	public String color;

    public Room (Door north, Door south, Door blueDoor, Door west, String description, ArrayList<AbstractObject> objects, ArrayList<Player> players){
        this.id = ++id;
    	this.north = north;
        this.south = south;
        this.east = blueDoor;
        this.west = west;
        this.description = description;
        this.items = objects;
        this.players = players;
        this.color = "COR " + String.valueOf(id);
    }
    
    public String getDescription() {
    	return description;
    }
    
    public ArrayList<Player> getPlayers(){
    	
    	if(players.isEmpty())
    		return null;
    	
    	return players;
    }
    
    public ArrayList<Player> getThirdParties(Player player){
    	
    	ArrayList<Player> thirdParties = new ArrayList<>(players);
    	thirdParties.remove(player);
    	
    	if(thirdParties.isEmpty())
    		return null;
    	
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
    		 .filter(i -> i.getDescription().equals(item))
    		 .findFirst()
    		 .orElseGet(null);
    	
    	return obj;
    	
    }
    
    public Door[] getDoors() {
    	Door[] doors = new Door[4];
    	doors[0] = north;
    	doors[1] = south;
    	doors[2] = east;
    	doors[3] = west;
    	
    	return doors;
    }

	public String getColor() {
		return color;
	}

	public Room getDestination(String direction) {
		
		Room destination = null;
		Room[] connection = null;
		
		switch(direction) {
		case "NORTH":
			connection = north.getConnection();
			break;
		case "SOUTH":
			connection = south.getConnection();
			break;
		case "EAST":
			connection = east.getConnection();
			break;
		case "WEST":
			connection = west.getConnection();
			break;
		}
				
		if(connection[0].equals(this))
			destination = connection[1];
		else
			destination = connection[0];
						
		return destination;
	}
    
}