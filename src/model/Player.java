package model;

import java.net.*;
import java.util.ArrayList;

public class Player {
   
	private String id;
	private InetAddress IPAddress;
	private ArrayList<AbstractObject> backPack;
	private Room location;

    public Player (String id, InetAddress IPAddress){
        this.id = id;
        this.IPAddress = IPAddress;
        this.backPack = new ArrayList<AbstractObject>();        
    }
    
    public String getId() {
    	return id;
    }
    
    public InetAddress getIPAddress() {
    	return IPAddress;
    }
    
    public ArrayList<AbstractObject> getBackPack(){
    	return backPack;
    }
    
    public boolean searchBackPack(AbstractObject item) {
    	return backPack.stream()
    			.anyMatch(o -> o.equals(item));
    }
    
    public void keep(AbstractObject item) {
    	backPack.add(item);
    }
    
    public void drop(AbstractObject item) {
    	backPack.remove(item);
    }
    
    
    public void setIPAddress(InetAddress newIPAddress) {
    	this.IPAddress = newIPAddress;
    }
    
    public Room getLocation() {
    	return location;
    }
    
    public void setLocation(Room location) {
    	this.location = location;
    }
    
    public String getStatus() {
    	return "Jogador " + getId() + " está na " + getLocation().getDescription();
    }

    @Override
    public String toString() {
    	return "Client " + id + "\nIP " + IPAddress;
    }
}
