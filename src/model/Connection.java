package model;

import java.util.ArrayList;

public class Connection{
    private String id;
    private Door door;
    private ArrayList<Room> rooms;

    public Connection(String id, Door door, ArrayList<Room> rooms){
        this.id = id;
        this.door = door;
        this.rooms = rooms;
    }

    public String getId(){
		return id;
    }

    public String getDescription(){
		return null;

    }

    public ArrayList<Room> getRooms(){
		return rooms;
    }

    public void passThrough(){
        
    }
}
