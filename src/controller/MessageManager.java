package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
import controller.Rand;
import model.Node; 

public class MessageManager {
	
	private ArrayList<String> beginParts;
	private ArrayList<String> middleParts;
	private ArrayList<String> endParts;
	private StringBuilder sb;
	
	public MessageManager() {
		beginParts = new ArrayList<String>(readFile("begin.txt"));
		middleParts = new ArrayList<String>(readFile("middle.txt"));
		endParts = new ArrayList<String>(readFile("end.txt"));
		
	}
	
	public ArrayList<String> getNewMessage() {
		
		String destiny = "Stein";
		
		ArrayList<String> result = new ArrayList<>();
		
		sb = new StringBuilder();
		
		String begin = beginParts.get(Rand.getRandInt(0, beginParts.size()));
		String middle = middleParts.get(Rand.getRandInt(0, middleParts.size()));
		String end = endParts.get(Rand.getRandInt(0, endParts.size()));
		
		sb.append(begin + " ");
		sb.append(middle + " "); 
		sb.append(end + " "); 
		
		String msg = sb.toString();
		String CRC = String.valueOf(SecurityManager.crc16(msg));
		
		result.add("naocopiado");
		result.add(Node.getID());
		result.add(destiny);
		result.add(CRC);
		result.add(msg);
		
		return result;
	}
	
	private ArrayList<String> readFile(String fileName) {
    	
		File file = new File(fileName);
    	Scanner scan = null;
		ArrayList<String> parts = new ArrayList<String>();
    	
    	try {
			scan = new Scanner(file);
						
			String aux = null;			
			
			while(scan.hasNextLine()) {
				aux = scan.nextLine();
				parts.add(aux);
			}
		
		} 
		
		catch (FileNotFoundException e) {
			System.out.println("ERROR: Unable to find message parts file " + fileName);
		}
		
		finally {
			scan.close();
		}
    	
    	return parts;
		
	}

	
}
