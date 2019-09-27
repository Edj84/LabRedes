package model;

import java.util.ArrayList;

public class Door extends AbstractObject {
    private static int id = 0;
    private String description;
    private ArrayList<Key> requirements;
    private Room[] connection;
    private boolean closed;

    public Door (String description, ArrayList<Key> requirements, boolean closed){
        this.id = ++id;
        this.description = description;
        this.requirements = requirements;
        this.connection = new Room[2];
        this.closed = closed;
    }

    public int getId(){
		return id;
    }

    public String getDescription(){
		return description;
    }

    public ArrayList<Key> getRequirements(){
    	return requirements;
    }

    public boolean checkClosed(){
    	return closed;
    }
    
    public boolean open(Key key) {
    	if(requirements.stream()
    			.anyMatch(k -> k.equals(key))) {
    		closed = false;
    		return true;
    	}
    	
    	else
    		return false;		
    }
    
    public boolean close(Key key) {
    	if(requirements.stream()
    			.anyMatch(k -> k.equals(key))) {
    		closed = true;
    		return true;
    	}
    	
    	else
    		return false;		
    }
    
    public String getConnection() {
    	
    	if(!closed)
    		return "A porta " + description + " conecta as salas " + connection[0].getDescription() + " e " + connection[1].getDescription();
    	
    	else
    		return "A porta " + description + " está fechada. Não é possível ver onde ela leva";
    }
    
}
