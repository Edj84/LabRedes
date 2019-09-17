package server;

public class Player {
    private boolean map;
    private ArrayList<Key> keys;
    private String id;

    public Player (ArrayList<Key> keys, String id){
        this.temMapa = false;
        this.keys = keys;
        this.id = id;
    }

    public boolean useMap(){

    }

    public boolean getKeys(){
        return this.keys;
    }

    public boolean getId(){
        return this.id;
    }

    public void useKey(Door door){

    }

    public void acquireKey(Key key){
        
    }

    public void acquireMap(){

    }

    public void openDoor(){
        
    }
}
