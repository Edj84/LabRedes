package controller;

import java.util.Random;

public abstract class Rand {
	
	public static int getRandInt(int min, int max){
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(max-min) + min;
		//System.out.println("Rand entre [" + min + "," + max + "] retornou " + randomInt);
		return randomInt;
	}
	
}