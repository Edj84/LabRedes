package controller;

import java.net.DatagramPacket;
import java.security.Timestamp;

public final class TokenManager {
    private DatagramPacket token;
    private long timeOfCreation;
    
    public TokenManager() {
    	
    }
    
    private void createToken() {
    	
    	DatagramPacket newToken = new DatagramPacket("1234".getBytes(), 1024);
    	timeOfCreation = System.currentTimeMillis();
    }
    
    public boolean checkDoubleToken(){
        
    	if(System.currentTimeMillis() - timeOfCreation < 3000)
            return true;
        
        return false;
    }

    public boolean tokenTimeout(){
        
    	if(System.currentTimeMillis() - timeOfCreation > 12000)
            return true;
        
        return false;
    }

}