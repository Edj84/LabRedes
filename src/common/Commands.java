package common;

import java.util.ArrayList;
import java.util.Set;

public final class Commands{
	
	private ArrayList<String> commandList; 
	
	private Commands(){
		commandList = new ArrayList<String>();
		commandList.add("EXAMINAR");
		commandList.add("MOVER");
		commandList.add("PEGAR"); 
		commandList.add("LARGAR");
		commandList.add("INVENTORIO");
		commandList.add("USAR");
		commandList.add("FALAR"); 
		commandList.add("COCHICAR");
		commandList.add("AJUDA");
	}
	
	public boolean contains(String command) {
		if(commandList.contains(command))
				return true;
		else return false;
	}
	
}
		
	

	
	


