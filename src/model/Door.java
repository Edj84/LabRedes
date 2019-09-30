package model;

import java.util.ArrayList;

public class Door extends AbstractObject {
    private static int ID = 0;
    private String description;
    private Key requirement;
    private Room[] connection;
    private boolean closed;

    public Door (String description, Key requirement, boolean closed){
        this.ID = ++ID;
        this.description = description;
        this.requirement = requirement;
        this.connection = new Room[2];
        this.closed = closed;
    }

    public int getID(){
		return ID;
    }

    public String getDescription(){
		return description;
    }

    public Key getRequirement(){
    	return requirement;
    }

    public boolean checkClosed(){
    	return closed;
    }
    
    public boolean useKey(Key key) {
    	if(this.requirement.equals(key)) {
    		closed = !closed;
    		return true;
    	}
    	
    	else
    		return false;		
    }
           
    public Room[] getConnection() {
    	return connection;
    }
    
    public String getConnectionDescription() {
    	
    	if(!closed)
    		return "A porta " + description + " conecta as salas " + connection[0].getDescription() + " e " + connection[1].getDescription();
    	
    	else
    		return "A porta " + description + " está fechada. Não é possível ver onde ela leva";
    }
    
}