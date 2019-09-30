package model;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

public class Map {
	
	private static ArrayList<Room> rooms;
	
	public Map() {
		
		rooms = new ArrayList<Room>();
		
		Key monsterKey = new Key("...um material radiotivo?!");
		
		Door monsterDoor = new Door("monstro", monsterKey, true);
		Door redDoor = new Door("vermelho", null, false);
		Door blueDoor = new Door("azul", null, false);
		Door purpleDoor = new Door("roxo", null, false);
		Door blackDoor = new Door("preta", null, false);
		Door whiteDoor = new Door("branca", null, false);
		
		rooms.add(new Room(null, null, blueDoor, null, "vermelho", null, null));
		rooms.add(new Room(purpleDoor, null, null, redDoor, "azul", null, null));
		rooms.add(new Room(blackDoor, blueDoor, null, null, "roxo", null, null));
		rooms.add(new Room(null, purpleDoor, whiteDoor, null, "preto", null, null));
		rooms.add(new Room(null, null, null, blackDoor, "branco", null, null));
		
	}
	
	public static Room getRandomRoom() {
		
		Room randRoom = rooms.stream()
					.unordered()
					.findFirst()
					.get();
		
		return randRoom;		
	}
	
	
}