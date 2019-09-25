package model;

import java.util.ArrayList;

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

    public Room (Door north, Door south, Door east, Door west, String description, ArrayList<AbstractObject> objects, ArrayList<Player> players){
        this.id = ++id;
    	this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;
        this.description = description;
        this.items = items;
        this.players = players;
        this.color = "COR " + String.valueOf(id);
    }
    
    public String getDescription() {
    	return description;
    }
    
    public ArrayList<Player> getPlayers(){
    	return players;
    }
    
    public ArrayList<AbstractObject> getItems(){
    	return items;
    }

	public String getColor() {
		return color;
	}
    
}
