package model;

public class Key extends AbstractObject {
    private static int id = 0;
    private String description;

    public Key(String description){
        this.id = ++id;
        this.description = "Essa chave é feita de um metal " + description;
    }

    public int getId(){
        return id;
    }
    
    public String getDescription(){
        return description;
    }
    
}
