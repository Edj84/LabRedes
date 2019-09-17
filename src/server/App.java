package server;


public class App{

    public static void main(String args[]) throws Exception{
        UDPServer server = new UDPServer();
        
        while(true) {
            server.run();           
        }
    }
    
}