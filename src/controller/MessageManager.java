package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
import controller.Rand; 

public class MessageManager {
	
	private ArrayList<String> beginParts;
	private ArrayList<String> middleParts;
	private ArrayList<String> endParts;
	
	public MessageManager() {
		beginParts = new ArrayList<String>(readFile("begin.txt"));
		middleParts = new ArrayList<String>(readFile("middle.txt"));
		endParts = new ArrayList<String>(readFile("end.txt"));
	}
	
	public String getNewMessage() {
		
		String begin = beginParts.get(Rand.getRandInt(0, beginParts.size()));
		String middle = middleParts.get(Rand.getRandInt(0, middleParts.size()));
		String end = endParts.get(Rand.getRandInt(0, endParts.size()));
		
		String result = begin + " " + middle + " " + end; 
		
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
