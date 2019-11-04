package controller;

import java.net.DatagramPacket;
import java.security.Timestamp;

public final class TokenManager {
    private DatagramPacket token;
    private long lastTokenTimestamp;
    private boolean isTokenManager;
    
    public TokenManager(boolean isTokenManager) {
    	this.isTokenManager = isTokenManager;  
    	token = null;
    	lastTokenTimestamp = -1;
    }
    
    public DatagramPacket createToken() {
    	
    	DatagramPacket newToken = new DatagramPacket("1234".getBytes(), 1024);
    	return newToken;
    }
    
    public void destroyToken() {
    	token = null;
    }
    
    public void aquireToken() {
    	
    	long newTimeStamp = System.currentTimeMillis();
    	
    	if(isTokenManager) {
    		
    		if(!checkDoubleToken(newTimeStamp)) {
    			lastTokenTimestamp = newTimeStamp;
    			token = createToken();
    		}
    	}
    	
    	else
    		token = createToken();
    	
    }

    public boolean checkDoubleToken(long newTimeStamp){
        
    	if(lastTokenTimestamp > 0 ) {
    	
	    	if(newTimeStamp - lastTokenTimestamp < 20000)
	            return true;
    	}
        
        return false;
    }

    public boolean checkTokenTimeout(){
        
    	if(lastTokenTimestamp > 0 ) {
    	
	    	if(System.currentTimeMillis() - lastTokenTimestamp > 30000)
	            return true;
    	}
    	
        return false;
    }
    
    public boolean getIsTokenManager() {
    	return isTokenManager;
    }
    
    public DatagramPacket getToken() {
    	return token;
    }
    
}