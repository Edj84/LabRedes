package model;

public class Monster extends AbstractObject{
	
	
	public Monster() {
		System.out.println(getDescription());
	}

	@Override
	public String getDescription() {
		
		return "Rooooar!!!";
	}
	
}
