package model;

public class Door{
    private String id;
    private String description;
    private ArrayList<Key> requirements;
    private boolean closed;

    public Door (String id, String description, ArrayList<Key> requirements,boolean closed){
        this.id = id;
        this.description = description;
        this.requirements = requirements;
        this.closed = closed;
    }

    public String getId(){

    }

    public String getDescription(){

    }

    public ArrayList<Key> getRequirements(){

    }

    public boolean checkClosed(){

    }
}
