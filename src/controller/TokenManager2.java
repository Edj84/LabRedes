package controller;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.security.Timestamp;

public final class TokenManager2 {
    private DatagramPacket token;
    private long lastTokenTimestamp;
    private boolean isTokenManager;
    
    public TokenManager2(boolean isTokenManager) {
    	this.isTokenManager = isTokenManager;  
    	token = null;
    	lastTokenTimestamp = -1;
    }
    
    public DatagramPacket createToken() {
    	
    	InetAddress nextIPAddress = PacketManager.getNextInetAddress();
    	
    	byte[] sendData = new byte[1024];
		
		sendData = "1234".getBytes();

		DatagramPacket newToken = new DatagramPacket(sendData, sendData.length, nextIPAddress, 8084);
    	
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
    	
	    	if(newTimeStamp - lastTokenTimestamp < 20000) {
	    		System.out.println("You're not my token, you're just a shallow copy!");
	    	}
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