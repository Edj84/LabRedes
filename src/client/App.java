package client;


public class App{
	private static UDPClient client;
    private static Interface view;
    
    public static void main(String args[]) throws Exception{
        client = new UDPClient();
        view = new Interface();		
        
        while(true) {
            run();           
        }
    }

	private static void run() {
		
		
	}
    
}