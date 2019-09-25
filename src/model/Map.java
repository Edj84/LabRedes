package model;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

public class Map {
	
	private static ArrayList<Room> rooms;
	
	public Map() {
		
		rooms = new ArrayList<Room>();
		
		Door redDoor = new Door("Vermelha", null, false);
		Door blueDoor = new Door("Azul", null, false);
		Door purpleDoor = new Door("Roxa", null, false);
		Door blackDoor = new Door("Preta", null, false);
		Door whiteDoor = new Door("Branca", null, false);
		
		rooms.add(new Room(null, null, blueDoor, null, "Vermelho", null, null));
		rooms.add(new Room(purpleDoor, null, null, redDoor, "Azul", null, null));
		rooms.add(new Room(blackDoor, blueDoor, null, null, "Roxo", null, null));
		rooms.add(new Room(null, purpleDoor, whiteDoor, null, "Preto", null, null));
		rooms.add(new Room(null, null, null, blackDoor, "Branco", null, null));
		
	}
	
	public static Room getRandomRoom() {
		
		Room start = rooms.parallelStream()
					.findAny().get();
		
		return start;		
	}
	
	
}
