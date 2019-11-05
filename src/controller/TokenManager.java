package controller;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.security.Timestamp;

public final class TokenManager {
    private DatagramPacket token;
    private long lastTokenTimestamp;
    private boolean isTokenManager;
    private int tokenLT;
    
    public TokenManager(boolean isTokenManager, int tokenTime, int pcCount) {
    	this.isTokenManager = isTokenManager;  
    	this.tokenLT = tokenTime*1000*(pcCount+2);
    	token = null;
    	lastTokenTimestamp = -1;
    }
    
    public DatagramPacket createToken() {
    	
    	InetAddress nextIPAddress = PacketManager.getNextInetAddress();
    	
    	byte[] sendData = new byte[1024];
		
		sendData = "1234".getBytes();

		DatagramPacket newToken = new DatagramPacket(sendData, sendData.length, nextIPAddress, 8080);
    	
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
    	
    	else {
    		token = createToken();
    		lastTokenTimestamp = newTimeStamp;
    	}
    	
    }

    public boolean checkDoubleToken(long newTimeStamp){
        
    	if(lastTokenTimestamp > 0 ) {
    	
	    	if(newTimeStamp - lastTokenTimestamp < tokenLT/3) {
	    		System.out.println("You're not my token, you're just a shallow copy!");
	    	}
	            return true;
    	}
        
        return false;
    }

    public boolean checkTokenTimeout(){
        
    	if(lastTokenTimestamp > 0 ) {
    	
	    	if((System.currentTimeMillis() - lastTokenTimestamp) > tokenLT) {
	    		resetTokenTimeout();
	            return true;
	    	}
    	}
    	
        return false;
    }
    
    public boolean getIsTokenManager() {
    	return isTokenManager;
    }
    
    public DatagramPacket getToken() {
    	return token;
    }
    
    private void resetTokenTimeout() {
    	lastTokenTimestamp = System.currentTimeMillis();
    }
    
    public int getTokenTL() {
    	return tokenLT;
    }
    
}