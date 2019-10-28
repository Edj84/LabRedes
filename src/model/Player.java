package model;

import java.net.*;
import java.util.ArrayList;

public class Player {
   
	private String ID;
	private InetAddress IPAddress;
	private ArrayList<AbstractObject> backPack;
	private Room location;

    public Player (String ID, InetAddress IPAddress){
        this.ID = ID;
        this.IPAddress = IPAddress;
        this.backPack = new ArrayList<AbstractObject>();        
    }
    
    public String getID() {
    	return ID;
    }
    
    public InetAddress getIPAddress() {
    	return IPAddress;
    }
    
    public ArrayList<AbstractObject> getBackPack(){
    	return backPack;
    }
    
    public boolean searchBackPack(String item) {
    	return backPack.stream()
    			.anyMatch(o -> o.getDescription().equals(item));
    }
    
    public void keep(AbstractObject item) {
    	backPack.add(item);
    }
    
    public void drop(AbstractObject item) { 
    	backPack.remove(item);
    }
    
    public AbstractObject getObjectFromBackpack(String target) {
    	 return backPack.stream()
    					  .filter(i -> i.getDescription().equals(target))
    					  .findFirst()
    					  .orElseGet(null);
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
    	return "Jogador " + getID() + " está na " + getLocation().getDescription();
    }

    @Override
    public String toString() {
    	return "Client " + ID + "\nIP " + IPAddress;
    }
}
