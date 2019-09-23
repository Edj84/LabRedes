package model;

public class Key extends AbstractObject {
    private static int id = 0;
    private String description;

    public Key(int id, String description){
        this.id = ++id;
        this.description = description;
    }

    public int getId(){
        return id;
    }
    
    public String getDescription(){
        return description;
    }
    
}
