package model;

import java.util.ArrayList;

public class Room {
    private Connection north;
    private Connection south;
    private Connection east;
    private Connection west;
    private ArrayList<Key> keys;
    private boolean hasMap;
    private ArrayList<Player> players;

    public Room (Connection north, Connection south, Connection east, Connection west, ArrayList<Key> keys, boolean hasMap, ArrayList<Player> players){
        this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;
        this.keys = keys;
        this.hasMap = hasMap;
        this.players = players;
    }


}
