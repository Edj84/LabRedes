import java.net.DatagramPacket;

import controller.PackManager;
import controller.TokenManager;

public class Node{
    private String ID;
    private Node next;
    private UDPSocket in;
    private UDPSocket out;
    private PackManager packMan;
    private TokenManager tokenMan;
    private String config;
    private DatagramPacket token;

    public Node(String ID){
        this.ID = ID;
        config = "config.txt";
        packMan = new PackManager(); 
        tokenMan = new TokenManager();
    }
    
    public boolean hasToken() {
    	return token != null;
    }

}