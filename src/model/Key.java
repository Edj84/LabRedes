package model;

public class Key {
    private String id;
    private String description;

    public Key(String id, String description){
        this.id = id;
        this.description = description;
    }

    public String getId(){
        return id;
    }
    
    public String getDescription(){
        return description;
    }
    
}
