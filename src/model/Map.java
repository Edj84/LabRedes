package model;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

import controller.Rand;

public class Map {
	
	private static ArrayList<Room> rooms;
	
	public Map() {
		
		rooms = new ArrayList<Room>();
		Key monsterKey = new Key("...um material radiotivo?!");
		Key RedYellowKey = new Key(" vermelhos e amarelos");
		
		Room monsterRoom = new Room("do Monstro");
		rooms.add(monsterRoom);
		Room yellowRoom = new Room("amarelo");
		yellowRoom.addItem(monsterKey);
		rooms.add(yellowRoom);
		Room redRoom = new Room("vermelho"); 
		rooms.add(redRoom);
		Room whiteRoom = new Room("branco");
		rooms.add(whiteRoom);
		Room blackRoom = new Room("preto");
		rooms.add(blackRoom);
		blackRoom.addItem(RedYellowKey);
		Room blueRoom = new Room("azul"); 
		rooms.add(blueRoom);			

		Door monsterDoor = new Door("...verdes, que brilham no escuro", monsterRoom, redRoom, monsterKey, true);
		monsterRoom.setDoor(monsterDoor, 0);
		redRoom.setDoor(monsterDoor, 1);
		Door RedYellowDoor = new Door("vermelhos e amarelos", redRoom, yellowRoom, RedYellowKey, true);
		yellowRoom.setDoor(RedYellowDoor, 2);
		redRoom.setDoor(RedYellowDoor, 3);
		Door RedWhiteDoor = new Door("vermelhos e brancos", redRoom, whiteRoom, null, false);
		redRoom.setDoor(RedWhiteDoor, 2);
		whiteRoom.setDoor(RedWhiteDoor, 3);
		Door WhiteBlackDoor = new Door("brancos e pretos", whiteRoom, blackRoom, null, false);
		blackRoom.setDoor(WhiteBlackDoor, 0);
		whiteRoom.setDoor(WhiteBlackDoor, 1);
		Door BlueBlackDoor = new Door("pretos e azuis", blueRoom, blackRoom, null, false);
		blueRoom.setDoor(BlueBlackDoor, 0);
		blackRoom.setDoor(BlueBlackDoor, 1);
		
	}
	
	public static Room getRandomRoom() {
		
		ArrayList<Room> auxRooms = new ArrayList<Room>(rooms);
		auxRooms.remove(0);
		auxRooms.remove(0);
		
		int index = Rand.getRandInt(0, auxRooms.size());
		
		Room randRoom = auxRooms.get(index);
		
		return randRoom;		
	}

	public static Room getMonsterRoom(){
		return rooms.get(0);
	}
	
	
}