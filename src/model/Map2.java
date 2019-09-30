package model;

import java.util.ArrayList;
import java.util.HashSet;
import controller.Rand;

public class Map2 {
	
	private static ArrayList<Room> rooms;
	private static Room monsterRoom;
	private static Door monsterDoor;
	private static Key monsterKey;
	
	public static void main(String[] args) {
		Map2 m = new Map2();
	}
	
	public Map2() {
		
		createDungeon();
		
	}
	
	private void createDungeon() {
		
		createRooms();
		ArrayList<Room> unconnected = new ArrayList<Room>(rooms);
		createMonsterChambers();
		joinRooms(monsterRoom, unconnected);
		
	}
	
	private void createRooms() {
		
		rooms = new ArrayList<Room>();
		
		ArrayList<String> colors = new ArrayList<String>();
		colors.add("vermelho");
		colors.add("azul");
		colors.add("branco");
		colors.add("preto");
		colors.add("verde");
		colors.add("roxo");
		
		for(String c : colors) { 
			Room room = new Room(c, null, null);
			rooms.add(room);			
		}
		
	}
		
	private void createMonsterChambers() {
		
		monsterRoom = new Room("Câmara do Monstro", null, null);
		rooms.add(monsterRoom);
		createMonster();
	}

	private void createMonster() {
		
		monsterKey = new Key("... radioativo?!");
		Monster monster = new Monster();
		monsterRoom.addItem(monster);
		System.out.println("Monstro repousando no seu quarto");
	}

	private void joinRooms(Room firstRoom, ArrayList<Room> unconnected) {
		
		Room secondRoom = getRandomRoom(unconnected);
		System.out.println("Conectando " + firstRoom.getDescription() + " e " + secondRoom.getDescription());
		
		
		door.setConnection(secondRoom);
		unconnected.remove(secondRoom);
		
		int firstDirection = door.getFirstDirection();
		int secondDirection = getDoorSecondDirection(firstDirection);
		secondRoom.setDoor(door, secondDirection);
		System.out.println(door.getText());
		
		if(!unconnected.isEmpty()) {
			
			Key auxKey = null;
			boolean closed = false;
			Door newDoor;
			int[] candidatesDirections = secondRoom.getFreeWalls();
			int index = Rand.getRandInt(0, candidatesDirections.length);
			int newDoorDirection = candidatesDirections[index];
					
			if(Rand.getRandInt(0, 10) >= 6) {
				auxKey = new Key(secondRoom.getDescription());
				closed = true;
			}
			newDoor = new Door(secondRoom, auxKey, closed);
			secondRoom.setDoor(door, newDoorDirection);
			joinRooms(newDoor, unconnected);			
		}
			
	}

	private int getDoorSecondDirection(int doorDirection) {
		
		if(doorDirection == 0 || doorDirection == 2)
			doorDirection++;
		else
			doorDirection--;
		
		return doorDirection;
	}

	public static Room getRandomRoom(ArrayList<Room> roomsList) {
		
		if(roomsList == null)
			roomsList = rooms;
		
		Room randRoom = roomsList.get(Rand.getRandInt(0, roomsList.size()));
		
		return randRoom;		
	}
	
	
}
