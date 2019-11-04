package controller;

import java.net.DatagramPacket;
import java.security.Timestamp;

public final class TokenManager {
    private DatagramPacket token;
    private long lastTokenTimestamp;
    private boolean isTokenManager;
    
    public TokenManager(boolean isTokenManager) {
    	this.isTokenManager = isTokenManager;    	
    }
    
    public DatagramPacket createToken() {
    	
    	DatagramPacket newToken = new DatagramPacket("1234".getBytes(), 1024);
    	return newToken;
    }
    
    public DatagramPacket getToken() {
    	return token;
    }
    
    public void aquireToken() {
    	
    	token = createToken();
    	
    	if(isTokenManager) {
    		
    		if(!checkDoubleToken())
    			lastTokenTimestamp = System.currentTimeMillis();
    		
    	}	
    }

    public boolean checkDoubleToken(){
        
    	if(System.currentTimeMillis() - lastTokenTimestamp < 20000)
            return true;
        
        return false;
    }

    public boolean tokenTimeout(){
        
    	if(System.currentTimeMillis() - lastTokenTimestamp > 30000)
            return true;
        
        return false;
    }
    
    public boolean getIsTokenManager() {
    	return isTokenManager;
    }
    
}